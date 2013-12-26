package controllers;

import play.*;
import play.data.*;
import play.mvc.*;
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
	
}
