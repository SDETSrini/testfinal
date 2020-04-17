package com.qa.stepdefn;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class DeleteData {

	public static void main(String[] args) {
		
//		HashMap<String,AttributeValue> item_key =
//			   new HashMap<String,AttributeValue>();
//			
//			item_key.put("Name", new AttributeValue(name));
//			
//		
//			HashMap<String,AttributeValueUpdate> updated_values =
//			    new HashMap<String,AttributeValueUpdate>();
//			
//		
//			for (String[] field : extra_fields) {
//			    updated_values.put(field[0], new AttributeValueUpdate(
//			                new AttributeValue(field[1]), AttributeAction.PUT));
//			}
//			
//			final AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
//			
//			try {
//			    ddb.updateItem(table_name, item_key, updated_values);
//			} catch (ResourceNotFoundException e) {
//			    System.err.println(e.getMessage());
//			    System.exit(1);
//			} catch (AmazonServiceException e) {
//			    System.err.println(e.getMessage());
//			    System.exit(1);
//
//		
//
//	}

}
}

