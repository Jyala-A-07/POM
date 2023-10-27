package com.qa.opencart.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Epic 100: Login Page Design")
@Story("US 101: design login page for opencartapp with title, url, forgot pwd link, user is able to login")
public class LoginPageTest extends BaseTest {

	@Severity(SeverityLevel.MINOR)
	@Description("checking login page title test..")
	@Feature("title test")
	@Test
	public void loginpageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Description("checking login page url test..")
	@Feature("title test")
	@Test
	public void loginpageUrlTest() {
		String actUrl = loginPage.getLoginPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("checking forgot pwd link test..")
	@Feature("title test")
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}

	@Severity(SeverityLevel.BLOCKER)
	@Description("checking user is able to login with correct username/pwd test..")
	@Feature("title test")
	@Test
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLinkExist());
//		Assert.assertTrue(accPage.getAccPageTitle().equals("My Account"));
	}
}


