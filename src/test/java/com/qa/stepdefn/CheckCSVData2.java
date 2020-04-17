package com.qa.stepdefn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opencsv.exceptions.CsvException;
import com.qa.base.Base;

public class CheckCSVData2 extends Base {

	public static void main(String[] args) throws IOException, CsvException {
		
		
		String fname = "C:\\vfc-salsify-test\\vfc-salsifytest\\WCS_CSVFiles\\CatalogEntryADRelEN_US.csv";
		
		
		Base data = new Base();
		
		List<Map<String, String>> CSVData = data.ReadDataFromCSVFile(fname);
		for(Map<String, String> EachRow : CSVData)
			System.out.println(EachRow);
		
		
		Set<String> AllPartNumber = new HashSet<String>();
		
		for(Map<String, String> EachRow : CSVData) {
			String EachRowpartNumber  = EachRow.get("partnumber".toLowerCase());
			AllPartNumber.add(EachRowpartNumber);
		}
		
		System.out.println(AllPartNumber);
		
		List<String> AllCommonAttributes = new ArrayList<String>();
		Map<String, List<String>> AllDetails = new HashMap<String, List<String>>();
		
		for(String EachpartNumber : AllPartNumber) {
			List<String> SpecificAttributes= new ArrayList<String>();

			for(Map<String, String> EachRow : CSVData)  {
					String EachRowPartNumber = EachRow.get("partnumber".toLowerCase());
				if(EachRowPartNumber.equals(EachpartNumber))  {
                    String AttributeIdentifier = EachRow.get("AttributeIdentifier".toLowerCase());
					SpecificAttributes.add(AttributeIdentifier);
				}	
				
				
			}
			
			 AllDetails.put(EachpartNumber, SpecificAttributes);
			
			
			
		}
		
		 for( Map.Entry<String, List<String>> eachEntry : AllDetails.entrySet()) {
			 
			
			 if(eachEntry.getKey().equals("LPRODUCT001"))
			 {			 
			 
			   eachEntry.getValue().size();
		 }
			 	System.out.println(eachEntry);
		 
		 
		 
		
		
		
//		for (Map<String, String> eachRow : CSVData)
//		{
////			System.out.println(eachRow);
//
//		// Filter Particular Row
//		List<Map<String, String>> CSVFieldValue = data.FilterDataFromCSV(CSVData, "partnumber", "ITEM1404123");
//		System.out.println(CSVFieldValue);
//		
//		}
		
		
		
		
		
//		for (Map<String, String> eachRow : CSVFieldValue) {
//			
//			String CSVPartNumber = eachRow.get("PartNumber".toLowerCase());
//			String CSVName = eachRow.get("Name".toLowerCase());
//			
//			System.out.println(CSVPartNumber);
//			
//		}		

	}
		 
	}}


