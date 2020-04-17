Feature: E2EScenario DEV Environment

@E2EPCMSalsify
Scenario Outline: Timberland : User is able to Process 10 Entities  using PCM Salsify Implementation and verify that associated CSV's are genereated in S3 Bucket
	Given Product file "<ProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
	Then PRODUCT is available in ProductService
	And ITEM is available in ProductService
	And ETL for WCS is run and Successful with "<Cluster>" and "<ETLJOB>"
	Then verify PRODUCT EN US data in "CatalogEntryEN_US.csv" 
	Then verify ITEM EN US data in "CatalogEntryEN_US.csv" 
	

	Examples: 
| ProductFile				 |S3Bucket																	| BrandwithPath 	              | Result	| Cluster | ETLJOB |  CSVFile	|
| salsify_timberland_wcs_json_output_timberland_2020_03_27_10_27_40_UTC10.json  | vf-cm-dev-pcm-s3-store-messages-bucket/SALSIFY/ecom/data/TBL-NA/salsify| SALSIFY/ecom/data/TBL-NA/archive | Pass   |vf-cm-dev-pcm-cluster | vf-cm-dev-pcm-tbl-generate-wcs-csvs-process | CatalogEntryEN_US.csv |


@E2EPCMSalsify 
Scenario Outline: Timberland : User is able to Process 100 Entities using PCM Salsify Implementation and verify that associated CSV's are genereated in S3 Bucket
	Given Product file "<ProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
	Then PRODUCT is available in ProductService
	And ITEM is available in ProductService
	And ETL for WCS is run and Successful with "<Cluster>" and "<ETLJOB>"
	Then verify PRODUCT EN US data in "CatalogEntryEN_US.csv" 
	Then verify ITEM EN US data in "CatalogEntryEN_US.csv" 
	

	Examples: 
| ProductFile				 |S3Bucket																	| BrandwithPath 	              | Result	| Cluster | ETLJOB |  CSVFile	|
| salsify_timberland_wcs_json_output_timberland_2020_03_27_10_28_47_UTC100.json  | vf-cm-dev-pcm-s3-store-messages-bucket/SALSIFY/ecom/data/TBL-NA/salsify| SALSIFY/ecom/data/TBL-NA/archive | Pass   |vf-cm-dev-pcm-cluster | vf-cm-dev-pcm-tbl-generate-wcs-csvs-process | CatalogEntryEN_US.csv |


@E2EPCMSalsify
Scenario Outline: Timberland : User is able to Process 1000 Entities  using PCM Salsify Implementation and verify that associated CSV's are genereated in S3 Bucket
	Given Product file "<ProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
	Then PRODUCT is available in ProductService
	And ITEM is available in ProductService
	And ETL for WCS is run and Successful with "<Cluster>" and "<ETLJOB>"
	Then verify PRODUCT EN US data in "CatalogEntryEN_US.csv" 
	Then verify ITEM EN US data in "CatalogEntryEN_US.csv" 
	

	Examples: 
| ProductFile				 |S3Bucket																	| BrandwithPath 	              | Result	| Cluster | ETLJOB |  CSVFile	|
| salsify_timberland_wcs_json_output_timberland_2020_03_27_10_39_13_UTC1000.json  | vf-cm-dev-pcm-s3-store-messages-bucket/SALSIFY/ecom/data/TBL-NA/salsify| SALSIFY/ecom/data/TBL-NA/archive | Pass   |vf-cm-dev-pcm-cluster | vf-cm-dev-pcm-tbl-generate-wcs-csvs-process | CatalogEntryEN_US.csv |


@E2EPCMSalsify
Scenario Outline: Timberland : User is able to Process 3000 Entities  using PCM Salsify Implementation and verify that associated CSV's are genereated in S3 Bucket
	Given Product file "<ProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
	Then PRODUCT is available in ProductService
	And ITEM is available in ProductService
	And ETL for WCS is run and Successful with "<Cluster>" and "<ETLJOB>"
	Then verify PRODUCT EN US data in "CatalogEntryEN_US.csv" 
	Then verify ITEM EN US data in "CatalogEntryEN_US.csv" 
	

	Examples: 
