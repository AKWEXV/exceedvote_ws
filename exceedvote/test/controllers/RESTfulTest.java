package controllers;

import models.Criterion;
import models.User;

import org.hamcrest.xml.HasXPath;
import org.junit.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import java.util.*;

import play.mvc.*;
import play.libs.*;
import play.test.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import com.avaje.ebean.Ebean;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import com.google.common.collect.ImmutableMap;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.xml.element.Node;

public class RESTfulTest extends WithApplication {

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
    public void testVote()	{
    		running(testServer(PORT), new Runnable() {
			@Override
			public void run() {
				//before Vote
				User user = User.authenticate("apiwat","apiwat");
				RestAssured.given()
								.auth().digest(user.username, user.password)
							.when()
								.get("/exceedvote/api/v1/myvote")
							.then()
								.statusCode(200);
				
				RestAssured.given()
								.auth().digest("badId", "badPassword")
							.when()
								.get("/exceedvote/api/v1/myvote")
							.then()
								.statusCode(401);
				
				String vote_xml = "<vote>\n" +
									"<contestants>\n" +
										"<contestant>\n" +
											"<id>1</id>\n" +
											"<score>9</score>\n" +
										"</contestant>\n"+
										"<contestant>\n" +
											"<id>2</id>\n" +
											"<score>7</score>\n" +
										"</contestant>\n" +
									"</contestants>\n" +
								"</vote>";
				
				
				RestAssured.given()
								.contentType(ContentType.XML)
								.content(vote_xml)
						.when()
								.auth().digest(user.username, user.password)
								.post("/exceedvote/api/v1/criterion/3/vote")
						.then()
								.statusCode(201);
				
			}
    		});
    }
    @Test
    public void testRanking()	{
    		running(testServer(PORT), new Runnable()	{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String expectedRoot = "rank";
				String actualRoot= RestAssured.get("exceedvote/api/v1/rank").xmlPath().get().name();
				assertEquals(expectedRoot, actualRoot);
			}
    		});
    }
}