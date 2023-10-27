package com.qa.opencart.pages;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	private By prodHeader = By.cssSelector("div#content h1");
	private By prodImages = By.cssSelector("ul.thumbnails img");
	private By prodMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By prodPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By prodQuantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");

	private Map<String, String> productInfoMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(this.driver);
	}

	public String getProductHeaderName() {
		return eleUtil.doGetElementText(prodHeader);
	}

	public int getProductImagesCount() {
		return eleUtil.waitForElementsVisible(prodImages, AppConstants.MEDIUM_DEFUALT_WAIT).size();
	}

	public Map<String, String> getProductInfo() {
//		productInfoMap = new HashMap<String, String>();
		//productInfoMap = new LinkedHashMap<String, String>();// it maintain order
		productInfoMap = new TreeMap<String, String>(); 
		getProductMetaData();
		getProductPriceData();
		productInfoMap.put("productname", getProductHeaderName());
		return productInfoMap;
	}

	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.getElements(prodMetaData);
		for (WebElement e : metaList) {
			String metaText = e.getText();
			String metaInfo[] = metaText.split(":");// String array
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}
	}

	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.getElements(prodPriceData);
		String priceValue = priceList.get(0).getText();
		String exTaxPrice = priceList.get(1).getText();
		String exTaxPriceValue = exTaxPrice.split(":")[1].trim();

		productInfoMap.put("productPrice", priceValue);
		productInfoMap.put("exaxprice", exTaxPriceValue);
	}
}
