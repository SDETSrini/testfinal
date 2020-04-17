Feature: E2EScenario  QA Environment

@E2EPCMSalsifyQA @QuickSanity @Defect
Scenario Outline: Timberland : User is able to Process 10 Entities  using PCM Salsify Implementation and verify that associated CSV's are genereated in S3 Bucket
	Given Product file "<ProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
	Then PRODUCT is available in ProductService
	And ITEM is available in ProductService
	
	
	
	Examples: 
		| ProductFile				 |S3Bucket																	| BrandwithPath 	              | Result	|
		| salsify_timberland_wcs_json_output_timberland.json  | vf-cm-qa-pcm-s3-store-messages-bucket/SALSIFY/ecom/data/TBL-NA/salsify| SALSIFY/ecom/data/TBL-NA/archive | Pass   |
