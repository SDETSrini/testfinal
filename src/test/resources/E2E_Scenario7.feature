Feature: E2EScenario DEV Environment

@E2EPCMSalsify7
Scenario Outline: Timberland : Verify that when user updates product related data wrt CommonAttribute in product json, then changes are reflected in the generated WCS CSV
	Given Product file "<ProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
	Then PRODUCT is available in ProductService
	And ITEM is available in ProductService
	And ETL for WCS is run and Successful with "<Cluster>" and "<ETLJOB>"
	Then verify Product related CommonAttributes is seen in "CatalogEntryADRelEN_US.csv" as per "<ProductFile>"
	Given Updated Product file "<UpdatedProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
	Then PRODUCT is available in ProductService
	And ITEM is available in ProductService
	And ETL for WCS is run and Successful with "<Cluster>" and "<ETLJOB>"
	Then verify Product related CommonAttributes is seen in "CatalogEntryADRelEN_US.csv" as per "<UpdatedProductFile>"
	

	Examples: 
| ProductFile				 |UpdatedProductFile |S3Bucket																	| BrandwithPath 	              | Result	| Cluster | ETLJOB |  CSVFile	|
| salsify_03232020001122S7.json |salsify_03232020001122S7Updated.json | vf-cm-dev-pcm-s3-store-messages-bucket/SALSIFY/ecom/data/TBL-NA/salsify| SALSIFY/ecom/data/TBL-NA/archive | Pass   |vf-cm-dev-pcm-cluster | vf-cm-dev-pcm-tbl-generate-wcs-csvs-process | CatalogEntryEN_US.csv |