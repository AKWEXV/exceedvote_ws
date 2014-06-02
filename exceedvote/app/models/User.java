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
	
	@Constraints.Required
	public String username;
	
	@Constraints.Required
	public String password;
	
	public String email;
	
	public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
	
	public User() {
		
	}
	
	public User(String username, String password, String email, Role role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
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
     */
    public static User authenticate(String username, String password) {
        return find.where()
            .eq("username", username)
            .eq("password", password)
            .findUnique();
    }

    /**
	 * find user by username.
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
			Role role = Role.find.where().eq("id", role_id).findUnique();
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
			user.setRole(Role.find.where().eq("id", role_id).findUnique());
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
	
	public String toString() {
		return "";
	}

}
