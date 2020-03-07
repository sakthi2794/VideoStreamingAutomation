package com.HippoVideo.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.HippoVideo.Test.BaseClass;
import com.aventstack.extentreports.Status;
/* 
 * This page is having the methods to add Hippovideo extension to chrome browser  
 * Creating an Account 
 * Initial Setup for the Account Created
 */
public class SignUp extends BaseClass {
    
	public void initiate() throws IOException {
		this.addExtension();
		this.createNewAcc();
		this.initialSetuUp();

	}
	public void addExtension() throws IOException {
		childTest=logger.createNode("Adding Hippo Videos Extension To the Browser");
		String Url = getData("URL");
		launchBrowser(Url);
	}

	public void createNewAcc() throws IOException {
		childTest=logger.createNode("Creating New Account ");
		String homePage = driver.getTitle();
		if (homePage.contains("Hippo Video")) {
			childTest.log(Status.INFO, "Landed on HomePage : " + homePage);
			clickOn("//div[@class='fa-wrap webinar-banner-wrap']//div[contains(@class,'close')]");
			clickOn("//button[@class='btn btn-primary hv-primary-btn signup-dum input-trigger']");
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(
					By.xpath("//div[@class='col s-part']//input[@class='form-control email input-trigger']"))));
			typeOn("//div[@class='col s-part']//input[@class='form-control email input-trigger']", "Email");
			typeOn("//div[@class='col s-part']//input[@class='form-control password input-trigger']", "Password");
			clickOn("//div[@class='col s-part']//button[@class='btn btn-primary hv-primary-btn f-btn signup-btn']");

		}
	}

	public void initialSetuUp() throws IOException {
		
		boolean initialSetUpPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Business-4")))
				.isDisplayed();
		childTest.log(Status.PASS, "Account Created Succesfully");
		if (initialSetUpPage) {
			childTest.log(Status.INFO, "Initial Setup Page");
			clickOnID("Business-4");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("Marketing")));
			clickOnID("Marketing");
			String afterClick = driver.findElement(By.id("Marketing")).getAttribute("class");
			if (afterClick.contains("active")) {
				clickOnID("Business-next-btn");
				wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("saveCompanyName"))));
				typeOnId("firstNameTxt", "FirstName");
				typeOnId("companyNameTxt", "CompanyName");
				typeOnId("phoneTxt", "PhoneNumber");
				clickOnID("saveCompanyName");
				wait.until(ExpectedConditions.elementToBeClickable(By.id("ly_20000000004")));
				childTest.log(Status.PASS, "Initial Setup Successfully done ");

			}
		}
	}

}
