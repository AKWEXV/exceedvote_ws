import java.util.*;

import com.avaje.ebean.*;
import play.*;
import play.libs.*;

import models.*;

public class Global extends GlobalSettings {

	static class InitialData {
		public static void insert(Application app) {
			if (Ebean.find(Contestant.class).findRowCount() == 0) {
				Map<String, List<Object>> all = (Map<String, List<Object>>)Yaml.load("initial-data.yml");
				Ebean.save(all.get("contestants"));
				Ebean.save(all.get("criteria"));
				Ebean.save(all.get("roles"));
				Ebean.save(all.get("users"));
				Ebean.save(all.get("ballots"));
				Ebean.save(all.get("votes"));
				for(Object vote: all.get("votes")) {
                    // Insert the ... relation
                    Ebean.saveManyToManyAssociations(vote, "ballots");
                }
			}
		}
	}

	public void onStart(Application app) {
		InitialData.insert(app);
	}

}