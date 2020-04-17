Feature: Product Service Test - Find product by ID - /v1/product/{productId}


Background: 
Given User perform authentication operation

@FPID_001 @Sanity1 @Regression @API
Scenario Outline: Validate Status Code - Products using Find product by ID 
Given the end point is "GETByID" for "<productID>"
And the method is "GET" for "<productID>" 
Then status code is 200

Examples:
| productID		|
| BA1HZ7025		|


@FPID_002 @Sanity1 @Regression @API
Scenario Outline: Validate ResponseBody in json format - Products using Find product by ID 
Given the end point is "GETByID" for "<productID>"
And the method is "GET" for "<productID>" 
Then status code is 200
And the response body is in json format

Examples:
| productID		|
| BA1HZ7025		|

@FPID_003 @Regression @Sanity @API
Scenario Outline: Validate Response body Schema validation - Products using Find product by ID
Given the end point is "GETByID" for "<productID>"
And the method is "GET" for "<productID>" 
Then status code is 200
And the response body is in json "tc003schema"

Examples:
| productID		|
| BA1HZ7025		|



#Mandatory check