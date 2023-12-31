package com.qa.opencart.utils;

import java.util.Arrays;
import java.util.List;

public class AppConstants {

	public static final String LOGIN_PAGE_TITLE_VALUE = "Account Login";
	public static final String ACCOUNTS_PAGE_TITLE_VALUE = "My Account";

	public static final String LOGIN_PAGE_URL_FRACTION_VALUE = "route=account/login";

	public static final int SHORT_DEFUALT_WAIT = 5;
	public static final int MEDIUM_DEFUALT_WAIT = 10;
	public static final int LONG_DEFUALT_WAIT = 20;

	public static final List<String> EXP_ACCOUNTS_HEADERS_LIST = Arrays.asList("My Account", "My Orders",
			"My Affiliate Account", "Newsletter");
	
	public static final String LOGIN_ERROR_MSG = " Warning: No match for E-Mail Address and/or Password.";
	public static final String USER_REG_SUCCESS_MSG = "Your Account Has Been Created!";
	
	//*******SHEET NAME******
	public static final String REGISTER_SHEET_NAME = "register";

}
