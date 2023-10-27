package com.qa.opencart.utils;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.frameworkexception.FrameException;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;
	private final int DEFAULT_TIME_OUT = 5;

	/**
	 * We are avoiding static here bcz static does not help in parallel execution in
	 * CMA, it creates a deadlock condition for elements to interact and elements
	 * can goto wait state. driver will be static unnecessary, and driver will be
	 * used one at a time
	 * 
	 * @param driver
	 */
	// private make it use to accessed in its own class
	// if we make it public anyone can accessed it and I might get the default null
	// value here
	// Just to avoid it, make it private

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(this.driver);
		// Encapsulation concept

	}

	public void doSendKeys(By locator, String value) {
		if (value == null) {// null values are not allowed in sendkeys method
			System.out.println("null values are not allowed");
			throw new FrameException("VALUECANNOTBENULL");
		}
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

//	public void doClick(By locator, int timeOut) {
//		checkElementClickable(locator, timeOut).click();
//	}

	public WebElement getElement(By locator, int timeOut) {
		WebElement element =  waitforElementVisible(locator, timeOut);
		if(Boolean.parseBoolean(DriverFactory.highlightElement)) {
			jsUtil.flash(element);
		}
		return element;
	}

	public WebElement getElement(By locator) {
		WebElement element = null;
		try {
			element = driver.findElement(locator);
			System.out.println("Element is found using this locator: " + locator);
		} catch (NoSuchElementException e) {
			System.out.println("Element is not found using this locator: " + locator);
			element = waitforElementVisible(locator, DEFAULT_TIME_OUT);
		}
		if(Boolean.parseBoolean(DriverFactory.highlightElement)) {
			jsUtil.flash(element);
		}
		return element;
	}

	public void doClear(By locator) {
		getElement(locator).clear();
	}

	public String doGetElementText(By locator) {
		return getElement(locator).getText();
	}

	public boolean checkElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public String doGetAttributeValue(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public void search(String searchKey, By searchLocator, By suggestions, String suggName)
			throws InterruptedException {
		driver.findElement(searchLocator).sendKeys(searchKey);
		Thread.sleep(3000);
		List<WebElement> suggList = driver.findElements(suggestions);
		System.out.println("suggestions total : " + suggList);

		if (suggList.size() > 0) {

			for (WebElement e : suggList) {
				String text = e.getText();
				if (text.length() > 0) {
					System.out.println(text);
					if (text.contains(suggName)) {
						e.click();
						break;
					}
				} else {
					System.out.println("blank values-----no suggestions");
					break;
				}
			}
		} else {
			System.out.println("no search suggestions");
		}
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

//***********Drop Down Utils**************
//--------------------------------------------	
	public void doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropDownByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public void doSelectDropDownByValueAttribute(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	/**
	 * Actions utils
	 */
//	public void doActionsClick(By locator, int timeOut) {
//		Actions act = new Actions(driver);
//		act.click(checkElementClickable(locator, timeOut)).build().perform();
//	}

//*************Wait Utils*************
	/**
	 * presenceOfElementLocated(username) = An expectation for checking that an
	 * element is present on the DOM of a page. This does notnecessarily mean that
	 * the element is visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */

	public String waitForTitleIsAndCapture(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
			String title = driver.getTitle();
			return title;
		} else {
			System.out.println("title is not present within the given timeout : " + timeOut);
			return null;
		}
	}

	public String waitForURLContainsAndCapture(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
			String url = driver.getCurrentUrl();
			return url;
		} else {
			System.out.println("url is not present within the given timeout : " + timeOut);
			return null;
		}
	}

	public WebElement waitforElementPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */

	public WebElement waitforElementVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * default timeout = 500 ms
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */

	public List<WebElement> waitforEleVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitforElePresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeout
	 */
	public void clickElementWhenReady(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		// Might give you not interactable exception, then we should go with actions
		// class or Javascript click..
	}

}