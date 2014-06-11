package models;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

/**
 * Role defines one Role that a User can have.
 * A Role has a name and ID.
 * In the current implementation of ExceedVote, the roles 
 * are poorly used as they are mostly checked as Strings.
 * 
 * @author Kanokphol
 * revised 11-6-2014: add javadoc, revise toString to return role name
 * revised 11-6-2014: added findByName(String name) to lookup roles
 *
 */
@Entity
public class Role extends Model {
	/** A dummy role for users that have no role, to avoid nulls. */
	public static final Role NONE = new Role("None", 0);
	
	@Id
	public Long id;
	
	public String name;
	
	// the weight given to this role when computing ranks for votes?
	private int criterionVote;
	// find used in User class to locate roles.
	public static Finder<Long, Role> find = new Finder<Long, Role>(Long.class, Role.class);

	public Role() {
		this.name = "No name";
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
	
	/**
	 * Return a string description of this role.
	 * This obviates the need to use role.getName().
	 * @return the role name
	 */
	public String toString() {
		return this.name;
	}
	
	/**
	 * Find a role by name.
	 * @param name the string name of role to find.
	 * @return Role with this name, or null if no such role name.
	 */
	public static Role findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
}
