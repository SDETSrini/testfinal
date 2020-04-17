package com.qa.stepdefn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudsearchdomain.model.Bucket;
import com.amazonaws.services.ecs.AmazonECS;
import com.amazonaws.services.ecs.AmazonECSClientBuilder;
import com.amazonaws.services.ecs.model.AwsVpcConfiguration;
import com.amazonaws.services.ecs.model.LaunchType;
import com.amazonaws.services.ecs.model.NetworkConfiguration;
import com.amazonaws.services.ecs.model.RunTaskRequest;
import com.amazonaws.services.ecs.model.RunTaskResult;
import com.amazonaws.services.ecs.model.StartTaskRequest;
import com.amazonaws.services.ecs.model.StartTaskResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.opencsv.exceptions.CsvException;
import com.qa.base.Base;
import com.qa.base.TestBase;

public class E2EFinalStepDefn extends Base {

	WebDriver driver;

	final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
	String ProcessedFilename;
	public static String bearerToken;

	List<JSONObject> list1 = new ArrayList<JSONObject>();
	List<JSONObject> list2 = new ArrayList<JSONObject>();
	List<JSONObject> list3 = new ArrayList<JSONObject>();
	List<JSONObject> list4 = new ArrayList<JSONObject>();
	List<JSONObject> list5 = new ArrayList<JSONObject>();
	List<JSONObject> list6 = new ArrayList<JSONObject>();
	
	
	String JsonPRODUCTPartnumber;
	String JsonPRODUCTNameENUS;

	String newauthtoken = "http://vf-cm-dev-pcm-ps-alb-1058215861.us-east-1.elb.amazonaws.com/tbl/v1/authenticate";
	String fileauth = System.getProperty("user.dir") + "\\src\\main\\resources\\auth.json";
		
	
	// Created by Srinivas Naika on 16/03/2020
	// Updated by
	@Given("Product file {string} is available with valid data in drop location {string}")
	public void product_file_is_available_with_valid_data_in_drop_location(String string, String string2) {

		System.out.println("Product file" + string + "is available with valid data in drop location" + string2);

		ProcessedFilename = string;
		String file_path = System.getProperty("user.dir") + "\\src\\main\\resources\\SALSIFYJSON\\" + string;
		String bucket_name = string2;
		String key_name = Paths.get(file_path).getFileName().toString();

		System.out.format("Uploading %s to S3 bucket %s...\n", file_path, bucket_name);

		try {
			s3.putObject(bucket_name, key_name, new File(file_path));
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}

		System.out.println("Completed Uploading the File");

	}
	
	
	
	// Created by Srinivas Naika on 16/03/2020
	// Updated by
	@When("Lambda is invoked to process and ETL for Salsify is Run for {string}")
	public void lambda_is_invoked_to_process_and_ETL_for_Salsify_is_Run_for(String string) throws InterruptedException {

		System.out.println("Lambda is invoked to process and ETL for Salsify is Running");

		boolean flag = false;

		Thread.sleep(150000);

		ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
				.withBucketName("vf-cm-dev-pcm-s3-store-messages-bucket").withPrefix(string).withEncodingType("url");

		ObjectListing ObjectListing = s3.listObjects(listObjectsRequest);
		List<S3ObjectSummary> objects = ObjectListing.getObjectSummaries();
		System.out.println("Printing all the files in the folder");
		System.out.println(objects.size());

		System.out.println("Lambda is invoked to process and ETL for Salsify is Completed");

		for (int i = 0; i < objects.size(); i++)

		{
			String OriginalFillename = objects.get(i).getKey().replace(string + "/", "");
			System.out.println(OriginalFillename);
			if (ProcessedFilename.equalsIgnoreCase(OriginalFillename)) {
				flag = true;
				System.out.println("File is found in " + string);
				break;

			}

		}

		System.out.println(flag);
		Assert.assertTrue(flag);

	}
	
	
	
	
	// Created by Srinivas Naika on 16/03/2020
	// Updated by
	@Then("Verify Whether ETL for Salsify is {string}")
	public void verify_Whether_ETL_for_Salsify_is(String string) {

		String Result = string;
		System.out.println(Result);

		if (Result.equalsIgnoreCase("Pass")) {
			System.out.println("Test Case is Passed");
		}

	}
	
	
	
	// Created by Srinivas Naika on 16/03/2020
	// Updated by
	@Then("PRODUCT is available in ProductService")
	public void product_is_available_in_ProductService() throws Exception {

		System.out.println("Executing Step - Started - PRODUCT is available in ProductService ");

		String filename = System.getProperty("user.dir") + "\\src\\main\\resources\\SALSIFYJSON\\" + ProcessedFilename;
		System.out.println(filename);
		String json = getFileContent(new File(filename));

		JSONArray jsonArray = new JSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			JSONObject modified = formateJSON(obj);
			list1.add(modified);
		}

		// System.out.println(list);
		List<JSONObject> result = FilterProductsBasedOnType(list1, "type", "Style");
		System.out.println("result : " + result);
		System.out.println(result.size());

