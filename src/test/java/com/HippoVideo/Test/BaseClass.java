package com.HippoVideo.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

// This Class Will have the Basic Setups and All the Reusables for the Application
public class BaseClass {
	public static WebDriver driver;
	public static ExtentReports report;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest logger = null;
	public static ExtentTest childTest = null;
	public static String suiteName;
	public static WebElement element;
	public static Properties prop;
	public static WebDriverWait wait;
	public static Actions action;
	ArrayList<String> oldTabs = new ArrayList<String>();
	public static String tab1 = null;
	public static String tab2 = null;

	@SuppressWarnings("deprecation")
	@BeforeTest
	public static void reportGeneration() {
		String path = System.getProperty("user.dir") + "/Report/report.html";
		System.out.println(path);
		htmlReporter = new ExtentHtmlReporter(path);
		report = new ExtentReports();
		report.attachReporter(htmlReporter);
		report.setSystemInfo("OS", "Mac Sierra");
		report.setSystemInfo("Host Name", "Automation");
		report.setSystemInfo("Environment", "QA");
		report.setSystemInfo("User Name", "Tester");
		htmlReporter.config().setDocumentTitle("HippoVideo Automation Report");
		htmlReporter.config().setReportName("Automation Report");
		htmlReporter.config().setEncoding("UTF-8");
		htmlReporter.config().setProtocol(Protocol.HTTPS);
		htmlReporter.config().setReportName("HippoVideo Automation Report");
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setCSS("css-string");
		htmlReporter.config().setJS("js-string");
	}

	public static void loadData() throws IOException {
		prop = new Properties();
		File propertypath = new File(System.getProperty("user.dir") + "/Properties/HippoCredentials.properties");
		FileReader reader = new FileReader(propertypath);
		prop.load(reader);
	}

	public static String getData(String value) throws IOException {
		loadData();
		String data = prop.getProperty(value);
		return data;
	}

	public void clickOnID(String locator) {
		element = driver.findElement(By.id(locator));
		element.click();
	}

	public void clickOn(String xpath) {
		element = driver.findElement(By.xpath(xpath));
		element.click();
	}

	public void typeOn(String xpath, String value) throws IOException {
		String text = getData(value);
		element = driver.findElement(By.xpath(xpath));
		element.clear();
		element.click();
		element.sendKeys(text);
	}

	public void typeOnId(String locator, String value) throws IOException {
		String text = getData(value);
		element = driver.findElement(By.id(locator));
		element.clear();
		element.click();
		element.sendKeys(text);
	}

	public void launchBrowser(String url) {
		try {
			ChromeOptions options = new ChromeOptions();
			options.addExtensions(new File(System.getProperty("user.dir") + "/resource/Hippo.crx"));
			options.addArguments(Arrays.asList("disable-infobars", "ignore-certificate-errors", "start-maximized",
					"use-fake-ui-for-media-stream"));
			// options.addArguments("--disable-notifications");
			options.addArguments("auto-select-desktop-capture-source=Entire screen");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(capabilities);
			driver.get("chrome-extension://cijidiollmnkegoghpfobabpecdkeiah/html/injected/onboard.html");
			driver.navigate().refresh();
			driver.close();
			Thread.sleep(5000);
			childTest.log(Status.PASS, "Extension Added Succesfully");
			switchToNewTab();
			driver.get(url);
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, 60);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public String getCurrentWindow() {
		String tab1 = driver.getWindowHandle();
		return tab1;
	}
	
	public void switchToNewTab() {
		ArrayList<String> newTabs = new ArrayList<String>(driver.getWindowHandles());
		newTabs.remove(tab1);
		int i = newTabs.size() - 1;
		driver.switchTo().window(newTabs.get(i));
		
	}
	
	public void switchToParentWindow () {
		driver.switchTo().window(tab1);
	}
	
	public void openNewWindow() {
		((JavascriptExecutor) driver).executeScript("window.open()");
	}
	
	public void switchToOtherTab() {
		String tab1 = driver.getWindowHandle();
		ArrayList<String> newTabs = new ArrayList<String>(driver.getWindowHandles());
		newTabs.remove(tab1);
		int i = newTabs.size() - 1;
		driver.switchTo().window(newTabs.get(i));

	}
	

	@AfterMethod
	public void getResult(ITestResult result) {

		if (result.getStatus() == ITestResult.FAILURE) {
			childTest.log(Status.FAIL, "Test Case Failed is " + result.getName());
			childTest.log(Status.FAIL, "Test Case Failed is " + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			childTest.log(Status.SKIP, "Test Case Skipped is " + result.getName());
		}
		report.flush();

	}

	 @AfterTest
	 public void tearDown() {
	 report.flush();
	 driver.quit();
	 }

}
