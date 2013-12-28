package controllers;

import java.util.*;

import play.*;
import play.data.*;
import play.mvc.*;
import static play.data.Form.*;
import models.*;
import models.Timer;
import views.html.*;
import views.xml.*;

@Security.Authenticated(Secured.class)
public class Timers extends Controller {
	
	public static Result timerManagement() {
		User user = User.findByUsername(request().username());
		if (user.getRole().getName().equals("Admin")) {
			return ok(views.html.timer_management.render(Timer.find.ref((long) 1), user));
		}
		else {
			return redirect(
	                routes.Application.index()
	            );
		}
	}
	
	public static Result updateTimer() {
		User user = User.findByUsername(request().username());
		if (user.getRole().getName().equals("Admin")) {
			Timer timer = Timer.find.ref((long) 1);
			int start_day = Integer.parseInt(form().bindFromRequest().get("start.day"));
			int start_month = Integer.parseInt(form().bindFromRequest().get("start.month")) - 1;
			int start_year = Integer.parseInt(form().bindFromRequest().get("start.year")) - 1900;
			int start_hour = Integer.parseInt(form().bindFromRequest().get("start.hour"));
			int start_minute = Integer.parseInt(form().bindFromRequest().get("start.minute"));
			int finish_day = Integer.parseInt(form().bindFromRequest().get("finish.day"));
			int finish_month = Integer.parseInt(form().bindFromRequest().get("finish.month")) - 1;
			int finish_year = Integer.parseInt(form().bindFromRequest().get("finish.year")) - 1900;
			int finish_hour = Integer.parseInt(form().bindFromRequest().get("finish.hour"));
			int finish_minute = Integer.parseInt(form().bindFromRequest().get("finish.minute"));
			Date newStart = new Date(start_year, start_month, start_day, start_hour, start_minute);
			Date newFinish = new Date(finish_year, finish_month, finish_day, finish_hour, finish_minute);
			timer.setStart(newStart);
			timer.setFinish(newFinish);
			timer.update();
			return redirect(
			        routes.Timers.timerManagement()
			    );
		}
		else {
			return redirect(
		            routes.Application.index()
		        );			
		}
	}

}
