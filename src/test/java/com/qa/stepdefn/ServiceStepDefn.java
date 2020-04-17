package com.qa.stepdefn;

import com.qa.base.TestBase;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;
import java.util.ResourceBundle;

import io.restassured.http.ContentType;

import org.hamcrest.Matchers;
import org.hamcrest.Matchers.*;
//import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;



public class ServiceStepDefn extends TestBase{
	
	TestBase tb = new TestBase();
	String URL;
	ValidatableResponse res;
	RequestSpecification reqSpec;
	int statuscode;
	public static String  bearerToken;
	
	String response;
	String jsonBody1;
	String jsonBody2;
	String filepath;
	
	String newauthtoken = "http://vf-cm-dev-pcm-ps-alb-1058215861.us-east-1.elb.amazonaws.com/tbl/v1/authenticate";	
	String fileauth =  System.getProperty("user.dir") + "\\src\\main\\resources\\auth.json";
	
	
	@Given("^the end point is \"([^\"]*)\" for \"([^\"]*)\"$")
	public void the_end_point_is_for(String arg1, String arg2) throws Throwable {
		URL = tb.setUp(arg1,arg2);
			
	}
	
	@Given("^the body is \"([^\"]*)\" json$")
	public void the_body_is_json(String arg1) throws IOException {
		
		
		String filepath = System.getProperty("user.dir")+ "\\src\\main\\resources\\" +arg1 +".json";
		jsonBody1 = GenerateStringFromResource(filepath);
		
	   
	}
	
	@Given("^the method is \"([^\"]*)\" for \"([^\"]*)\"$")
	public void the_method_is_for(String arg1, String arg2) throws Throwable {
		
		
		
		switch(arg1)
		{
		case "GET": 
			response = given().header("Content-Type","application/json").header("Authorization",bearerToken).when().get(URL).asString();
			System.out.println(response);
			break;
			
		case "POST":
			filepath = System.getProperty("user.dir")+ "\\src\\main\\resources\\" +arg2 +".json";
			System.out.println(filepath);
			jsonBody2 = GenerateStringFromResource(filepath);
			response  = given().header("Content-Type","application/json").header("Authorization",bearerToken).body(jsonBody2).when().post(URL).asString();
			System.out.println(response);	
			statuscode = given().header("Content-Type","application/json").header("Authorization",bearerToken).body(jsonBody2).when().post(URL).statusCode();
			System.out.println(statuscode);
			break;
			
		case "PUT":
			filepath = System.getProperty("user.dir")+ "\\src\\main\\resources\\" +arg2 +".json";
			System.out.println(filepath);
			jsonBody2 = GenerateStringFromResource(filepath);
			response  = given().header("Content-Type","application/json").header("Authorization",bearerToken).body(jsonBody2).when().put(URL).asString();
			System.out.println(response);
			statuscode = given().header("Content-Type","application/json").header("Authorization",bearerToken).body(jsonBody2).when().put(URL).statusCode();
			System.out.println(statuscode);
			break;
			
		default:
			break;
			
			
		}
	   
	}
	
	
	@Given("^User perform authentication operation$")
	public String user_perform_authentication_operation() throws Throwable {
		
		String authJson = GenerateStringFromResource(fileauth);
		
		Response response = given().header("Content-Type","application/json").body(authJson).when().post(newauthtoken);
		bearerToken = response.header("authorization");
		System.out.println(bearerToken);
		
		return bearerToken;

	}

	
	@Then("^status code for POST is (\\d+)$")
	public void status_code_for_POST_is(int arg1) throws IOException {
	
		Assert.assertEquals(arg1, statuscode);
	}
	
	
	@Then("^status code for PUT is (\\d+)$")
	public void status_code_for_PUT_is(int arg1) throws Throwable {
		int scode  = given().header("Content-Type","application/json").header("Authorization",bearerToken).body(GenerateStringFromResource(jsonBody2)).when().put(URL).statusCode();
		System.out.println(scode);
		Assert.assertEquals(RESPONSE_STATUS_CODE_200, scode);
	    
	}
	
	@Then("^status code is (\\d+)$")
	public void status_code_is(int arg1)  {
		
		int statuscode = given().header("Content-Type","application/json").header("Authorization",bearerToken).when().get(URL).statusCode();
		Assert.assertEquals(arg1, statuscode);

	}
	

	@Then("^the response body is in json format$")
	public void the_response_body_is_in_json_format()  {
		
		given().header("Content-Type","application/json").header("Authorization",bearerToken).when().get(URL).then().assertThat().contentType(ContentType.JSON);

	    
	}
	
	@Then("^the response body is in json \"([^\"]*)\"$")
	public void the_response_body_is_in_json(String arg1)  {
		
		String filepath = arg1 + ".json";	
		given().header("Content-Type","application/json").header("Authorization",bearerToken).when().get(URL).then().assertThat().body(matchesJsonSchemaInClasspath(filepath));
		
	}
	

	
	@Then("^verify mandatory fields \"([^\"]*)\"$")
	public void verify_mandatory_fields(String arg1)  {
		
		String field = arg1;
		System.out.println("Verifying the data " +field);
		
		res = given().when().get(URL).then().body(field, Matchers.notNullValue());
		
			
	}
	

	@Given("^the header contains Authorization Token$")
	public void the_header_contains_Authorization_Token() throws Throwable {
	    
	}
	
	
	
	
	
	
	
//--------------------------------------------------------------
//for later ref
//--------------------------------------------------------------
//	
	
//	
//	
//	@Given("^the body is \"([^\"]*)\" and \"([^\"]*)\"$")
//	public void the_body_is_and(String arg1, String arg2) {
//		TestBase.createbody(arg1,arg2);
//	}
//	
//	
//	
//	
//	@Given("^the header is \"([^\"]*)\" and \"([^\"]*)\"$")
//	public void the_header_is_and(String arg1, String arg2) {
//		TestBase.createheader(arg1,arg2);
//	    
//	}
//	
//	@Given("^the method is \"([^\"]*)\" with header$")
//	public void the_method_is_with_header(String arg1)  {
//		
//		response  = given().pathParams(k)
//		  .when().get(URL)
//		  .asString();
//			System.out.println(response);
//	   
//	}
//	
//	
	
	
	
	


}
