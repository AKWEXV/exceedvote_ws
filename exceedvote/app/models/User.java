package models;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

/**
 * A User of the application.
 * Each user has a Role that determines his privileges in the app.
 * 
 * @author Sonny
 * @see app.models.Role
 */
@Entity
public class User extends Model {
	// Model assigns its own values to the Id field when persisted.
	@Id
	private Long id;
	
	/** The user's role. In this version a user can have only one role. */
	@ManyToOne
	public Role role;
	
	@Constraints.Required
	public String username;
	
	@Constraints.Required
	public String password;
	
//JIM: email is still used in several methods and views.  Can't just comment it out.
	public String email;
	
	public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
	
	public User() {
		this("","","",Role.NONE);
	}
	
	public User(String username, String password, String email, Role role) {
		this.username = username;
		this.password = password;
		this.email = email;
		// don't allow null role
		this.role = (role == null)? Role.NONE : role;
	}

	public static String getPasswordFromUsername(String username) {
		User user = find.where().eq("username", username).findUnique();
		if (user != null)
			return user.getPassword();
		else
			return null;
	}

	/**
     * Authenticate a User.
     * TODO encapsulate password checking so it can be changed.
     */
    public static User authenticate(String username, String password) {
        return find.where()
            .eq("username", username)
            .eq("password", password)
            .findUnique();
    }

    /**
	 * Find user by username.
	 * @return first user with matching username, or null if not found.
	*/
	public static User findByUsername(String username) {
		return find.where()
                   .eq("username", username)
        		   .findUnique();
	}

	public static void updateUser(String username, String email) {
		User user = find.where()
					    .eq("username", username)
					    .findUnique();
		//TODO throw exception to indicate username not found. Or (better) eliminate this method!
		if (user==null) return;
		user.setEmail(email);
		user.update();
	}

	public static void changePassword(String username, String current_password, String password, String retype_password) {
		User user = find.where()
					    .eq("username", username)
					    .findUnique();
		if (user.getPassword().equals(current_password) && password.equals(retype_password)) {
			user.setPassword(password);
			user.update();
		}
	}
	
	// Admin
	public static void addUser(String username, String password, String email, Long role_id) {
		User user = find.where()
				.eq("username", username)
				.findUnique();
		if (user == null) {
//			Role role = Role.find.where().eq("id", role_id).findUnique();
			Role role = Role.find.byId(role_id);
			User newUser = new User(username, password, email, role);
			newUser.save();
		}
	}
	
	public static void adminUpdateUser(Long id, String username, String password, String email, Long role_id) {
		User user = find.ref(id);
		if (user != null) {
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			Role role = Role.find.byId(role_id);
			user.setRole(role);
			user.update();
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		if (role == null) throw new RuntimeException("User's role may not be null");
		this.role = role;
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
	
	/**
	 * Return a string description of this user.
	 * @return the user's named
	 */
	public String toString() {
		return username;
	}
}
