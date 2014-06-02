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


	public Criterion(String name, int type) {
		this.name = name;
		this.type = type;
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
	
	public static void addCriterion(String name, String type) {
		Criterion criterion = find.where()
									.eq("name", name)
									.findUnique();
		if (criterion == null) {
			Criterion newCriterion = new Criterion(name, Integer.parseInt(type));
			newCriterion.save();
		}
	}

	public static void updateCriterion(Long id, String name, String type) {
		Criterion criterion = find.ref(id);
		if (criterion != null) {
			criterion.setName(name);
			criterion.setType(Integer.parseInt(type));
			criterion.update();
		}
	}

}
