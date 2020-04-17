Feature: Product Service Test  - Create a product - /v1/products

@PS_CP_001 @Sanity @Regression
Scenario Outline: Validate Product Creation - POST - Status code 
Given the end point is "UPDATEAPRODUCT" for "product"
And the body is "<productfile>" json
And the method is "PUT" for "<productfile>" 
Then status code for PUT is 200

Examples:
| productfile	|
| PSCP001S3		|