package com.HippoVideo.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.HippoVideo.Test.BaseClass;
import com.aventstack.extentreports.Status;

import org.openqa.selenium.JavascriptExecutor;
/*
 * This class is having the methods to check the email has been received from the account
 */
public class EmailVerification extends BaseClass {

	public void emailValidation() throws InterruptedException, IOException {
		childTest=logger.createNode("Email Validation ");
		childTest.log(Status.INFO, "Checking the Email has been received or not");
		String parentWindow = getCurrentWindow();
		openNewWindow();
	    switchToNewTab();
		driver.get(getData("GmailURL"));
		Thread.sleep(10000);
		clickOn("//button[@class='gb_tf']");
		Thread.sleep(2000);
		typeOn("//input[@class='ZH nr aQa']", "EmailFrom");
		typeOn("//input[@class='ZH nr aQd']", "EmailSubject");
		clickOn("//div[contains(@class,'T-I J-J5-Ji Zx aQe T-I-atl L3')]");
		List<WebElement> emailList = driver
				.findElements(By.xpath("//div[contains(@class,'UI')]//tr//td[@role='gridcell']//span/span"));
		System.out.println(emailList);
		for (WebElement emailSubjectText : emailList) {
			System.out.println(emailSubjectText.getText());
			if (emailSubjectText.getText().equals("Email Subject from Automation") == true) {
				emailSubjectText.click();
				break;
				}
			childTest.log(Status.PASS, "Email has been Received with Video Created");
		}
		driver.close();
		driver.switchTo().window(parentWindow);
		String backToHippo = driver.getTitle();
		if (backToHippo.contains("Hippo Video")) {
			signOut();
		}
	}

	public void signOut() {
		logger = report.createTest("SignOut");
		childTest=logger.createNode("Signing from the account");
		clickOnID("ly_20000000007");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("userSignOut")));
		clickOnID("userSignOut");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='nav-link log-btn']")));
		childTest.log(Status.PASS, "Signed Out Successfully");
	}

}
