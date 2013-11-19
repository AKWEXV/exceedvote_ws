package models;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

/**
 * 
 * @author Sonny
 *
 */
@Entity
public class Criterion extends Model {

	@Id
	public Long id;
	
	@Constraints.Required
	public String name;
	
	public static Finder<Long, Criterion> find = new Finder<Long, Criterion>(Long.class, Criterion.class);
	
	public Criterion() {
		
	}
	
	public Criterion(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "";
	}
	
}
