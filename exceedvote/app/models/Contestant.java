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
public class Contestant extends Model {

	@Id
	public Long id;
	
	@Constraints.Required
	public String name;
	
	public String description;
	
	public static Finder<Long, Contestant> find = new Finder<Long, Contestant>(Long.class, Contestant.class);
	
	public Contestant() {
		
	}
	
	public Contestant(String name, String description) {
		this.name = name;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return "";
	}
	
	// Add
	public int findScoreCriterion(Criterion criterion) {
		List<Vote> votesList = Vote.find.where().eq("criterion", criterion).findList();
		int scoreCriterion = 0;
		for (Vote vote : votesList) {
			List<Ballot> ballotsList = vote.getBallots();
			for (Ballot ballot : ballotsList) {
				if (ballot.getContestant().equals(this)) {
					scoreCriterion += ballot.getScore();
				}
			}
		}
		return scoreCriterion;
	}

	public static void addContestant(String name, String description) {
		Contestant contestant = find.where()
									.eq("name", name)
									.findUnique();
		if (contestant == null) {
			Contestant newContestant = new Contestant(name, description);
			newContestant.save();
		}
	}

	public static void updateContestant(Long id, String name, String description) {
		Contestant contestant = find.ref(id);
		if (contestant != null) {
			contestant.setName(name);
			contestant.setDescription(description);
			contestant.update();
		}
	}

}
