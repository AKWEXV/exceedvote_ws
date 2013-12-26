package controllers;

import play.*;
import play.data.*;
import play.mvc.*;

import models.*;
import views.html.*;
import views.xml.*;

@Security.Authenticated(Secured.class)
public class Users extends Controller {
	
	public static Result index() {
		return ok(views.html.users.render(User.find.all()));
	}

	public static Result profile() {
		return ok(views.html.profile.render(User.findByUsername(request().username())));	
	}

}
