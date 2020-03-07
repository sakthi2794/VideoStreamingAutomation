package com.HippoVideo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.HippoVideo.Test.BaseClass;
import com.aventstack.extentreports.Status;
/* 
 * This Class is having methods to record a video 
 * Personalize the video
 */
public class ScreenRecording extends BaseClass {

	public void createVideo() throws InterruptedException {
		childTest=logger.createNode("Creating New Video");
		clickOnID("ly_20000000004");
		String dropDown = driver.findElement(By.xpath("//div[@class='importPanel']")).getAttribute("class");
		if (!dropDown.contains("hide")) {
			clickOn("//section[@data-name='Create video _+']");
			boolean returnStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("startRecording")))
					.isDisplayed();
			if (returnStatus) {
				
				clickOnID("startRecording");
				childTest.log(Status.INFO, "Screen Sharing Started");
				boolean stopRecording = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.id("stopRecording"))).isDisplayed();
				if (stopRecording) {
					Thread.sleep(20000); //To record the video for More than one Sec 
					clickOnID("stopRecording");
					boolean personalizationTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("personlizationTab-heading"))).isDisplayed();
					if(personalizationTab) {
						childTest.log(Status.PASS, "Video Recorded Sucessfully");
						personalizeVideo();
					}
					
				}
			}
		}

	}
	
	public void personalizeVideo() throws InterruptedException {
		childTest = logger.createNode("Personalization of the Created Video");
		clickOnID("personlizationTab-heading");
		String personalizationTab = driver.findElement(By.id("personlizationTab")).getAttribute("class");
		if(personalizationTab.contains("active")) {
			clickOnID("videoPersonalization");
			String videoTab = driver.findElement(By.id("videoPersonalization")).getAttribute("class");
			if(videoTab.contains("active")) {
				childTest.log(Status.INFO, "Adding Text to the video");
				clickOnID("personalizeButton");
				wait.until(ExpectedConditions.elementToBeClickable(By.id("saveSimpleEdit")));
				clickOnID("addTextBtn");
				clickOnID("saveSimpleEdit");
				childTest.log(Status.PASS, "Video Personalized Successfully ");
		
	}

}
	}
}
