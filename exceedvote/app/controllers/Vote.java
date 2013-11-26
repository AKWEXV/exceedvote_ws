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
	
	public static Result vote() {
		Document dom = request().body().asXml();
		if (dom == null) {
			return badRequest("Expecting XML data");
		}
		else {
			Node voteNode = XPath.selectNode("vote", dom);
			Node criterionNode = XPath.selectNode("criterion", voteNode);
			int criterionId = Integer.parseInt(XPath.selectText("id", criterionNode));
			Criterion criterion = Criterion.find.where().eq("id", criterionId).findUnique();
			Node userNode = XPath.selectNode("user", voteNode);
			int userId = Integer.parseInt(XPath.selectText("id", userNode));
			User user = User.find.where().eq("id", userId).findUnique();
			models.Vote vote = new models.Vote(user, criterion);
			Node contestantsNode = XPath.selectNode("contestants", voteNode);
			NodeList contestantNodeList = XPath.selectNodes("contestant", contestantsNode);
			for (int i = 0; i < contestantNodeList.getLength(); i++) {
				Node contestantNode = contestantNodeList.item(i);
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

}
