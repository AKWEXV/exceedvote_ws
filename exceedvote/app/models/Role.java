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
public class Role extends Model {
	
	@Id
	public Long id;
	
	public String name;
	
	public int criterionVote;
	
	public Finder<Long, Role> find = new Finder<Long, Role>(Long.class, Role.class);

	public Role() {
		
	}
	
	public Role(String name, int criterionVote) {
		this.name = name;
		this.criterionVote = criterionVote;
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

	public int getCriterionVote() {
		return criterionVote;
	}

	public void setCriterionVote(int criterionVote) {
		this.criterionVote = criterionVote;
	}
	
	public String toString() {
		return "";
	}
	
}
