package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.*;
import models.Contestant;
import models.Criterion;
import models.Timer;
import models.User;
import models.Vote;

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
        User user = User.findByUsername(request().username());
        // List<models.Vote> listVote = models.Vote.find.where().eq("user", user).findList();
        if (models.Vote.find.where().eq("user", user).findList().size() > 0) {
            return ok(views.html.vote.render(Rank.getRanking(), user, Timer.find.ref((long) 1), models.Vote.find.where().eq("user", user).findList()));
        }
        else {
            return ok(views.html.home.render(Rank.getRanking(), user, Timer.find.ref((long) 1), Criterion.find.all(), Contestant.find.all()));
        }
    }

    @Security.Authenticated(Secured.class)
    public static Result about() {
        return ok(views.html.about.render(User.findByUsername(request().username())));
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
