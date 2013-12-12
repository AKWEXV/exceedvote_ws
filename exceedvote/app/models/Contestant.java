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
public class Contestant extends Model {

	@Id
	public Long id;
	
	@Constraints.Required
	public String name;
	
	public String description;
	
	public static Finder<Long, Contestant> find = new Finder<Long, Contestant>(Long.class, Contestant.class);
	
	public Contestant() {
		
	}
	
	public Contestant(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return "";
	}
	
}
