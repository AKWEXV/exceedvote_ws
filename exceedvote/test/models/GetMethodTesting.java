package models;

import com.jayway.restassured.RestAssured;
import org.junit.*;

import play.test.WithApplication;



import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;
import static play.test.Helpers.*;
import static org.hamcrest.Matchers.*;

public class GetMethodTesting extends WithApplication {
	private static final int PORT = 3333;

    @Before
    public void setUp()
    {
        RestAssured.port = PORT;
    }

    @After
    public void tearDown()
    {
        RestAssured.reset();
    }

    @Test
    public void testGetAll_Contestant()
    {
        running(testServer(PORT), new Runnable()
        {
            @Override
            public void run()
            {
            		RestAssured.expect().statusCode(200).when().get("/exceedvote/api/v1/contestant");
            		RestAssured.get("/exceedvote/api/v1/contestant").then().assertThat().body(notNullValue());
            }
        });
    }
    
    @Test
    public void testGetSpecificContestantByID()	{
    		running(testServer(PORT),new Runnable()
    		{
			@Override
			public void run() {
			// TODO Auto-generated method stub
            		RestAssured.expect().statusCode(200).when().get("/exceedvote/api/v1/contestant/1");
            		RestAssured.get("/exceedvote/api/v1/contestant/1").then().assertThat().body(
            				"contestant.id",equalTo(Integer.toString(1)),
            				"contestant.name",equalTo("Organizer"),
            				"contestant.description",equalTo("By Simon Adrian Jens")
            						);
			}
    		});
    }
    @Test
    public void testGetAll_Criterion()	{
    		running(testServer(PORT), new Runnable()	
    		{
    			@Override
    			public void run()	{
            		RestAssured.expect().statusCode(200).when().get("/exceedvote/api/v1/criterion");
            		RestAssured.get("/exceedvote/api/v1/criterion").then().assertThat().body(notNullValue());
    			}
    		});
	}
    
    @Test
    public void testGetSpecificCriterionByID()	{
    		running(testServer(PORT),new Runnable()
    		{
    			@Override
    			public void run()	{
            		RestAssured.expect().statusCode(200).when().get("/exceedvote/api/v1/criterion/1");
            		RestAssured.get("/exceedvote/api/v1/criterion/1").then().assertThat().body(
            				"criterion.id",equalTo(Integer.toString(1)),
            				"criterion.name",equalTo("Popular Vote"),
            				"criterion.type",equalTo(Integer.toString(1))
            				);
    			}
    		});
    }
    @Test
    public void testMyVote()	{
    		running(testServer(PORT), new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				User user = User.findByUsername("apiwat");
				RestAssured.given().auth().digest("apiwat", "apiwat").when().get("/exceedvote/api/v1/myvote").then().statusCode(204);
				RestAssured.given().auth().digest("badId", "badPassword").when().get("/exceedvote/api/v1/myvote").then().statusCode(401);
				
			}
    		});
    }
    
    
}
