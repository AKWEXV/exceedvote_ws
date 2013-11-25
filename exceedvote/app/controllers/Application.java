package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    @With(Authentication.class)
    public static Result authorized() {
    	return ok("Logged in");
    }

}
