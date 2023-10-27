package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.frameworkexception.FrameException;

public class DriverFactory {
	WebDriver driver;
	OptionsManager optManager;
	public static String highlightElement;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	// Threaadlocal will help in parallel execution of tc, when u have high no. of
	// threads and large tc.

	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser").trim();
		System.out.println("browser name is :" + browserName);

		highlightElement = prop.getProperty("highlight");
		optManager = new OptionsManager(prop);

		switch (browserName.toLowerCase()) {
		case "chrome":
			// driver = new ChromeDriver(optManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optManager.getChromeOptions()));
			break;
		case "edge":
			// driver = new EdgeDriver(optManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optManager.getEdgeOptions()));
			break;
		case "safari":
			// driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;
		case "firefox":
			// driver = new FirefoxDriver(optManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optManager.getFirefoxOptions()));
			break;

		default:
			System.out.println("plz pass the right browser ....." + browserName);
			throw new FrameException("NOBROWSERFOUNDEXCEPTION");
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}

	// return the thread local copy of driver
	public synchronized static WebDriver getDriver() {// to give you gurrantee that whenever any thread is calling this
														// method copy,
		// they get separate sync copy of driver
		return tlDriver.get();
	}

	public Properties initProp() {

		// mvn clean install -Denv="qa"
		// mvn clean install
		Properties prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env");
		System.out.println("running test cases on env: " + envName);

		try {
			if (envName == null) {
				System.out.println("no env is given, hence running on QA env...");

				ip = new FileInputStream("./src/main/resource/config/qa.config.properties");

			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/main/resource/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/main/resource/config/dev.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/main/resource/config/stage.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/main/resource/config/uat.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/main/resource/config/config.properties");
					break;

				default:
					System.out.println("plz pass the ryt env name..." + envName);
					throw new FrameException("NOVALIDENVGIVEN");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;

	}
	
	/**
	 * TAKE SCREENSHOT
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		}catch (IOException e) {
			e.printStackTrace();
		}
			return path;
		}
	}
	
	
	
	
	


