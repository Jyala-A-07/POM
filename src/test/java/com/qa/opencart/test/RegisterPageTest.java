package com.qa.opencart.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		regPage = loginPage.navigateToRegisterPage();
	}

//	@DataProvider(name = "regData")
//	public Object[][] getUserRegTestData() {
//		return new Object[][] {
//
//				{ "Naman", "Goyal", "namangg77@gmail.com", "9865326598", "naman123", "yes" }, // *user created and login
//																							// so first logout, then
//																							// click on registerlink,
//				{ "aman", "koyal", "amanautowala@gmail.com", "9865378998", "aman123", "yes" },
//				{ "Modi", "Shah", "modichorh@gmail.com", "9865564598", "modi123", "yes" },
//
//		};
//	}

	@DataProvider(name = "regExcelData")
	public Object[][] getRegExcelData() {
		Object regData[][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regData;
	}

	@Test(dataProvider = "regExcelData")
	public void userRegisterTest(String fName, String lName, String email, String telephone, String password,
			String subscribe) {
		String actRegSucMsg = regPage.registerUser(fName, lName, email, telephone, password, subscribe);
		Assert.assertEquals(actRegSucMsg, AppConstants.USER_REG_SUCCESS_MSG);
	}

}
