import java.util.*;

import com.avaje.ebean.*;
import play.*;
import play.libs.*;

import models.*;

public class Global extends GlobalSettings {

	static class InitialData {
		public static void insert(Application app) {
//			if (Ebean.find(User.class).findRowCount() == 0) {
//				Map<String, List<Object>> all = (Map<String, List<Object>>)Yaml.load("initial-data.yml");
//				Ebean.save(all.get("role"));
//			}
		}
	}

	public void onStart(Application app) {
		InitialData.insert(app);
	}

}