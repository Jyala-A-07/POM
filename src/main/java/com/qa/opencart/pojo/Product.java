package com.qa.opencart.pojo;

public class Product {

	private String SearchKey;
	private String productName;
	private int productImages;
	
	public String getSearchKey() {
		return SearchKey;
	}

	public void setSearchKey(String searchKey) {
		SearchKey = searchKey;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductImages() {
		return productImages;
	}

	public void setProductImages(int productImages) {
		this.productImages = productImages;
	}

	public Product(String searchKey, String productName, int productImages) {
		SearchKey = searchKey;
		this.productName = productName;
		this.productImages = productImages;
	}

	@Override
	public String toString() {
		return "Product [SearchKey=" + SearchKey + ", productName=" + productName + ", productImages=" + productImages
				+ "]";
	}
	
	
}
