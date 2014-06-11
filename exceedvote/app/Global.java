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
				// this will fail if any roles defined in initialization file have id 0 or conflicts
				Ebean.save(all.get("roles"));
				// Save a "null" role for users without role. But only if it isn't in database already.
				Role norole = Role.findByName(Role.NONE.getName());
				if (norole == null) {
					// hasn't been added to database yet
					long id = Role.find.getMaxRows() + 1;
					Role.NONE.setId(id); // hack, hack.
					Ebean.save(Role.NONE);
				}
				else {
					// set the id to match role in database
					Role.NONE.setId(norole.getId());
				}
				// force synchronization?
				List<Role> roles = Role.find.all();
				
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