package models;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

/**
 * Ballot collects scores for each contestant given by voter.
 * 
 * @author Kanokphol
 *
 */
@Entity
public class Ballot extends Model {
	
	@Id
	public Long id;
	
	@OneToOne
	public Contestant contestant;
	
	public int score;
	
	public static Finder<Long, Ballot> find = new Finder<Long, Ballot>(Long.class, Ballot.class);

	public Ballot() {
		
	}
	
	public Ballot(Contestant contestant, int score) {
		this.contestant = contestant;
		this.score = score;
	}
	
	public Contestant getContestant() {
		return contestant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setContestant(Contestant contestant) {
		this.contestant = contestant;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public String toString() {
		return "";
	}

}
