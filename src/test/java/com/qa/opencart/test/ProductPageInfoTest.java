package com.qa.opencart.test;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest{

		@BeforeClass
		public void accPageSetup() {
			accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		}
		
		@Test
		public void productInfoTest() {
			resultsPage = accPage.doSearch("Macbook");
			productInfoPage = resultsPage.selectProduct("MacBook Pro");
			Map<String, String> productInfoMap = productInfoPage.getProductInfo();
			System.out.println(productInfoMap );
//			{Brand=Apple, Availability=In Stock, exaxprice=$2,000.00, Product Code=Product 18, productname=MacBook Pro, 
	//		Reward Points=800, productPrice=$2,000.00}	PASSED: productInfoTest = Not in order - HashMap
			
			//{Brand=Apple, Product Code=Product 18, Reward Points=800, Availability=In Stock,
			//productPrice=$2,000.00, exaxprice=$2,000.00, productname=MacBook Pro} -- LinkedHashMap
			
			//{Availability=In Stock, Brand=Apple, Product Code=Product 18, Reward Points=800, 
			//exaxprice=$2,000.00, productPrice=$2,000.00, productname=MacBook Pro} -- treeMap
			
			//Assert = static - for single test data
			//softAssert = non static - for multiple test data
			
			softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
			softAssert.assertEquals(productInfoMap.get("Availability"), "In Stock");
			softAssert.assertEquals(productInfoMap.get("productname"), "MacBook Pro");
			softAssert.assertEquals(productInfoMap.get("productPrice"), "$2,000.00");
			softAssert.assertAll();
		}
		
		
		
	}


