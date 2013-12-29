package models;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import play.db.ebean.*;
import play.data.validation.*;

public class Ranking extends Model {

	public Criterion criterion;
	public List<RankedContestant> contestants;

	public class RankedContestant implements Comparable<RankedContestant> {
		public Long contestantId;
		public String contestantName;
		public int score;
		public int rank;

		public RankedContestant(Long id, String name, int score) {
			this.contestantId = id;
			this.contestantName = name;
			this.score = score;	
		}

		public Long getId() {
			return this.contestantId;
		}

		public String getName() {
			return this.contestantName;
		}
		
		public int getScore() {
			return this.score;
		}

		public int getRank() {
			return this.rank;
		}

		public int compareTo(RankedContestant compareContestant) {
			int compareScore = ((RankedContestant) compareContestant).getScore(); 
			return compareScore - this.score;
		}
	}

	public Ranking(Criterion criterion, List<Contestant> unrankContestants) {
		this.criterion = criterion;
		contestants = new ArrayList<RankedContestant>();
		for (Contestant ctt : unrankContestants) {
			RankedContestant rctt = new RankedContestant(ctt.getId(), ctt.getName(), ctt.findScoreCriterion(this.criterion));
			contestants.add(rctt);
		}
		Collections.sort(contestants);
		int rank = 1;
		for (RankedContestant ctt : contestants) {
			ctt.rank = rank;
			rank++;
		}
	}

	public Criterion getCriterion() {
		return this.criterion;
	}

	public List<RankedContestant> getContestants() {
		return this.contestants;
	}

}
