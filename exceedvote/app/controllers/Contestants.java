package controllers;

import play.*;
import play.data.*;
import play.mvc.*;

import models.*;
import views.html.*;
import views.xml.*;

public class Contestants extends Controller {
	
	@Security.Authenticated(Secured.class)
	public static Result index() {
		return ok(views.html.contestants.render(Contestant.find.all(), User.findByUsername(request().username())));
	}
	
	public static Result indexXml() {
		if (Contestant.find.all().size() == 0) {
			return noContent();
		}
		else {
			return ok(views.xml.contestants.render(Contestant.find.all()));
		}
	}
	
	public static Result contestantXml(Long id) {
		return ok(views.xml.contestant.render(Contestant.find.where().eq("id", id).findUnique()));
	}

}