		for (int i = 0; i < result.size(); i++) {

			String prodPartNumber = (String) result.get(i).get("identifier");
			System.out.println(prodPartNumber);
			if (prodPartNumber.startsWith("TB0")) {
				prodPartNumber = prodPartNumber.replace("TB0", "");
			}

			String authJson = GenerateStringFromResource(fileauth);

			Response response = given().header("Content-Type", "application/json").body(authJson).when()
					.post(newauthtoken);
			bearerToken = response.header("authorization");
			System.out.println(bearerToken);

			// Write code to do a GET Call & Verify StatusCode and Assert that

			String URL = "http://vf-cm-dev-pcm-ps-alb-1058215861.us-east-1.elb.amazonaws.com/tbl/v1/product/"
					+ prodPartNumber;
			System.out.println(URL);
			int statusCode = given().header("Content-Type", "application/json").header("Authorization", bearerToken)
					.when().get(URL).statusCode();
			System.out.println(statusCode);
			
			//Verifying the Status Code  - Whether Product is Present in the ProductService		
			Assert.assertEquals(200, statusCode);
			System.out.println("Product is Present in the ProductService");
			
//			String resBody = given().header("Content-Type", "application/json").header("Authorization", bearerToken)
//			             .when().get(URL).asString();
			
//			JsonPath js = new JsonPath(resBody);
//			JsonPRODUCTPartnumber  = js.get("partNumber");
//			JsonPRODUCTNameENUS = (String) ((JSONObject) js.get("name")).get("en_US");
			
		
		
			

//        	Write Code to fetch the data of LongDescription EN US from Response using GET Call
//        	String LongDescResponse_ENUS;
//        	//Write Code to fetch the data of LongDescription EN US from JSON	
//        	JSONObject object = (JSONObject) ((JSONArray) result.get(i).get("longDescription (localized)")).get(0);
//        	String LongDescJSON_ENUS =   String.valueOf(object.get("enUS"));
//        	System.out.println(LongDescJSON_ENUS);

		}

