package controllers;

import play.*;
import play.data.*;
import play.mvc.*;
import static play.data.Form.*;

import models.*;
import views.html.*;
import views.xml.*;

public class Criteria extends Controller {
	
	@Security.Authenticated(Secured.class)
	public static Result index() {
		return ok(views.html.criteria.render(Criterion.find.all()));
	}
	
	public static Result indexXml() {
		if (Criterion.find.all().size() == 0) {
			return noContent();
		}
		else {
			return ok(views.xml.criteria.render(Criterion.find.all()));
		}
	}
	
	public static Result criterionXml(Long id) {
		if (Criterion.find.where().eq("id", id).findUnique() == null) {
			return notFound();
		}
		else {
			return ok(views.xml.criterion.render(Criterion.find.where().eq("id", id).findUnique()));
		}
	}

	@Security.Authenticated(Secured.class)
	public static Result criteriaManagement() {
		User user = User.findByUsername(request().username());
		if (user.getRole().getName().equals("Admin")) {
			return ok(views.html.criteria_management.render(Criterion.find.all(), user));
		}
		else {
			return redirect(
                routes.Application.index()
            );
		}	
	}

	@Security.Authenticated(Secured.class)
	public static Result addCriterion() {
		User user = User.findByUsername(request().username());
		if (user.getRole().getName().equals("Admin")) {
			Criterion.addCriterion(
				form().bindFromRequest().get("name"),
				form().bindFromRequest().get("type")
			);
			return redirect(
                routes.Criteria.criteriaManagement()
            );
			// return ok(views.html.criteria_management.render(Criterion.find.all(), user));
		}
		else {
			return redirect(
                routes.Application.index()
            );
		}	
	}

	@Security.Authenticated(Secured.class)
	public static Result deleteCriterion(Long id) {
		User user = User.findByUsername(request().username());
		if (user.getRole().getName().equals("Admin")) {
			Criterion.find.ref(id).delete();
			return redirect(
                routes.Criteria.criteriaManagement()
            );
			// return ok(views.html.criteria_management.render(Criterion.find.all(), user));
		}
		else {
			return redirect(
                routes.Application.index()
            );
		}	
	}

	@Security.Authenticated(Secured.class)
	public static Result updateCriterion(Long id) {
		User user = User.findByUsername(request().username());
		if (user.getRole().getName().equals("Admin")) {
			Criterion.updateCriterion(
				id,
				form().bindFromRequest().get("name"),
				form().bindFromRequest().get("type")
			);
			return redirect(
                routes.Criteria.criteriaManagement()
            );
			// return ok(views.html.criteria_management.render(Criterion.find.all(), user));
		}
		else {
			return redirect(
                routes.Application.index()
            );
		}	
	}
	
}
