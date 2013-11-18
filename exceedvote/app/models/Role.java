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
	public Integer id;
	
	public String name;
	
	public Integer criterionVote;
	
	public Finder<Integer, Role> find = new Finder<Integer, Role>(Integer.class, Role.class);

	public Role() {
		
	}
	
	public Role(String name, int criterionVote) {
		this.name = name;
		this.criterionVote = criterionVote;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCriterionVote() {
		return criterionVote;
	}

	public void setCriterionVote(Integer criterionVote) {
		this.criterionVote = criterionVote;
	}
	
	public String toString() {
		return "";
	}
	
}
