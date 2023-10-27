package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// Step-1- create const of the class
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}

	// Step-2- private Bylocators:
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By footerLinks = By.xpath("//footer//a");
	private By loginErrMsg = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	private By registerLink = By.linkText("Register");

	// Create public page actions/methods:=behaviour of page/what exactly we want to do on that page.
	@Step("getting login page title")
	public String getLoginPageTitle() {
		return eleUtil.waitForTitleIsAndCapture(AppConstants.LOGIN_PAGE_TITLE_VALUE, AppConstants.SHORT_DEFUALT_WAIT);
	}
	
	@Step("getting login page url")
	public String getLoginPageUrl() {
		return eleUtil.waitForURLContainsAndCapture(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE, AppConstants.SHORT_DEFUALT_WAIT);
	}
	
	@Step("checking forgot pwd link exist on login page")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.checkElementIsDisplayed(forgotPwdLink);
	}
	
	@Step("getting footer links")
	public List<String> getFooterLinksList() {
		List<WebElement> footerLinksList = eleUtil.waitForElementsVisible(footerLinks, AppConstants.MEDIUM_DEFUALT_WAIT);
		List<String> footerTextList = new ArrayList<String>();
		for (WebElement e : footerLinksList) {
			String text = e.getText();
			footerTextList.add(text);
		}
		return footerTextList;
	}
	
	@Step("login with username {0} and password {1}")
	public AccountsPage doLogin(String username, String pwd) {
		System.out.println("correct creds are : " + username + ":" + pwd);
		eleUtil.waitforElementVisible(emailId, AppConstants.MEDIUM_DEFUALT_WAIT);
		eleUtil.doSendKeys(emailId, username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		
		// return the next landing page -- Accountspage -- page chaining model
		return new AccountsPage(driver);
	}
	
	@Step("login with wrong username {0} and password {1}")
	public boolean doLoginWithWrongCredentials(String username, String pwd) {
		System.out.println("wrong creds are : " + username + ":" + pwd);
		eleUtil.waitforElementVisible(emailId, AppConstants.MEDIUM_DEFUALT_WAIT);
		eleUtil.doSendKeys(emailId, username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		String errorMsg =  eleUtil.doGetElementText(loginErrMsg);
		System.out.println(errorMsg);
		if(errorMsg.contains(AppConstants.LOGIN_ERROR_MSG)) {
			return true;
		}
		return false;
	}
	
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}

}
