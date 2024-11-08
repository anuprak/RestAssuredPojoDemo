package tests;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.ECommerceAddProductResponse;
import pojo.ECommerceCreateOrderRequest;
import pojo.ECommerceCreateOrderResponse;
import pojo.ECommerceLoginRequest;
import pojo.ECommerceLoginResponse;
import pojo.ECommerceOrderInformation;

public class ECommerceAPITests {
	
	String loginToken;
	String userId;
	String token;
	String baseUri = "https://rahulshettyacademy.com";
	String imageUrl = "";
	String productId;
	
	@Test(priority=1)
	public void login()
	{
		ECommerceLoginRequest loginRequest = new ECommerceLoginRequest();
		loginRequest.setUserEmail("");
		loginRequest.setUserPassword("");

		RequestSpecification LoginRequestSpec = new RequestSpecBuilder().setBaseUri(baseUri)
				.setContentType(ContentType.JSON).build();

		RequestSpecification request = given().spec(LoginRequestSpec).body(loginRequest);

		ECommerceLoginResponse response = request.when().post("/api/ecom/auth/login").then().extract()
				.response().as(ECommerceLoginResponse.class);
		
		loginToken = response.getToken();
		userId = response.getUserId();
		token = response.getToken();
		//System.out.println(response.getToken());
		//System.out.println(response.getUserId());
	}
	
	@Test(priority=2)
	public void createProduct()
	{
		RequestSpecification addProductRequestSpec = new RequestSpecBuilder().setBaseUri(baseUri)
				.addHeader("authorization", token).build();
		
		//Form parameters in this request, so use "param". For Form parameters, we cannot use Pojo classes
		RequestSpecification addProductRequest = given().log().all().spec(addProductRequestSpec)
		.param("productName", "TestProduct")
		.param("productAddedBy", userId)
		.param("productCategory", "testcategory")
		.param("productSubCategory", "testsubcategory")
		.param("productPrice", "100")
		.param("productDescription", "TestDescription")
		.param("productFor", "All")
		.multiPart("productImage", new File(imageUrl));
		
		ECommerceAddProductResponse response = addProductRequest.when().log().all()
		.post("/api/ecom/product/add-product")
		.then().log().all().extract().response().as(ECommerceAddProductResponse.class);
		
		productId = response.getProductId();
	}
	
	@Test(priority=3)
	public void createOrder()
	{
		RequestSpecification createOrderSpec = new RequestSpecBuilder().setBaseUri(baseUri)
			.addHeader("authorization", token).setContentType(ContentType.JSON).build();
		
		List<ECommerceOrderInformation> ordersList = new ArrayList<ECommerceOrderInformation>();
		ECommerceOrderInformation orderInfo = new ECommerceOrderInformation();
		orderInfo.setCountry("India");
		orderInfo.setProductOrderedId(productId);
		ordersList.add(orderInfo);
		
		ECommerceCreateOrderRequest orders = new ECommerceCreateOrderRequest();
		orders.setOrders(ordersList);
		
		RequestSpecification createOrderRequest = given().log().all().spec(createOrderSpec)
				.body(orders);
		
		ECommerceCreateOrderResponse response = createOrderRequest.when().log().all()
				.post("/api/ecom/order/create-order")
				.then().log().all()
				.extract().response().as(ECommerceCreateOrderResponse.class);
		
		System.out.println(response.getMessage());
	}
	
	@Test(priority=4)
	public void deleteProduct()
	{
		RequestSpecification deleteProduct = new RequestSpecBuilder().setBaseUri(baseUri)
				.addHeader("authorization", token).build();
		
		RequestSpecification deleteProductRequest = given().log().all().spec(deleteProduct).pathParam("productId", productId);
		
		String response = deleteProductRequest.when().log().all().delete("/api/ecom/product/delete-product/{productId}")
				.then().log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(response);
		Assert.assertEquals("Product Deleted Successfully", js.get("message"));
		
		
		
	
	}

}
