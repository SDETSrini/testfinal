package com.qa.base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.ResourceBundle;

import java.io.File;
import java.io.OutputStreamWriter;
 


public class TestBase extends TestConfig {

	static ResourceBundle rb;
	
	protected static HashMap <String, String> m = new HashMap<String , String> ();
	protected static HashMap <String, String> k = new HashMap<String , String> ();

	 
	public String setUp(String method,String productID)  {
		
		

		rb = ResourceBundle.getBundle("config");
		 
		String BaseURL  =rb.getString("baseURL");
		String FindproductbyID = rb.getString("FindproductbyID");
		String Createaproduct = rb.getString("Createaproduct");
		String createItem = rb.getString("createItem");
		String createProdItemRel = rb.getString("createProductItemRelation");
		String createAttribute = rb.getString("createAttribute");
		String PRODAttributeRel = rb.getString("PRODAttributeRel");
		String CREATEATTRIBUTEValue = rb.getString("CREATEATTRIBUTEValue");
		String ItemAttributeRel = rb.getString("ItemAttributeRel");
		
		
		
		
		String apiURL = null;
		String URL;
			
		
			switch(method)
			{
			  
			   case "GETByID" :
				  apiURL = FindproductbyID + productID;
				  break; 
				  
			   case "CREATEAPRODUCT" :
				  System.out.println("Testing Creating a Product");
				  apiURL = Createaproduct;
			      break; 
			      
			   case "CREATEAITEM" :
				   System.out.println("Testing Creating a Item");
					  apiURL = createItem;
				      break;  
				      
			   case "CREATEPRODUCTITEMREL" :
				   System.out.println("Testing Creating a Product ITEM Relation");
					  apiURL = createProdItemRel;
				      break;  
				      
			   case "CREATEATTRIBUTE" :
				   System.out.println("Testing Creating a Attribute");
					  apiURL = createAttribute;
				      break; 
				      
			   case "CREATEATTRIBUTEValue" :
				   System.out.println("Testing Creating a AttributeValue");
					  apiURL = CREATEATTRIBUTEValue;
				      break; 
				      
			   case "PRODAttributeRel" :
				   System.out.println("Testing Creating a Product Attribute Relation");
					  apiURL = PRODAttributeRel;
				      break;
				      
			   case "ItemAttributeRel" :
				   System.out.println("Testing Creating a Item Attribute Relation");
					  apiURL = ItemAttributeRel;
				      break;
				      
				       
				  
				      
			      
			   case "UPDATEAPRODUCT" :
				  apiURL = FindproductbyID + productID;
					  break; 
					  
					  
			   
			  
			   default : 
			      
			}

					
			URL = BaseURL + apiURL;
			System.out.println(URL);
			return URL;
	
	}
	
	

	public static HashMap<String, String> createbody(String arg1, String arg2) {
		
		String body1 = arg1;
		String Bodyvalue1 = rb.getString(body1);
		
		System.out.println(body1);
		System.out.println(Bodyvalue1);
		
		
		m.put(body1, Bodyvalue1);
		
		return m;
		
		
	}

	public static HashMap<String, String> createheader(String arg1, String arg2) {
		String key = arg1;
		String keyvalue = rb.getString(key);
		
		System.out.println(key);
		System.out.println(keyvalue);
		
		
		k.put(key, keyvalue);
		
		return k;
		
	}
	
	public static String GenerateStringFromResource(String path) throws IOException {

		return new String(Files.readAllBytes(Paths.get(path)));



	}
	
	public static void LaunchBrowser()
	{
		
	}
	
	
		 
	
	
		
}
