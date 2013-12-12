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
public class Criterion extends Model {

	@Id
	public Long id;
	
	@Constraints.Required
	public String name;
	
	public int type;
	
	public static Finder<Long, Criterion> find = new Finder<Long, Criterion>(Long.class, Criterion.class);
	
	public Criterion() {
		
	}
	
	public Criterion(String name) {
		this.name = name;
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
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String toString() {
		return "";
	}
	
}
