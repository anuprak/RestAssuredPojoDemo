package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlaceMethodSerializeAndDeserialize;
import pojo.LocationsSerializeAndDeserialize;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializeTest {

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
		
		List<String> ls =new ArrayList<String>();
		ls.add("shoe park");
		ls.add("Dont know");
		
		addRequest.setTypes(ls);

		LocationsSerializeAndDeserialize locationData =new LocationsSerializeAndDeserialize();
		locationData.setLat(-38.234);
		locationData.setLng(33.456);

		addRequest.setLocation(locationData);

		Response addPlaceResponse = given().log().all().queryParam("key", "qaclick123").body(addRequest).when().log()
				.all().post("/maps/api/place/add/json").then().assertThat().statusCode(200).extract().response();

		System.out.println("THIS IS THE RESPONSE: \n" + addPlaceResponse.asString());
		// Use Pojo classes to read the response

	}

}
