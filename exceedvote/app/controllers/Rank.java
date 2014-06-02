package controllers;

import java.util.List;
import java.util.ArrayList;

import play.*;
import play.data.*;
import play.mvc.*;
import models.*;
import views.html.*;
import views.xml.*;

public class Rank extends Controller {

	public static List<Ranking> ranking = new ArrayList<Ranking>();

	public static Result rank() {
		if (Timer.find.ref((long) 1).checkCompetitionOver()) {
			updateRanking(ranking);
			return ok(views.xml.rank.render(ranking));
		}
		else {
			return badRequest("You cannot view ranking now, Please wait until competition is over.");
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result index() {
		updateRanking(ranking);
		return ok(views.html.rank.render(ranking, User.findByUsername(request().username())));
	}

	public static void updateRanking(List<Ranking> ranking) {
		List<Criterion> criteria = Criterion.find.all();
		List<Contestant> contestants = Contestant.find.all();
		ranking.clear();
		for (Criterion criterion : criteria) {
			Ranking r = new Ranking(criterion, contestants);
			ranking.add(r);
		}
	}

	public static List<Ranking> getRanking() {
		updateRanking(ranking);
		return ranking;
	}
	
}
