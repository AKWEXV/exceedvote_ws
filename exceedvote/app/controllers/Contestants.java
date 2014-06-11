package controllers;

import play.*;
import play.data.*;
import play.mvc.*;
import static play.data.Form.*;

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
		return ok(views.xml.contestant.render(Contestant.find.byId(id)));
	}

	@Security.Authenticated(Secured.class)
	public static Result viewContestant(Long id) {
		User user = User.findByUsername(request().username());
		if (user.getRole().getName().equals("Admin")) {
			return ok(views.html.contestant_management.render(Contestant.find.byId(id), user));
		}
		else {
			return redirect(
                routes.Application.index()
            );
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result contestantsManagement() {
		User user = User.findByUsername(request().username());
		if (user.getRole().getName().equals("Admin")) {
			return ok(views.html.contestants_management.render(Contestant.find.all(), user));
		}
		else {
			return redirect(
                routes.Application.index()
            );
		}	
	}

	@Security.Authenticated(Secured.class)
	public static Result addContestant() {
		User user = User.findByUsername(request().username());
		if (user.getRole().getName().equals("Admin")) {
			Contestant.addContestant(
				form().bindFromRequest().get("name"),
				form().bindFromRequest().get("description")
			);
			return redirect(
                routes.Contestants.contestantsManagement()
            );
			// return ok(views.html.contestants_management.render(Contestant.find.all(), user));
		}
		else {
			return redirect(
                routes.Application.index()
            );
		}	
	}

	@Security.Authenticated(Secured.class)
	public static Result updateContestant(Long id) {
		User user = User.findByUsername(request().username());
		if (user.getRole().getName().equals("Admin")) {
			Contestant.updateContestant(
				id,
				form().bindFromRequest().get("name"),
				form().bindFromRequest().get("description")
			);
			return redirect(
                routes.Contestants.contestantsManagement()
            );
			// return ok(views.html.contestants_management.render(Contestant.find.all(), user));
		}
		else {
			return redirect(
                routes.Application.index()
            );
		}	
	}

	@Security.Authenticated(Secured.class)
	public static Result deleteContestant(Long id) {
		User user = User.findByUsername(request().username());
		if (user.getRole().getName().equals("Admin")) {
			Contestant.find.ref(id).delete();
			return redirect(
                routes.Contestants.contestantsManagement()
            );
			// return ok(views.html.contestants_management.render(Contestant.find.all(), user));
		}
		else {
			return redirect(
                routes.Application.index()
            );
		}	
	}

}
