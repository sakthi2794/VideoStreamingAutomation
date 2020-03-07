package com.HippoVideo.pages;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.HippoVideo.Test.BaseClass;
import com.aventstack.extentreports.Status;

public class VideoCampaings extends BaseClass {

	public void videoCamping() throws InterruptedException, IOException {

		boolean videoCampingTab = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("videoCampaignsTab-heading"))).isDisplayed();
		if (videoCampingTab) {
			childTest=logger.createNode("Sending Videos to the Mail");
			clickOnID("videoCampaignsTab-heading");
			String videoCampign = driver.findElement(By.id("videoCampaignsTab")).getAttribute("class");
			if (videoCampign.contains("active")) {
				clickOnID("videoCampaign");
				childTest.log(Status.INFO, "Configuring Mail");
				String parentWindow = getCurrentWindow();
//				String parentWindow = driver.getWindowHandle();
				System.out.println(driver.getCurrentUrl());
				clickOnID("grantAccessBtn");
				switchToNewTab();
				boolean configurationWindow = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.id("configureGmailSettingsBtn")))
						.isDisplayed();
				if (configurationWindow) {
					clickOn("//img[@id='configureGmailSettingsBtn']/parent::a");
					switchToOtherTab();
					Thread.sleep(3000);
					typeOnId("identifierId", "CustomerEmail");
					clickOnID("identifierNext");
					Thread.sleep(3000);
					// wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@type='password']"))));
					typeOn("//input[@type='password']", "Customerpwd");
					clickOnID("passwordNext");
				    // wait.until(ExpectedConditions.elementToBeClickable(By.id("submit_approve_access")));
					Thread.sleep(5000);
					clickOnID("submit_approve_access");
					driver.switchTo().window(parentWindow);
					boolean emailConfigured = wait
							.until(ExpectedConditions.visibilityOfElementLocated(By.id("importOrContacts")))
							.isDisplayed();
					if (emailConfigured) {
						childTest.log(Status.PASS, "From Email Address has been Configured Successfully");
						sendVideoToMail();
					}
				}
			}
		}
	}

	public void sendVideoToMail() throws IOException {
		childTest.log(Status.INFO, "Adding Mail ID's to Send the created Video");
        driver.findElement(By.className("add-contacts-btn")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("addContacts")));
		typeOn("//div[@class='large-3 columns direct-contacts-email']/input[1]", "CustomerEmail");
	    clickOnID("addContacts");
	    typeOnId("emailSubject", "EmailSubject");
	    clickOnID("sendEmailBtn");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("sendEmailBtn")));
		childTest.log(Status.PASS, "Email has been sent to contact ");

	}

}
