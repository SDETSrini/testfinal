Feature: E2EScenario DEV Environment

@E2EPCMSalsify2
Scenario Outline: Timberland : Verify that when product -item relation is modified, then changes are reflected in the generated WCS CSV
	Given Product file "<ProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
	Then PRODUCT is available in ProductService
	And ITEM is available in ProductService
	And ETL for WCS is run and Successful with "<Cluster>" and "<ETLJOB>"
	Then verify PRODUCT ITEM Relation is maintained in "CatalogEntryComponent.csv"
	Given Updated Product file "<UpdatedProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
	Then PRODUCT is available in ProductService
	And ITEM is available in ProductService
	And ETL for WCS is run and Successful with "<Cluster>" and "<ETLJOB>"
	Then verify PRODUCT ITEM Relation is updated in "CatalogEntryComponent.csv"
	

	Examples: 
| ProductFile				 |UpdatedProductFile |S3Bucket																	| BrandwithPath 	              | Result	| Cluster | ETLJOB |  CSVFile	|
| salsify_productFile.json |salsify_updatedproductFile.json | vf-cm-dev-pcm-s3-store-messages-bucket/SALSIFY/ecom/data/TBL-NA/salsify| SALSIFY/ecom/data/TBL-NA/archive | Pass   |vf-cm-dev-pcm-cluster | vf-cm-dev-pcm-tbl-generate-wcs-csvs-process | CatalogEntryEN_US.csv |