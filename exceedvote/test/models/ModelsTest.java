import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;

import models.Contestant;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*
;
import play.libs.*;
import com.avaje.ebean.Ebean;

public class ModelsTest	{
	@Test
	public void createAndRetrieveUser() {
    	Contestant c = new Contestant("Project","Some Description");
    	Contestant con = Contestant.find.where().eq("name","Project").findUnique();
    	// assertNotNull(con);
    	// assertEquals("Project",con.name);
    	assertThat(con).isNull();
	}
}