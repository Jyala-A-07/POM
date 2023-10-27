package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By fName = By.id("input-firstname");
	private By lName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	private By agreeCheckBox = By.name("agree");
	private By continueBtn = By.xpath("//input[@type='submit' and @value='Continue']");
	

	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[2]/input");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[2]/input");
	private By userRegSucMsg = By.cssSelector("div#content h1");
	private By logout = By.linkText("Logout");
	private By regLink = By.linkText("Register");
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}
	
	public String registerUser(String fName, String lName, String email, String telephone, String password,
			String subscribe) {
		eleUtil.waitforElementVisible(this.fName, AppConstants.MEDIUM_DEFUALT_WAIT);
		eleUtil.doSendKeys(this.fName, fName);
		eleUtil.doSendKeys(this.lName, lName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);
		eleUtil.doSendKeys(this.fName, fName);

		doSubscribe(subscribe);

		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueBtn);

		String userRegSucMsg = eleUtil.waitforElementVisible(this.userRegSucMsg, AppConstants.MEDIUM_DEFUALT_WAIT)
				.getText();
		System.out.println(userRegSucMsg);
		
		eleUtil.doClick(logout);//**line 22, regpageTest
		eleUtil.doClick(regLink);
		
		return userRegSucMsg;
	}

	private void doSubscribe(String subscribe) {
		if (subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		} else {
			eleUtil.doClick(subscribeNo);
		}
	}
}
