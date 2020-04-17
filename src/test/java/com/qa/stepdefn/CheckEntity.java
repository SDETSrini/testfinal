package com.qa.stepdefn;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.qa.base.Base;

import org.json.JSONArray;
import org.json.JSONObject;

public class CheckEntity extends Base {

	private static final int String = 0;
	private static final int ArrayList = 0;

	public static void main(String[] args) throws Exception {
		
		
		String filename = "C:\\Users\\srinaika\\Desktop\\UATVF\\salsify\\salsify_timberland_wcs_json_output_timberland_2020_03_27_10_27_40_UTC10.json";
		
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
		// System.out.println(list);
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

			System.out.println("Number of Attributes in Style  i s" +countStyle);
			int CStyleColor  = countStyle;
			
		}
				
				
				
				
					
			

 	
		
		
		

		int countStyleColor = 0;
	
		List<JSONObject> result1 = FilterProductsBasedOnType(list, "type", "StyleColor");
		System.out.println("result : " + result1);
		System.out.println("Number of StyleColor  " +result1.size());
		
		
			
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

			System.out.println("Number of Attributes in StyleColor  is" +countStyleColor);
		}
			
		
		
		
		
		

		int countStyleColorSize = 0;
		List<JSONObject> result2 = FilterProductsBasedOnType(list, "type", "StyleColor+Size");
		System.out.println("result : " + result2);
		System.out.println("Number of StyleColor + Size  " +result2.size());
		
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
		System.out.println("Number of Attributes in StyleColorsize  is" +countStyleColorSize);
		countStyleColorSize = 0;
		
	}
		


	}
			
	}




