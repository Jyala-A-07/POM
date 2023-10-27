package com.qa.opencart.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class LoginPageNegativeTest extends BaseTest{
	
	
	@DataProvider
	public Object[][]  incorrectLoginTestData() {
		return new Object[][] {
			
			{"@#$%@#$","@#$%@#$"},
			{"auto", "123456"},
			{"mohito@gmail.com", "567889"},
			{"naruto@gmail.com", "123456"}
		};
	}
	
	
	
	
	@Test(dataProvider = "incorrectLoginTestData")
	public void loginWithWrongCredentialsTest(String username, String pwd) {
		Assert.assertTrue(loginPage.doLoginWithWrongCredentials(username, pwd));
	}
}
