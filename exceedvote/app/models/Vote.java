package models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

// import org.codehaus.jackson.annotate.JsonIgnore;

import play.db.ebean.*;
import play.data.validation.*;

/**
 * 
 * @author Sonny
 *
 */
@Entity
public class Vote extends Model {
	
	@Id
	public Long id;
	
	@OneToOne
	public User user;
	
	@OneToOne
	public Criterion criterion;
	
	@ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "vote", nullable = false)
	public Map<Contestant, Integer> vote = new HashMap<Contestant, Integer>();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Criterion getCriterion() {
		return criterion;
	}

	public void setCriterion(Criterion criterion) {
		this.criterion = criterion;
	}

	public Map<Contestant, Integer> getVote() {
		return vote;
	}

	public void setVote(Map<Contestant, Integer> vote) {
		this.vote = vote;
	}
	
	public String toString() {
		return "";
	}

}
