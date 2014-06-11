package controllers;

import static play.data.Form.form;

import java.util.List;

import models.Ballot;
import models.Contestant;
import models.Criterion;
import models.Timer;
import models.User;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import play.libs.XPath;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.With;

public class Vote extends Controller {
	
	public static Result voteHtml() {
		return ok(views.html.votes.render(models.Vote.find.all()));
	}
	
	@Security.Authenticated(Secured.class)
	public static Result index() {
		return ok(views.html.index.render());
		//return ok(views.html.vote.render(User.findByUsername(request().username())));
	}

	@Security.Authenticated(Secured.class)
	public static Result reset() {
		User user = User.findByUsername(request().username());
		for (models.Vote vote : models.Vote.find.where().eq("user", user).findList()) {
			Long del = vote.getId();
			models.Vote.find.ref(del).delete();
		}
		return ok(views.html.home.render(Rank.getRanking(), user, Timer.find.ref((long) 1), Criterion.find.all(), Contestant.find.all()));
	}

	@With(Authentication.class)
	public static Result vote(Long criterionId) {
		Document dom = request().body().asXml();
		if (dom == null) {
			return badRequest("Expecting XML data");
		}
		else {
			String authorizationHeader = request().getHeader("Authorization");
			String username = getAuthorizationElement("username", authorizationHeader);
			User user = User.find.where().eq("username", username).findUnique();
			// GET criterion from request url (/id)
			Criterion criterion = Criterion.find.where().eq("id", criterionId).findUnique();
			models.Vote lvuc = models.Vote.find.where().eq("user", user).eq("criterion", criterion).findUnique();
			// List<models.Vote> lvuc = models.Vote.find.where().eq("user", user).eq("criterion", criterion).findList();
			// if (lvuc.size() != 0) {
			if (lvuc != null) {

					List<Ballot> ballots = lvuc.getBallots();

					NodeList contestantsNodeList = XPath.selectNodes("vote/contestants/contestant", dom);

					if (criterion.getType() == 1) {
						boolean haveScore = false;
						for (int i = 0; i < contestantsNodeList.getLength(); i++) {
							Node contestantNode = contestantsNodeList.item(i);
							if (Integer.parseInt(XPath.selectText("score",contestantNode)) > 0) {
								if (!haveScore)
									haveScore = true;
								else
									return badRequest("Your vote is for CRITERION, ONLY 1 contesant can get vote");
							}
						}
					}

					for (int i = 0; i < contestantsNodeList.getLength(); i++) {
						Node contestantNode = contestantsNodeList.item(i);
						int contestantId = Integer.parseInt(XPath.selectText("id",contestantNode));
						int score = Integer.parseInt(XPath.selectText("score",contestantNode));
						Contestant contestant = Contestant.find.where().eq("id", contestantId).findUnique();
						
						for (Ballot ballot : ballots) {
							if (ballot.getContestant().getId() == contestantId) {
								if (criterion.getType() == 1) {
									ballot.setScore(user.getRole().getCriterionVote());
								}
								else {
									ballot.setScore(score);
								}
								ballot.update();
							}
						}
					}
				// }
			}
			else {
				models.Vote vote = new models.Vote(user, criterion);
				NodeList contestantsNodeList = XPath.selectNodes("vote/contestants/contestant", dom);
				for (int i = 0; i < contestantsNodeList.getLength(); i++) {
					Node contestantNode = contestantsNodeList.item(i);
					int contestantId = Integer.parseInt(XPath.selectText("id",contestantNode));
					int score = Integer.parseInt(XPath.selectText("score",contestantNode));
					Contestant contestant = Contestant.find.where().eq("id", contestantId).findUnique();
					Ballot ballot = new Ballot(contestant, score);
					ballot.save();
					vote.addBallot(ballot);
				}
				vote.save();
			}
			models.Vote vote = models.Vote.find.where().eq("user", user).eq("criterion", criterion).findUnique();
			return created(views.xml.vote.render(vote));
		}
	}
	
	@Security.Authenticated(Secured.class)
	public static Result webVote() {
		if (!Timer.find.ref((long) 1).checkAllowVote()) {
			return redirect(
					routes.Application.index()
				);
		}
		else {
			User user = User.findByUsername(request().username());
			// List<models.Vote> lv = models.Vote.find.where().eq("user", user).findList();
			for (Criterion c : Criterion.find.all()) {
				models.Vote newVote = new models.Vote(user, c);
				if (c.getType() == 1) {
					Long conId = Long.parseLong(form().bindFromRequest().get(c.getName()));
					if (conId == 999) {
						flash("error", "You need to select all vote");
						return redirect(
							routes.Application.index()
							);
					}
					Ballot ballot = new Ballot(Contestant.find.ref(conId), user.getRole().getCriterionVote());
					ballot.save();
					newVote.addBallot(ballot);
				}
				else {
					for (Contestant con : Contestant.find.all()) {
						int score = Integer.parseInt(form().bindFromRequest().get(c.getName() + "." + con.getName()));
						if (score == 999) {
							flash("error", "You need to select all vote");
							return redirect(
								routes.Application.index()
								);
						}
						Ballot ballot = new Ballot(con, score);
						ballot.save();
						newVote.addBallot(ballot);
					}
				}
				flash("success", "You vote is now submitted");
				newVote.save();
			}
			return redirect(
	                routes.Application.index()
	            );
		}
	}
	
	@With(Authentication.class)
	public static Result myVoteXml() {
		String authorizationHeader = request().getHeader("Authorization");
		String username = getAuthorizationElement("username", authorizationHeader);
		User user = User.find.where().eq("username", username).findUnique();
//		System.out.println(user.username);
//		System.out.println(models.Vote.find.where().eq("user", user).findList().size());
		if (models.Vote.find.where().eq("user", user).findList().size() == 0) {
			return noContent();
		}
		else {
			List<models.Vote> voteList = models.Vote.find.where().eq("user", user).findList();
			return ok(views.xml.myvote.render(voteList));
		}
	}
	
	private static String getAuthorizationElement(String element, String requestHeader) {
		int index = requestHeader.indexOf(element);
		StringBuilder output = new StringBuilder();
		while (requestHeader.charAt(index) != '"') {
			index++;
		}
		index++;
		while (requestHeader.charAt(index) != '"') {
			output.append(requestHeader.charAt(index));
			index++;
		}
		return output.toString();
	}

}