		System.out.println("Executing Step - Completed - PRODUCT is available in ProductService ");

	}

	@Then("ITEM is available in ProductService")
	public void item_is_available_in_ProductService() throws Exception {

		System.out.println("Executing step - Started - ITEM is available in ProductService");

		String filename = System.getProperty("user.dir") + "\\src\\main\\resources\\SALSIFYJSON\\" + ProcessedFilename;
		String json = getFileContent(new File(filename));

		JSONArray jsonArray = new JSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			JSONObject modified = formateJSON(obj);
			list2.add(modified);
		}

		// System.out.println(list);
		List<JSONObject> result1 = FilterProductsBasedOnType(list2, "type", "StyleColor");
		System.out.println("Full JSON  : " + result1);
		System.out.println(result1.size());
		
		System.out.println("--------------------------------------------------------------------------");
		
		for (int i = 0; i < result1.size(); i++) {

			String itemPartNumber = (String) result1.get(i).get("identifier");
			System.out.println("ITEM PartNumber is : " + itemPartNumber);

		}
		
		System.out.println("--------------------------------------------------------------------------");
		
		List<JSONObject> result2 = FilterProductsBasedOnType(list2, "type", "StyleColor+Size");
		System.out.println("Full JSON  : " + result2);
		System.out.println(result2.size());
		
		System.out.println("--------------------------------------------------------------------------");
		
		
		for (int i = 0; i < result1.size(); i++) {
			
			

			String itemPartNumber = (String) result1.get(i).get("identifier");
			System.out.println("ITEM PartNumber is : " +itemPartNumber);
			
//			String itemColorCode = (String) result.get(i).get("IPColorCode");
//			System.out.println("ITEM IPColorCode  : " +itemColorCode);
//			if(itemColorCode.equals("null"))
//			{ 
//				break;
//			
//			}
//			else {
//				
//				itemPartNumber = itemPartNumber.substring(0,(itemPartNumber.length()-3)).concat(itemColorCode);
//			}
			
			
			

			
//			if(!(result.get(i).get("IPColorCode") == null))
//			{
//				System.out.println(result.get(i).get("IPColorCode"));
//				String itemColorCode = (String) result.get(i).get("IPColorCode");
//				itemPartNumber = itemPartNumber.substring(0,(itemPartNumber.length()-3)).concat(itemColorCode);
//			}
//			
			
			
			if (itemPartNumber.startsWith("TB0")) {
				itemPartNumber = itemPartNumber.replace("TB0", "");
			}
			
			
			
			System.out.println("New ITEM Partnumber is before Accessing ProductService  " +itemPartNumber);
			
			String authJson = GenerateStringFromResource(fileauth);

			Response response = given().header("Content-Type", "application/json").body(authJson).when()
					.post(newauthtoken);
			bearerToken = response.header("authorization");
			System.out.println(bearerToken);
//        	
			// Write code to do a GET Call & Verify StatusCode and Assert that

			String URL = "http://vf-cm-dev-pcm-ps-alb-1058215861.us-east-1.elb.amazonaws.com/tbl/v1/item/"
					+ itemPartNumber;
			System.out.println(URL);
			int statusCode = given().header("Content-Type", "application/json").header("Authorization", bearerToken)
					.when().get(URL).statusCode();

//			String resBody = given().header("Content-Type", "application/json").header("Authorization", bearerToken)
//					.when().get(URL).asString();
//			
//			JsonPath js = new JsonPath(resBody);
//			String JsonITEMPartnumber  = js.get("partNumber");
//			String JsonITEMNameENUS = (String) ((JSONObject) js.get("name")).get("en_US");
			
//			System.out.println(JsonITEMPartnumber);
//			
			System.out.println(statusCode);
			
			if(statusCode!= 200)
			{
				System.out.println(result1.get(i).get("IPColorCode"));
				String itemColorCode = (String) result1.get(i).get("IPColorCode");
				itemPartNumber = itemPartNumber.substring(0,(itemPartNumber.length()-3)).concat(itemColorCode);
				URL = "http://vf-cm-dev-pcm-ps-alb-1058215861.us-east-1.elb.amazonaws.com/tbl/v1/item/"
						+ itemPartNumber;
				System.out.println(URL);
				statusCode = given().header("Content-Type", "application/json").header("Authorization", bearerToken)
						.when().get(URL).statusCode();
			
			}
			
				Assert.assertEquals(200, statusCode);

		}

		System.out.println("Executing step - Completed - ITEM is available in ProductService");

	}
	
	
	// Created by Srinivas Naika on 16/03/2020
	// Updated by

	@Then("ETL for WCS is run and Successful with {string} and {string}")
	public void etl_for_WCS_is_run_and_Successful_with_and(String string, String string2) throws InterruptedException {

		String cluster = string;
		String TaskDefn = string2;

		System.out.println("Executing Step  - Started - ETL for WCS");

		Thread.sleep(3000);

		AmazonECS client = AmazonECSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

		// AWS network configuration needed for the Fargate tasks
		AwsVpcConfiguration vpcConfiguration = new AwsVpcConfiguration();
		vpcConfiguration.setAssignPublicIp("DISABLED");
		// private subnet ids subnet-fe98f2b4","subnet-88f454a6
		// set the network details
		vpcConfiguration.setSubnets(Arrays.asList(new String[] { "subnet-fe98f2b4", "subnet-88f454a6" }));

		// you have to set security group as well, copy the group id
		vpcConfiguration.setSecurityGroups(Arrays.asList(new String[] { "sg-09314a0ce06214705" }));

		NetworkConfiguration networkConfiguration = new NetworkConfiguration();
		networkConfiguration.setAwsvpcConfiguration(vpcConfiguration);

		RunTaskRequest request = new RunTaskRequest().withCluster(cluster).withTaskDefinition(TaskDefn)
				.withLaunchType(LaunchType.FARGATE).withNetworkConfiguration(networkConfiguration);

		System.out.println(client);
		System.out.println(client.listClusters());
		System.out.println(client.listTaskDefinitions());
		RunTaskResult response = client.runTask(request);

		// Adding wait so that ETL for WCS job should be completed.
		Thread.sleep(120000);

		System.out.println("Executing Step - Completed - ETL for WCS !!!");

	}
	
	
	
	
	
	
	

	@Then("verify PRODUCT EN US data in {string}")
	public void verify_PRODUCT_EN_US_data_in(String string) throws InterruptedException, IOException, CsvException {

		String file = string;

		ListObjectsRequest listObjectsRequest1 = new ListObjectsRequest()
				.withBucketName("vf-cm-dev-pcm-s3-store-messages-bucket").withPrefix("ecom/data/TBL-NA/SALSIFY")
				.withEncodingType("url");

		ObjectListing ObjectListing = s3.listObjects(listObjectsRequest1);
		List<S3ObjectSummary> objects = ObjectListing.getObjectSummaries();
		System.out.println("Printing all the files in the folder - Main");
		System.out.println(objects.size());
		
		int value = (objects.size()-2);
		System.out.println(objects.get(value).getKey());
		
		String Name1 = objects.get(value).getKey().replace("ecom/data/TBL-NA/SALSIFY/", "");
		System.out.println(Name1);
		String folderName = "ecom/data/TBL-NA/SALSIFY/" + Name1.substring(0, Name1.indexOf("/"));
		System.out.println("Folder Name is  " +folderName);

		ListObjectsRequest listObjectsRequest2 = new ListObjectsRequest()
				.withBucketName("vf-cm-dev-pcm-s3-store-messages-bucket").withPrefix(folderName)
				.withEncodingType("url");

		ObjectListing ObjectListing1 = s3.listObjects(listObjectsRequest2);
		List<S3ObjectSummary> objects1 = ObjectListing1.getObjectSummaries();
		System.out.println("Printing all the files in the folder");
		System.out.println(objects1.size());

		for (int i = 0; i < objects1.size(); i++)

		{
			System.out.println(objects1.get(i).getKey());
		}

		// download csv from S3

		String bucket_name = "vf-cm-dev-pcm-s3-store-messages-bucket/" + folderName;
		System.out.println(bucket_name);

		String key_name = string;
		System.out.println(key_name);
	
		String fname= System.getProperty("user.dir") +"\\WCS_CSVFiles\\" +key_name;
		System.out.println(fname);
		
		System.out.format("Downloading %s from S3 bucket %s...\n", key_name, bucket_name);

		try {
			S3Object o = s3.getObject(bucket_name, key_name);
			S3ObjectInputStream s3is = o.getObjectContent();
			FileOutputStream fos = new FileOutputStream(
					new File(fname));
			byte[] read_buf = new byte[1024];
			int read_len = 0;
			while ((read_len = s3is.read(read_buf)) > 0) {
				fos.write(read_buf, 0, read_len);
			}
			s3is.close();
			fos.close();
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("Done!");

		// Wait for one Mintue
		TimeUnit.MINUTES.sleep(1);
		File f = new File(fname);
		while (!f.exists()) {
			TimeUnit.MINUTES.sleep(1);
			System.out.println("Waiting files to be created");
		}

		// Validate whether Product is present in the file

		// Read CSV File
		Base data = new Base();

		List<Map<String, String>> CSVData = data.ReadDataFromCSVFile(fname);
		for (Map<String, String> eachRow : CSVData)
			System.out.println(eachRow);

		// Filter Particular Row
		List<Map<String, String>> CSVFieldValue = data.FilterDataFromCSV(CSVData, "type", "BundleBean");
		
		for (Map<String, String> eachRow : CSVFieldValue) {
			
			String CSVPartNumber = eachRow.get("PartNumber".toLowerCase());
			String CSVName = eachRow.get("Name".toLowerCase());
			
			System.out.println(CSVPartNumber);
			
			String authJson = GenerateStringFromResource(fileauth);

			Response response = given().header("Content-Type", "application/json").body(authJson).when()
					.post(newauthtoken);
			bearerToken = response.header("authorization");
			System.out.println(bearerToken);
			
			// code to verify in PS and do comparison
			
			String URL = "http://vf-cm-dev-pcm-ps-alb-1058215861.us-east-1.elb.amazonaws.com/tbl/v1/product/"+ CSVPartNumber;
//			String URL = "http://vf-cm-qa-pcm-ps-alb-1519671188.us-east-1.elb.amazonaws.com/tbl/v1/product/" + CSVPartNumber;
			
			System.out.println(URL);
			int statusCode = given().header("Content-Type", "application/json").header("Authorization", bearerToken)
					.when().get(URL).statusCode();

			String resBody = given().header("Content-Type", "application/json").header("Authorization", bearerToken)
					.when().get(URL).asString();
			
			JSONObject js = new JSONObject(resBody);
			
			
			String JsonITEMPartnumber  = (String) js.get("partNumber");
			
			
//			String JSONNAME = (String) js.get("name");
			
			
//			String JsonITEMNameENUS =  (String) ((JSONObject) js.get("name")).get("en_US");
			
			System.out.println("JSON Partnumber "+JsonITEMPartnumber);
//			System.out.println("JSON Name EN US "+JsonITEMNameENUS);
			
			
			System.out.println("CSV Partnumber "+CSVPartNumber);
			System.out.println("CSV Name "+CSVName);
			
			Assert.assertEquals(JsonITEMPartnumber, CSVPartNumber);
			
			
		}

	}

	

	@Then("verify ITEM EN US data in {string}")
	public void verify_ITEM_EN_US_data_in(String string) {

		String file = string;

	}
	
	
	@Then("verify PRODUCT ITEM Relation is maintained in {string}")
	public void verify_PRODUCT_ITEM_Relation_is_maintained_in(String string) throws InterruptedException, IOException, CsvException {
		
		String file = string;

		ListObjectsRequest listObjectsRequest1 = new ListObjectsRequest()
				.withBucketName("vf-cm-dev-pcm-s3-store-messages-bucket").withPrefix("ecom/data/TBL-NA/SALSIFY")
				.withEncodingType("url");

		ObjectListing ObjectListing = s3.listObjects(listObjectsRequest1);
		List<S3ObjectSummary> objects = ObjectListing.getObjectSummaries();
		System.out.println("Printing all the files in the folder - Main");
		System.out.println(objects.size());
		
		int value = (objects.size()-2);
		System.out.println(objects.get(value).getKey());

		String Name1 = objects.get(value).getKey().replace("ecom/data/TBL-NA/SALSIFY/", "");
		System.out.println(Name1);

		String folderName = "ecom/data/TBL-NA/SALSIFY/" + Name1.substring(0, Name1.indexOf("/"));
		System.out.println("Folder Name is  " +folderName);

		ListObjectsRequest listObjectsRequest2 = new ListObjectsRequest()
				.withBucketName("vf-cm-dev-pcm-s3-store-messages-bucket").withPrefix(folderName)
				.withEncodingType("url");

		ObjectListing ObjectListing1 = s3.listObjects(listObjectsRequest2);
		List<S3ObjectSummary> objects1 = ObjectListing1.getObjectSummaries();
		System.out.println("Printing all the files in the folder");
		System.out.println(objects1.size());

		for (int i = 0; i < objects1.size(); i++)

		{
			System.out.println(objects1.get(i).getKey());
		}

		// download csv from S3

		String bucket_name = "vf-cm-dev-pcm-s3-store-messages-bucket/" + folderName;
		System.out.println(bucket_name);

		String key_name = string;
		System.out.println(key_name);
	
		String fname= System.getProperty("user.dir") +"\\WCS_CSVFiles\\" +key_name;
		System.out.println(fname);
		
		System.out.format("Downloading %s from S3 bucket %s...\n", key_name, bucket_name);

		try {
			S3Object o = s3.getObject(bucket_name, key_name);
			S3ObjectInputStream s3is = o.getObjectContent();
			FileOutputStream fos = new FileOutputStream(
					new File(fname));
			byte[] read_buf = new byte[1024];
			int read_len = 0;
			while ((read_len = s3is.read(read_buf)) > 0) {
				fos.write(read_buf, 0, read_len);
			}
			s3is.close();
			fos.close();
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("Done!");

		// Wait for one Mintue
		TimeUnit.MINUTES.sleep(1);
		File f = new File(fname);
		while (!f.exists()) {
			TimeUnit.MINUTES.sleep(1);
			System.out.println("Waiting files to be created");
		}
		
		Base data = new Base();

		List<Map<String, String>> CSVData = data.ReadDataFromCSVFile(fname);
		System.out.println(CSVData);

		System.out.println("Number of Records in the file is " + CSVData.size());
		String DeleteColumn = null;
		boolean flagdelete = false;

		for (int i = 0; i < CSVData.size(); i++) {

			String PartNumber = CSVData.get(i).get("partnumber");
			String ChildPartNumber = CSVData.get(i).get("childpartnumber");
			DeleteColumn  = CSVData.get(i).get("delete");
			
			System.out.println(PartNumber);
			System.out.println(ChildPartNumber);
			System.out.println(DeleteColumn);
		}
		
		if(DeleteColumn.equals("0"))
		{
			flagdelete = true;
			
		}
		
		Assert.assertTrue(flagdelete);
	}

	@Then("verify PRODUCT ITEM Relation is updated in {string}")
	public void verify_PRODUCT_ITEM_Relation_is_updated_in(String string) throws InterruptedException, IOException, CsvException {
		
		String file = string;

		ListObjectsRequest listObjectsRequest1 = new ListObjectsRequest()
				.withBucketName("vf-cm-dev-pcm-s3-store-messages-bucket").withPrefix("ecom/data/TBL-NA/SALSIFY")
				.withEncodingType("url");

		ObjectListing ObjectListing = s3.listObjects(listObjectsRequest1);
		List<S3ObjectSummary> objects = ObjectListing.getObjectSummaries();
		System.out.println("Printing all the files in the folder - Main");
		System.out.println(objects.size());
		
		int value = (objects.size()-2);
	
		System.out.println(objects.get(value).getKey());

		String Name1 = objects.get(value).getKey().replace("ecom/data/TBL-NA/SALSIFY/", "");
		System.out.println(Name1);

		String folderName = "ecom/data/TBL-NA/SALSIFY/" + Name1.substring(0, Name1.indexOf("/"));
		System.out.println("Folder Name is  " +folderName);

		ListObjectsRequest listObjectsRequest2 = new ListObjectsRequest()
				.withBucketName("vf-cm-dev-pcm-s3-store-messages-bucket").withPrefix(folderName)
				.withEncodingType("url");

		ObjectListing ObjectListing1 = s3.listObjects(listObjectsRequest2);
		List<S3ObjectSummary> objects1 = ObjectListing1.getObjectSummaries();
		System.out.println("Printing all the files in the folder");
		System.out.println(objects1.size());

		for (int i = 0; i < objects1.size(); i++)

		{
			System.out.println(objects1.get(i).getKey());
		}

		// download csv from S3

		String bucket_name = "vf-cm-dev-pcm-s3-store-messages-bucket/" + folderName;
		System.out.println(bucket_name);

		String key_name = string;
		System.out.println(key_name);
	
		String fname= System.getProperty("user.dir") +"\\WCS_CSVFiles\\" +key_name;
		System.out.println(fname);
		
		System.out.format("Downloading %s from S3 bucket %s...\n", key_name, bucket_name);

		try {
			S3Object o = s3.getObject(bucket_name, key_name);
			S3ObjectInputStream s3is = o.getObjectContent();
			FileOutputStream fos = new FileOutputStream(
					new File(fname));
			byte[] read_buf = new byte[1024];
			int read_len = 0;
			while ((read_len = s3is.read(read_buf)) > 0) {
				fos.write(read_buf, 0, read_len);
			}
			s3is.close();
			fos.close();
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("Done!");

		// Wait for one Mintue
		TimeUnit.MINUTES.sleep(1);
		File f = new File(fname);
		while (!f.exists()) {
			TimeUnit.MINUTES.sleep(1);
			System.out.println("Waiting files to be created");
		}
	    
		
		Base data = new Base();

		List<Map<String, String>> CSVData = data.ReadDataFromCSVFile(fname);
		System.out.println(CSVData);

		System.out.println("Number of Records in the file is " + CSVData.size());
		String DeleteColumn = null;
		boolean flagdeletenew = false;

		for (int i = 0; i < CSVData.size(); i++) {

			String PartNumber = CSVData.get(i).get("partnumber");
			String ChildPartNumber = CSVData.get(i).get("childpartnumber");
			DeleteColumn  = CSVData.get(i).get("delete");
			
			System.out.println(PartNumber);
			System.out.println(ChildPartNumber);
			System.out.println(DeleteColumn);
		}
		
		if(DeleteColumn.equals("1"))
		{
			flagdeletenew = true;
			
		}
		
		Assert.assertTrue(flagdeletenew);
		
	}
	
	
	@Given("Updated Product file {string} is available with valid data in drop location {string}")
	public void updated_Product_file_is_available_with_valid_data_in_drop_location(String string, String string2) {
		
		
		System.out.println("Product file" + string + "is available with valid data in drop location" + string2);

		ProcessedFilename = string;
		String file_path = System.getProperty("user.dir") + "\\src\\main\\resources\\SALSIFYJSON\\" + string;
		String bucket_name = string2;
		String key_name = Paths.get(file_path).getFileName().toString();

		System.out.format("Uploading %s to S3 bucket %s...\n", file_path, bucket_name);

		try {
			s3.putObject(bucket_name, key_name, new File(file_path));
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}

		System.out.println("Completed Uploading the File");

	}
	
	@Then("Verify number of PRODUCT ITEM and SKU is matching with {string} and {string}")
	public void verify_number_of_PRODUCT_ITEM_and_SKU_is_matching_with_and(String string, String string2) throws Exception {
	   		
		//Parse JSON and get the Product and Item count
		
		String filename = System.getProperty("user.dir") + "\\src\\main\\resources\\SALSIFYJSON\\" + string;
		System.out.println(filename);
		String json = getFileContent(new File(filename));

		JSONArray jsonArray = new JSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			JSONObject modified = formateJSON(obj);
			list3.add(modified);
		}

		//Get the # of Products
		List<JSONObject> productCount = FilterProductsBasedOnType(list3, "type", "Style");
		System.out.println("result : " + productCount);
		System.out.println("Number of Products in json is "+productCount.size());
		
		int productcount  = productCount.size();
		
		//Get the # of Items
		List<JSONObject> itemCount = FilterProductsBasedOnType(list3, "type", "StyleColor");
		System.out.println("result : " + itemCount);
		System.out.println("Number of Item in json is "+itemCount.size());
		
		int itemcount  = itemCount.size();
		
		
		// Get the # of Items
		List<JSONObject> skuCount = FilterProductsBasedOnType(list3, "type", "StyleColor+Size");
		System.out.println("result : " + skuCount);
		System.out.println("Number of Item in json is " + skuCount.size());

		int skucount = skuCount.size();

		
		//code to Generate CSV's
		
		String file = string2;

		ListObjectsRequest listObjectsRequest1 = new ListObjectsRequest()
				.withBucketName("vf-cm-dev-pcm-s3-store-messages-bucket").withPrefix("ecom/data/TBL-NA/SALSIFY")
				.withEncodingType("url");

		ObjectListing ObjectListing = s3.listObjects(listObjectsRequest1);
		List<S3ObjectSummary> objects = ObjectListing.getObjectSummaries();
		System.out.println("Printing all the files in the folder - Main");
		System.out.println(objects.size());
		
		int value = (objects.size()-2);
		System.out.println(objects.get(value).getKey());

		String Name1 = objects.get(value).getKey().replace("ecom/data/TBL-NA/SALSIFY/", "");
		System.out.println(Name1);

		String folderName = "ecom/data/TBL-NA/SALSIFY/" + Name1.substring(0, Name1.indexOf("/"));
		System.out.println("Folder Name is  " +folderName);

		ListObjectsRequest listObjectsRequest2 = new ListObjectsRequest()
				.withBucketName("vf-cm-dev-pcm-s3-store-messages-bucket").withPrefix(folderName)
				.withEncodingType("url");

		ObjectListing ObjectListing1 = s3.listObjects(listObjectsRequest2);
		List<S3ObjectSummary> objects1 = ObjectListing1.getObjectSummaries();
		System.out.println("Printing all the files in the folder");
		System.out.println(objects1.size());

		for (int i = 0; i < objects1.size(); i++)

		{
			System.out.println(objects1.get(i).getKey());
		}

		// download csv from S3

		String bucket_name = "vf-cm-dev-pcm-s3-store-messages-bucket/" + folderName;
		System.out.println(bucket_name);

		String key_name = string2;
		System.out.println(key_name);
	
		String fname= System.getProperty("user.dir") +"\\WCS_CSVFiles\\" +key_name;
		System.out.println(fname);
		
		System.out.format("Downloading %s from S3 bucket %s...\n", key_name, bucket_name);

		try {
			S3Object o = s3.getObject(bucket_name, key_name);
			S3ObjectInputStream s3is = o.getObjectContent();
			FileOutputStream fos = new FileOutputStream(
					new File(fname));
			byte[] read_buf = new byte[1024];
			int read_len = 0;
			while ((read_len = s3is.read(read_buf)) > 0) {
				fos.write(read_buf, 0, read_len);
			}
			s3is.close();
			fos.close();
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("Done!");

		// Wait for one Mintue
		TimeUnit.MINUTES.sleep(1);
		File f = new File(fname);
		while (!f.exists()) {
			TimeUnit.MINUTES.sleep(1);
			System.out.println("Waiting files to be created");
		}

		// Validate whether Product is present in the file

		// Read CSV File
//		String csvfilepath = "C:\\vfc-salsify-test\\vfc-salsifytest\\CatalogEntryEN_US.csv";
		
		Base data = new Base();
		List<Map<String, String>> CSVData = data.ReadDataFromCSVFile(fname);
		for (Map<String, String> eachRow : CSVData)
			System.out.println(eachRow);

		// Filter Particular Row
		List<Map<String, String>> CSVBundle = data.FilterDataFromCSV(CSVData, "type", "BundleBean");
		
		
		int CSVBundleBeancount  = CSVBundle.size();
		
		List<Map<String, String>> CSVProduct = data.FilterDataFromCSV(CSVData, "type", "ProductBean");
		
		int CSVProductBeancount  = CSVProduct.size();
		
		List<Map<String, String>> CSVItem = data.FilterDataFromCSV(CSVData, "type", "ItemBean");

		int CSVItemBeancount = CSVItem.size();
		
		
		Assert.assertEquals(productcount, CSVBundleBeancount);
		
		Assert.assertEquals(itemcount, CSVProductBeancount);
		
		Assert.assertEquals(skucount, CSVItem);
		
	}
	
	//Code for Scenario 7
	@Then("verify Product related CommonAttributes is seen in {string} as per {string}")
	public void verify_Product_related_CommonAttributes_is_seen_in_as_per(String string, String string2) throws Exception {
		
		String file = string;

		ListObjectsRequest listObjectsRequest1 = new ListObjectsRequest()
				.withBucketName("vf-cm-dev-pcm-s3-store-messages-bucket").withPrefix("ecom/data/TBL-NA/SALSIFY")
				.withEncodingType("url");

		ObjectListing ObjectListing = s3.listObjects(listObjectsRequest1);
		List<S3ObjectSummary> objects = ObjectListing.getObjectSummaries();
		System.out.println("Printing all the files in the folder - Main");
		System.out.println(objects.size());
		
		int value = (objects.size()-2);
		System.out.println(objects.get(value).getKey());

		String Name1 = objects.get(value).getKey().replace("ecom/data/TBL-NA/SALSIFY/", "");
		System.out.println(Name1);

		String folderName = "ecom/data/TBL-NA/SALSIFY/" + Name1.substring(0, Name1.indexOf("/"));
		System.out.println("Folder Name is  " +folderName);

		ListObjectsRequest listObjectsRequest2 = new ListObjectsRequest()
				.withBucketName("vf-cm-dev-pcm-s3-store-messages-bucket").withPrefix(folderName)
				.withEncodingType("url");

		ObjectListing ObjectListing1 = s3.listObjects(listObjectsRequest2);
		List<S3ObjectSummary> objects1 = ObjectListing1.getObjectSummaries();
		System.out.println("Printing all the files in the folder");
		System.out.println(objects1.size());

		for (int i = 0; i < objects1.size(); i++)

		{
			System.out.println(objects1.get(i).getKey());
		}

		// download csv from S3

		String bucket_name = "vf-cm-dev-pcm-s3-store-messages-bucket/" + folderName;
		System.out.println(bucket_name);

		String key_name = string;
		System.out.println(key_name);
	
		String fname= System.getProperty("user.dir") +"\\WCS_CSVFiles\\" +key_name;
		System.out.println(fname);
		
		System.out.format("Downloading %s from S3 bucket %s...\n", key_name, bucket_name);

		try {
			S3Object o = s3.getObject(bucket_name, key_name);
			S3ObjectInputStream s3is = o.getObjectContent();
			FileOutputStream fos = new FileOutputStream(
					new File(fname));
			byte[] read_buf = new byte[1024];
			int read_len = 0;
			while ((read_len = s3is.read(read_buf)) > 0) {
				fos.write(read_buf, 0, read_len);
			}
			s3is.close();
			fos.close();
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("Done!");

		// Wait for one Mintue
		TimeUnit.MINUTES.sleep(1);
		File f = new File(fname);
		while (!f.exists()) {
			TimeUnit.MINUTES.sleep(1);
			System.out.println("Waiting files to be created");
		}
		
		
		//code to check Product CommonAttribute in JSON
		
		String filename = System.getProperty("user.dir") + "\\src\\main\\resources\\SALSIFYJSON\\" + string2;
		
		String json = getFileContent(new File(filename));
		List<JSONObject> list = new ArrayList<JSONObject>();

		JSONArray jsonArray = new JSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			JSONObject modified = formateJSON(obj);
			list.add(modified);
		}


		List<String> listnew = Arrays.asList("parent" ,"type" ,"buyable" ,"published (localized)" ,"identifier" ,"shortDescription (localized)" ,"longDescription (localized)" ,"upc" ,"Name (localized)" ,"taxCode" ,"AuxDesc1 (localized)" ,"AuxDesc2 (localized)" ,"MfPartnumber" ,"MasterCategory" ,"keywords" ,"URLKeyword" ,"seoUrl (localized)" ,"HeroImage" ,"IPColorCode" ,"bundleBean");
		
	
		int countStyle = 0;
	
		List<JSONObject> result = FilterProductsBasedOnType(list, "type", "Style");
		System.out.println("result : " + result);
			
			System.out.println("Number of Style  " +result.size());
			
			for(int i=0; i<result.size(); i++)
			{
				
			JSONObject js = result.get(i);
			// count the number of attribute
			Iterator<String> keys = js.keys();

			while (keys.hasNext()) {
				String key = keys.next();

				if (!(listnew.contains(key))) {
					System.out.println(key);
					countStyle++;

				}

			}

			System.out.println("------------------------------------------------------------------------------");
			System.out.println("Number of Attributes in Style  is" +countStyle);
			System.out.println("------------------------------------------------------------------------------");
						
		}
			
			
		
		//Code to Check the Product CommonAttribute in CSV Files
		//Agnu to add	
		

		
			
//		Assert.assertEquals(countStyle, countStyleCSV);	
		
	}
	
	@Then("verify ITEM and SKU related CommonAttributes is seen in {string} as per {string}")
	public void verify_ITEM_and_SKU_related_CommonAttributes_is_seen_in_as_per(String string, String string2) throws Exception {
		
		String file = string;

		ListObjectsRequest listObjectsRequest1 = new ListObjectsRequest()
				.withBucketName("vf-cm-dev-pcm-s3-store-messages-bucket").withPrefix("ecom/data/TBL-NA/SALSIFY")
				.withEncodingType("url");

		ObjectListing ObjectListing = s3.listObjects(listObjectsRequest1);
		List<S3ObjectSummary> objects = ObjectListing.getObjectSummaries();
		System.out.println("Printing all the files in the folder - Main");
		System.out.println(objects.size());
		
		int value = (objects.size()-2);
		System.out.println(objects.get(value).getKey());

		String Name1 = objects.get(value).getKey().replace("ecom/data/TBL-NA/SALSIFY/", "");
		System.out.println(Name1);

		String folderName = "ecom/data/TBL-NA/SALSIFY/" + Name1.substring(0, Name1.indexOf("/"));
		System.out.println("Folder Name is  " +folderName);

		ListObjectsRequest listObjectsRequest2 = new ListObjectsRequest()
				.withBucketName("vf-cm-dev-pcm-s3-store-messages-bucket").withPrefix(folderName)
				.withEncodingType("url");

		ObjectListing ObjectListing1 = s3.listObjects(listObjectsRequest2);
		List<S3ObjectSummary> objects1 = ObjectListing1.getObjectSummaries();
		System.out.println("Printing all the files in the folder");
		System.out.println(objects1.size());

		for (int i = 0; i < objects1.size(); i++)

		{
			System.out.println(objects1.get(i).getKey());
		}

		// download csv from S3

		String bucket_name = "vf-cm-dev-pcm-s3-store-messages-bucket/" + folderName;
		System.out.println(bucket_name);

		String key_name = string;
		System.out.println(key_name);
	
		String fname= System.getProperty("user.dir") +"\\WCS_CSVFiles\\" +key_name;
		System.out.println(fname);
		
		System.out.format("Downloading %s from S3 bucket %s...\n", key_name, bucket_name);

		try {
			S3Object o = s3.getObject(bucket_name, key_name);
			S3ObjectInputStream s3is = o.getObjectContent();
			FileOutputStream fos = new FileOutputStream(
					new File(fname));
			byte[] read_buf = new byte[1024];
			int read_len = 0;
			while ((read_len = s3is.read(read_buf)) > 0) {
				fos.write(read_buf, 0, read_len);
			}
			s3is.close();
			fos.close();
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		System.out.println("Done!");

		// Wait for one Mintue
		TimeUnit.MINUTES.sleep(1);
		File f = new File(fname);
		while (!f.exists()) {
			TimeUnit.MINUTES.sleep(1);
			System.out.println("Waiting files to be created");
		}
		
		
		//code to check Product CommonAttribute in JSON
		
		String filename = System.getProperty("user.dir") + "\\src\\main\\resources\\SALSIFYJSON\\" + string2;
		
		String json = getFileContent(new File(filename));
		List<JSONObject> list = new ArrayList<JSONObject>();

		JSONArray jsonArray = new JSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			JSONObject modified = formateJSON(obj);
			list.add(modified);
		}


		List<String> listnew = Arrays.asList("parent" ,"type" ,"buyable" ,"published (localized)" ,"identifier" ,"shortDescription (localized)" ,"longDescription (localized)" ,"upc" ,"Name (localized)" ,"taxCode" ,"AuxDesc1 (localized)" ,"AuxDesc2 (localized)" ,"MfPartnumber" ,"MasterCategory" ,"keywords" ,"URLKeyword" ,"seoUrl (localized)" ,"HeroImage" ,"IPColorCode" ,"bundleBean");
		
	
		int countStyleColor = 0;
	
		List<JSONObject> result1 = FilterProductsBasedOnType(list, "type", "StyleColor");
		System.out.println("result : " + result1);
			
			System.out.println("Number of Style Color  " +result1.size());
			
			for(int i=0; i<result1.size(); i++)
			{
				
			JSONObject js = result1.get(i);
			// count the number of attribute
			Iterator<String> keys = js.keys();

			while (keys.hasNext()) {
				String key = keys.next();

				if (!(listnew.contains(key))) {
					System.out.println(key);
					countStyleColor++;

				}

			}

			System.out.println("------------------------------------------------------------------------------");
			System.out.println("Number of Attributes in Style color is" +countStyleColor);
			System.out.println("------------------------------------------------------------------------------");
						
		}
			
			int countStyleColorSize =0;
			
			List<JSONObject> result2 = FilterProductsBasedOnType(list, "type", "StyleColor+Size");
			System.out.println("result : " + result2);
				
				System.out.println("Number of Style Color  " +result2.size());
				
				for(int i=0; i<result2.size(); i++)
				{
					
				JSONObject js = result2.get(i);
				// count the number of attribute
				Iterator<String> keys = js.keys();

				while (keys.hasNext()) {
					String key = keys.next();

					if (!(listnew.contains(key))) {
						System.out.println(key);
						countStyleColorSize++;

					}

				}

				System.out.println("------------------------------------------------------------------------------");
				System.out.println("Number of Attributes in Style color Size is" +countStyleColorSize);
				System.out.println("------------------------------------------------------------------------------");
							
			}	
			
		
		//Code to Check the Product CommonAttribute in CSV Files
		//Agnu to add	
		

		
			
//		Assert.assertEquals(countStyle, countStyleCSV);	
	   
	}
		
		
}
	
	



