Feature: E2EScenario DEV Environment


@E2EPCMSalsify
Scenario Outline: User Places a invalid file - Excel file  - Lambda is invoked and file is moved to error folder 
	Given Product file "<ProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
		
	Examples: 
| ProductFile				 |S3Bucket																	| BrandwithPath 	              | Result	| 
|salsify_41302020121199.xlsx | vf-cm-dev-pcm-s3-store-messages-bucket/SALSIFY/ecom/data/TBL-NA/salsify	|  SALSIFY/ecom/data/TBL-NA/error | Failed  |




@E2EPCMSalsify 
Scenario Outline: User Places a invalid file - word document - Lambda is invoked and file is moved to error folder 
	Given Product file "<ProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
		
	Examples: 
| ProductFile				 |S3Bucket																	| BrandwithPath 	              | Result	| 
|salsify_41302020121200.docx | vf-cm-dev-pcm-s3-store-messages-bucket/SALSIFY/ecom/data/TBL-NA/salsify	|  SALSIFY/ecom/data/TBL-NA/error | Failed  |


@E2EPCMSalsify
Scenario Outline: User Places a invalid file - pdf document - Lambda is invoked and file is moved to error folder 
	Given Product file "<ProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
		
	Examples: 
| ProductFile				 |S3Bucket																	| BrandwithPath 	              | Result	| 
|salsify_41302020121200.pdf  | vf-cm-dev-pcm-s3-store-messages-bucket/SALSIFY/ecom/data/TBL-NA/salsify	|  SALSIFY/ecom/data/TBL-NA/error | Failed  |

