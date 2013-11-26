package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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
	
	@ManyToMany
	public List<Ballot> ballots = new ArrayList<Ballot>();
	
	public static Finder<Long, Vote> find = new Finder<Long, Vote>(Long.class, Vote.class);

	public Vote() {
		
	}
	
	public Vote(User user, Criterion criterion) {
		this.user = user;
		this.criterion = criterion;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
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
	
	public List<Ballot> getBallots() {
		return ballots;
	}

	public void setBallots(List<Ballot> ballots) {
		this.ballots = ballots;
	}
	
	public int getBallotsNumber() {
		return ballots.size();
	}
	
	public void addBallot(Ballot ballot) {
		ballots.add(ballot);
	}

	public String toString() {
		return "";
	}

}
