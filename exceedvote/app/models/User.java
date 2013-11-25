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
public class User extends Model {
	
	@Id
	public Long id;
	
	@OneToOne
	public Role role;
	
	@OneToOne
	public Contestant contestant;
	
	@Constraints.Required
	public String username;
	
	@Constraints.Required
	public String password;
	
	public String email;
	
	public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
	
	public User() {
		
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/* */
	public static String getPasswordFromUsername(String username) {
		User user = find.where().eq("username", username).findUnique();
		if (user != null)
			return user.getPassword();
		else
			return null;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Contestant getContestant() {
		return contestant;
	}

	public void setContestant(Contestant contestant) {
		this.contestant = contestant;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString() {
		return "";
	}

}