| ProductFile				 |S3Bucket																	| BrandwithPath 	              | Result	| Cluster | ETLJOB |  CSVFile	|
| salsify_timberland_wcs_json_output_timberland_2020_04_02_06_45_43_UTC3000.json  | vf-cm-dev-pcm-s3-store-messages-bucket/SALSIFY/ecom/data/TBL-NA/salsify| SALSIFY/ecom/data/TBL-NA/archive | Pass   |vf-cm-dev-pcm-cluster | vf-cm-dev-pcm-tbl-generate-wcs-csvs-process | CatalogEntryEN_US.csv |


@E2EPCMSalsify
Scenario Outline: Timberland : User is able to Process 5000 Entities  using PCM Salsify Implementation and verify that associated CSV's are genereated in S3 Bucket
	Given Product file "<ProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
	Then PRODUCT is available in ProductService
	And ITEM is available in ProductService
	And ETL for WCS is run and Successful with "<Cluster>" and "<ETLJOB>"
	Then verify PRODUCT EN US data in "CatalogEntryEN_US.csv" 
	Then verify ITEM EN US data in "CatalogEntryEN_US.csv" 
	

	Examples: 
| ProductFile				 |S3Bucket																	| BrandwithPath 	              | Result	| Cluster | ETLJOB |  CSVFile	|
| salsify_timberland_wcs_json_output_timberland_2020_04_02_07_00_09_UTC5000.json  | vf-cm-dev-pcm-s3-store-messages-bucket/SALSIFY/ecom/data/TBL-NA/salsify| SALSIFY/ecom/data/TBL-NA/archive | Pass   |vf-cm-dev-pcm-cluster | vf-cm-dev-pcm-tbl-generate-wcs-csvs-process | CatalogEntryEN_US.csv |


@E2EPCMSalsify
Scenario Outline: Timberland : User is able to Process 7000 Entities  using PCM Salsify Implementation and verify that associated CSV's are genereated in S3 Bucket
	Given Product file "<ProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
	Then PRODUCT is available in ProductService
	And ITEM is available in ProductService
	And ETL for WCS is run and Successful with "<Cluster>" and "<ETLJOB>"
	Then verify PRODUCT EN US data in "CatalogEntryEN_US.csv" 
	Then verify ITEM EN US data in "CatalogEntryEN_US.csv" 
	

	Examples: 
| ProductFile				 |S3Bucket																	| BrandwithPath 	              | Result	| Cluster | ETLJOB |  CSVFile	|
| salsify_timberland_wcs_json_output_timberland_2020_04_02_07_07_38_UTC7000.json  | vf-cm-dev-pcm-s3-store-messages-bucket/SALSIFY/ecom/data/TBL-NA/salsify| SALSIFY/ecom/data/TBL-NA/archive | Pass   |vf-cm-dev-pcm-cluster | vf-cm-dev-pcm-tbl-generate-wcs-csvs-process | CatalogEntryEN_US.csv |




@E2EPCMSalsify1
Scenario Outline: Timberland : User is able to Process 10000 Entities  using PCM Salsify Implementation and verify that associated CSV's are genereated in S3 Bucket
	Given Product file "<ProductFile>" is available with valid data in drop location "<S3Bucket>" 
	When Lambda is invoked to process and ETL for Salsify is Run for "<BrandwithPath>" 
	Then Verify Whether ETL for Salsify is "<Result>" 
	Then PRODUCT is available in ProductService
	And ITEM is available in ProductService
	And ETL for WCS is run and Successful with "<Cluster>" and "<ETLJOB>"
	Then verify PRODUCT EN US data in "CatalogEntryEN_US.csv" 
	Then verify ITEM EN US data in "CatalogEntryEN_US.csv" 
	

	Examples: 
| ProductFile				 |S3Bucket																	| BrandwithPath 	              | Result	| Cluster | ETLJOB |  CSVFile	|
| salsify_timberland_wcs_json_output_timberland_2020_03_27_11_36_40_UTC10000.json  | vf-cm-dev-pcm-s3-store-messages-bucket/SALSIFY/ecom/data/TBL-NA/salsify| SALSIFY/ecom/data/TBL-NA/archive | Pass   |vf-cm-dev-pcm-cluster | vf-cm-dev-pcm-tbl-generate-wcs-csvs-process | CatalogEntryEN_US.csv |


