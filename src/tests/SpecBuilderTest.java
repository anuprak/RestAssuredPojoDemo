package tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlaceMethodSerializeAndDeserialize;
import pojo.LocationsSerializeAndDeserialize;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		// Basic serialization of data here
		AddPlaceMethodSerializeAndDeserialize addRequest = new AddPlaceMethodSerializeAndDeserialize();
		addRequest.setAccuracy(50);
		addRequest.setAddress("201 abc Road");
		addRequest.setLanguage("English");
		addRequest.setName("Anusha");
		addRequest.setPhone_number("123-456-7890");
		addRequest.setWebsite("https://TryingSerializing.com");

		List<String> ls = new ArrayList<String>();
		ls.add("shoe park");
		ls.add("Dont know");
		addRequest.setTypes(ls);

		LocationsSerializeAndDeserialize locationData = new LocationsSerializeAndDeserialize();
		locationData.setLat(-38.234);
		locationData.setLng(33.456);
		addRequest.setLocation(locationData);
		
		//Request and Response Spec Builders
		
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		RequestSpecification addPlaceRequest = given().spec(requestSpec).body(addRequest);

		//Running the actual request
		Response addPlaceResponse = addPlaceRequest.when().log().all().post("/maps/api/place/add/json").then()
				.spec(responseSpec).extract().response();

		System.out.println("THIS IS THE RESPONSE: \n" + addPlaceResponse.asString());
		// Use Pojo classes to read the response

	}

}
