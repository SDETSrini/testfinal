package com.qa.stepdefn;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.json.JSONObject;

import com.qa.base.Base;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReadJSONData  extends Base  {

	public static void main(String[] args) throws IOException {
		
		String newauthtoken = "http://vf-cm-dev-pcm-ps-alb-1058215861.us-east-1.elb.amazonaws.com/tbl/v1/authenticate";
		String fileauth = "C:\\vfc-salsify-test\\vfc-salsifytest\\src\\main\\resources\\auth.json";
		String bearerToken;

		
		
		String authJson = GenerateStringFromResource(fileauth);

		Response response = given().header("Content-Type", "application/json").body(authJson).when()
				.post(newauthtoken);
		bearerToken = response.header("authorization");
		System.out.println(bearerToken);
//    	
		// Write code to do a GET Call & Verify StatusCode and Assert that

		String URL = "http://vf-cm-dev-pcm-ps-alb-1058215861.us-east-1.elb.amazonaws.com/tbl/v1/product/"
				+ "PRODUCT001";
		System.out.println(URL);
		int statusCode = given().header("Content-Type", "application/json").header("Authorization", bearerToken)
				.when().get(URL).statusCode();
		
		System.out.println(statusCode);

		String resBody = given().header("Content-Type", "application/json").header("Authorization", bearerToken)
				.when().get(URL).asString();
		
		
		JSONObject js = new JSONObject(resBody);
		
		
		String JsonITEMPartnumber  = (String) js.get("partNumber");
		
		
//		String JsonITEMPartnumber  = (String) js.get("partNumber");
//		String JsonITEMNameENUS = (String) ((JSONObject) js.get("name")).get("en_US");
		
		String JsonITEMNameENUS = String.valueOf(((JSONObject) js.get("name")).get("en_US"));
		
		System.out.println(JsonITEMPartnumber);
		System.out.println(JsonITEMNameENUS);
		System.out.println(statusCode);
		

	}

}
