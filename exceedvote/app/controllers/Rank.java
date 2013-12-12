package controllers;

import java.util.List;

import play.*;
import play.data.*;
import play.mvc.*;
import models.*;
import views.html.*;
import views.xml.*;

public class Rank extends Controller {

	public static Result rank() {
		List<Criterion> criteriaList = Criterion.find.all();
		List<Contestant> contestantsList = Contestant.find.all();
		return ok(views.xml.rank.render(criteriaList, contestantsList));
	}
	
}
