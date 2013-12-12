package controllers;

import java.util.List;

import models.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import play.*;
import play.data.*;
import play.libs.XPath;
import play.mvc.*;
import models.*;
import views.html.*;
import views.xml.*;

public class Vote extends Controller {
	
	public static Result voteHtml() {
		return ok(views.html.votes.render(models.Vote.find.all()));
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
			return created(views.xml.vote.render(vote));
		}
	}
	
	@With(Authentication.class)
	public static Result myVoteXml() {
		String authorizationHeader = request().getHeader("Authorization");
		String username = getAuthorizationElement("username", authorizationHeader);
		User user = User.find.where().eq("username", username).findUnique();
		System.out.println(user.username);
		System.out.println(models.Vote.find.where().eq("user", user).findList().size());
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
