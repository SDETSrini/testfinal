Feature:  Product Service Test  - Create a PRODUCT and ITEM with CommonAttribute and SEO Details 

Background: 
	Given User perform authentication operation 
	
@APIFinal
Scenario Outline: Validate Product and ITEM Creation - POST - Status code 
#CreateProduct
	Given the end point is "CREATEAPRODUCT" for "product" 
	And the body is "<productfile>" json 
	And the method is "POST" for "<productfile>" 
	Then status code for POST is 201 
	#CreateItem
	Given the end point is "CREATEAITEM" for "product" 
	And the body is "<itemfile>" json 
	And the method is "POST" for "<itemfile>" 
	Then status code for POST is 201 
	#CreateProductItem Relation
	Given the end point is "CREATEPRODUCTITEMREL" for "product" 
	And the body is "<productItemRel>" json 
	And the method is "POST" for "<productItemRel>" 
	Then status code for POST is 201 
	#CreateAttribute
	Given the end point is "CREATEATTRIBUTE" for "product" 
	And the body is "<attribute>" json 
	And the method is "POST" for "<attribute>" 
	Then status code for POST is 201 
	#CreateAttributeValue
	Given the end point is "CREATEATTRIBUTEValue" for "product" 
	And the body is "<attributeValue>" json 
	And the method is "POST" for "<attributeValue>" 
	Then status code for POST is 201 
	#CreateProductAttributeRelation
	Given the end point is "PRODAttributeRel" for "product" 
	And the body is "<prodAttributeRel>" json 
	And the method is "POST" for "<prodAttributeRel>" 
	Then status code for POST is 200 
	#CreateItemAttributeRelation
	Given the end point is "ItemAttributeRel" for "product" 
	And the body is "<ItemAttributeRel>" json 
	And the method is "POST" for "<ItemAttributeRel>" 
	Then status code for POST is 200
	
	
	Examples: 
		| productfile	|	itemfile	| productItemRel	| attribute	| attributeValue | prodAttributeRel	| ItemAttributeRel |
		| PRODUCT1		|	ITEM		| PRODUCTITEMREL	| Attribute	| attributeValue | ProdARel			| ItemARel		|				