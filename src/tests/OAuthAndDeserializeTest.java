package tests;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.ApiCoursesSerializeAndDeserialize;
import pojo.GetCoursesMethodSerializeAndDeserialize;
import pojo.WebAutomationCoursesSerializeAndDeserialize;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

public class OAuthAndDeserializeTest {

	public static void main(String[] args) {
		
		//Generate an access token with grant type client_credentials
		
		String[] expectedCourseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token";
		String response = given()
		.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().log().all().post().then().assertThat().statusCode(200)
		.extract().response().asString();
		
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		
		String token = js.getString("access_token");
		
		//Use this access token in the test case
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/oauthapi/getCourseDetails";
		
		//Instead of using JsonPath, lets use pojo classes to de-serialize the response.
		//The responseObj is used for de-serializing.
		GetCoursesMethodSerializeAndDeserialize responseObj = given()
		.queryParam("access_token", token)
		.when().log().all()
		.get()
		.then().log().all().assertThat().statusCode(401)//Status Code is incorrect, API issue.
		.extract().response().as(GetCoursesMethodSerializeAndDeserialize.class);
		
		//Linkedin URL using pojo classes
		System.out.println(responseObj.getLinkedIn());
		
		//Instructor using pojo classes
		System.out.println(responseObj.getInstructor());
		
		
		//Get the second api course's Title (first index) 
		System.out.println(responseObj.getCourses().getApi().get(1).getCourseTitle());
		
		//Get price for api's second course "SoapUI Webservices Testing", iterate through all API courses
		List<ApiCoursesSerializeAndDeserialize> apiCourses = responseObj.getCourses().getApi();
		
		for(int i=0; i<apiCourses.size();i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices Testing"))
				System.out.println(apiCourses.get(i).getPrice());
		}
		
		//Print all course titles of Web Automation
		List<WebAutomationCoursesSerializeAndDeserialize> webCourses = responseObj.getCourses().getWebAutomation();
		
		for(int i=0;i<webCourses.size();i++)
		{
			System.out.println(webCourses.get(i).getCourseTitle());
		}
		
		//Compare web automation course titles match with our expected course list.
		ArrayList<String> courseTitles = new ArrayList<String>();
		
		for(int i=0;i<webCourses.size();i++)
		{
			courseTitles.add(webCourses.get(i).getCourseTitle());
		}
		//Arrays.asList() method converts arrays into an array list
		System.out.println(Arrays.asList(expectedCourseTitles).equals(courseTitles));
		
		Assert.assertTrue(courseTitles.equals(Arrays.asList(expectedCourseTitles)));
		
	}

}
