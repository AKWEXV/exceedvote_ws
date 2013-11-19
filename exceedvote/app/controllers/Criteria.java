package controllers;

import play.*;
import play.data.*;
import play.mvc.*;

import models.*;
import views.html.*;
import views.xml.*;

public class Criteria extends Controller {

	public static Result indexXml() {
		return ok(views.xml.criteria.render(Criterion.find.all()));
	}
	
	public static Result criterionXml(Long id) {
		return ok(views.xml.criterion.render(Criterion.find.where().eq("id", id).findUnique()));
	}
	
}
