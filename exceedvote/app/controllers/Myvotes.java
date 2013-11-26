package controllers;

import play.*;
import play.data.*;
import play.mvc.*;

import models.*;
import views.html.*;
import views.xml.*;

public class Myvotes extends Controller {
	
	public static Result myvoteXml(Long id) {
		return ok(views.xml.vote.render(models.Vote.find.where().eq("id", id).findUnique()));
	}

}
