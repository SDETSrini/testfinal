package com.qa.stepdefn;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.opencsv.exceptions.CsvException;
import com.qa.base.Base;

public class CheckCSVData extends Base {

	public static void main(String[] args) throws IOException, CsvException {
		
		
		String fname = "C:\\vfc-salsify-test\\vfc-salsifytest\\WCS_CSVFiles\\CatalogEntryEN_US.csv";
		
		
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
			
		}		

	}

}
