package com.qa.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public class Base extends TestBase {

	// Format JSON
	public static JSONObject formateJSON(JSONObject object) {

		Iterator<String> keys = object.keys();

		while (keys.hasNext()) {
			String key = keys.next();
			try {
				Object result = null;
				String value = object.get(key).toString();

				if (value.startsWith("[\"{")) {
					String newStr = value.replace("[\"{", "[{").replace("}\"]", "}]").replace("\\\"", "\"")
							.replace("\\\\", "\\").replace("=>", ":").replace("nil", "null").replace("\",\"", ",")
							.replace("_", "");
					result = new JSONArray(newStr);
				} else if (value.startsWith("[\"") && value.endsWith("\"]"))
					result = value.replace("[\"", "").replace("\"]", "");

				else if (value.startsWith("[") && value.endsWith("]"))
					result = value.replace("[", "").replace("]", "");

				object.put(key, result);
			} catch (Exception e) {
				System.out.println(object.get(key) + ">>>");
			}
		}

		return object;
	}

	// getFile to clean the data
	public static String getFileContent(File file) throws Exception {
		String content = null;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		// delete the last new line separator
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();

		content = stringBuilder.toString();
		return content;
	}

	public static List<JSONObject> FilterProductsBasedOnType(List<JSONObject> list, String searchAttribute,
			String searchValue) {

		List<JSONObject> FilterProduct = new ArrayList<JSONObject>();

		for (JSONObject eachJsonObject : list) {
			String[] Jsonpath = searchAttribute.split(",");
			Object result = eachJsonObject;

			for (String eachJsonPath : Jsonpath) {
				result = ((JSONObject) result).get(eachJsonPath);

//				if (result instanceof JSONArray)
//					result = (JSONObject) ((JSONArray) result).get(0);
			}

			if (String.valueOf(result).equalsIgnoreCase(String.valueOf(searchValue)))
				FilterProduct.add(eachJsonObject);

		}

		return FilterProduct;
	}

	public static String GenerateStringFromResource(String path) throws IOException {

		return new String(Files.readAllBytes(Paths.get(path)));

	}

//Read Data from CSV
	public List<Map<String, String>> ReadDataFromCSVFile(String filepath) throws IOException, CsvException {

		List<Map<String, String>> CSVData = new ArrayList<Map<String, String>>();
		File file = new File(filepath);
		FileReader reader = new FileReader(file);
		CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
		List<String[]> alldata = csvReader.readAll();
		String[] allKeys = alldata.get(0);

		for (int i = 1; i < alldata.size(); i++) {

			Map<String, String> map = new HashMap<String, String>();
			String[] EachRow = alldata.get(i);

			for (int j = 0; j < EachRow.length; j++) {
				String key = allKeys[j].toLowerCase();
				String value = EachRow[j].isEmpty() ? "Not Exist in CSV" : EachRow[j];

				map.put(key, value);
			}
			CSVData.add(map);
		}
		return CSVData;
	}

	public List<Map<String, String>> FilterDataFromCSV(List<Map<String, String>> CSVData, String InputFieldName,
			String InputFieldValue) {

		List<Map<String, String>> FilterRow = new ArrayList<Map<String, String>>();

		for (Map<String, String> eachRow : CSVData) {
			String eachRowFieldValue = eachRow.get(InputFieldName.toLowerCase());
			if (eachRowFieldValue.equalsIgnoreCase(InputFieldValue))
				FilterRow.add(eachRow);
		}

		return FilterRow;
	}

}
