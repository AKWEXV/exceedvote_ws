package controllers;

import play.*;
import play.data.*;
import play.mvc.*;
import static play.data.Form.*;

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

	public static Result updateProfile() {
		User.updateUser(form().bindFromRequest().get("username"), form().bindFromRequest().get("email"));
		return redirect(routes.Users.profile());
	}

	public static Result changePassword() {
		User.changePassword(
			request().username(),
			form().bindFromRequest().get("current_password"),
			form().bindFromRequest().get("password"),
			form().bindFromRequest().get("retype_password")
		);
		Logger.info(request().username() + " " + form().bindFromRequest().get("password"));
		return redirect(routes.Users.profile());
	}

	public static Result usersManagement() {
		User user = User.findByUsername(request().username());
		if (user.getRole().getName().equals("Admin")) {
			return ok(views.html.users_management.render(User.find.all(), user));
		}
		else {
			return redirect(
                routes.Application.index()
            );
		}	
	}

}
