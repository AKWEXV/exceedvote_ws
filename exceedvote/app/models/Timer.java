package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

/**
 * 
 * @author Sonny
 *
 */
@Entity
public class Timer extends Model {
	
	@Id
	public Long id;
	
	public Date start;
	
	public Date finish;
	
	public static Finder<Long, Timer> find = new Finder<Long, Timer>(Long.class, Timer.class);

	public Timer() {
		
	}
	
	public Timer(Date start, Date finish) {
		this.start = start;
		this.finish = finish;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getFinish() {
		return finish;
	}

	public void setFinish(Date finish) {
		this.finish = finish;
	}
	
	public boolean checkAllowVote() {
        Date now = new Date();
        Date start = this.getStart();
        Date finish = this.getFinish();
        if (now.after(start) && now.before(finish)) {
        	return true;
        }
        else {
        	return false;
        }
	}
 	
}
