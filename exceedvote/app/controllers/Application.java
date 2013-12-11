package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result indexXml() {
    	String baseUrl = routes.Application.index().absoluteURL(request());
    	return ok(views.xml.index.render(baseUrl));
    }
    
    @With(Authentication.class)
    public static Result authorized() {
    	return ok("Logged in");
    }

}
