package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.*;
import models.User;

public class Application extends Controller {

    // -- Authentication
    
    public static class Login {
        
        public String username;
        public String password;
        
        public String validate() {
            if(User.authenticate(username, password) == null) {
                Logger.info(username + " " + password);
                return "Invalid user or password";
            }
            return null;
        }
        
    }

    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("username", loginForm.get().username);
            return redirect(
                routes.Application.index()
            );
        }
    }

    /**
     * Login page.
     */
    public static Result login() {
        return ok(
            views.html.login.render(form(Login.class))
        );
    }

    /**
     * Logout and clean the session.
     */
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
            routes.Application.login()
        );
    }

    @Security.Authenticated(Secured.class)
    public static Result index() {
        return ok(views.html.home.render(User.findByUsername(request().username())));
    }
    
    public static Result indexXml() {
    	String baseUrl = routes.Application.index().absoluteURL(request());
    	return ok(views.xml.index.render(baseUrl));
    }
    
    @With(Authentication.class)
    public static Result authorized() {
    	return ok(request().getHeader("Authorization"));
    }

}
