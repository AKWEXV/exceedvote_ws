import java.util.*;

import com.avaje.ebean.*;
import play.*;
import play.libs.*;

import models.*;

public class Global extends GlobalSettings {
	
	static class InitialData {
		/**
		 * Insert initial data from file.
		 * @param app reference to this application
		 */
		public static void insert(Application app) {
			if (Ebean.find(Contestant.class).findRowCount() == 0) {
				Map<String, List<Object>> all = (Map<String, List<Object>>)Yaml.load("initial-data.yml");
				Ebean.save(all.get("contestants"));
				Ebean.save(all.get("criteria"));
				// Save a "null" role for users without role.
				Role.NONE.setId(0L);
				Ebean.save(Role.NONE);
				// this will fail if any roles defined in initialization file have id 0 or conflicts
				Ebean.save(all.get("roles"));
				Ebean.save(all.get("users"));
				// Ebean.save(all.get("ballots"));
				// Ebean.save(all.get("votes"));
				// for(Object vote: all.get("votes")) {
    //                 // Insert the ... relation
    //                 Ebean.saveManyToManyAssociations(vote, "ballots");
    //             }
				Ebean.save(all.get("timers"));
			}
		}
	}

	public void onStart(Application app) {
		InitialData.insert(app);
	}

}