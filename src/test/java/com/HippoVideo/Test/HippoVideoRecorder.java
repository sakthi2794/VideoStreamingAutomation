package com.HippoVideo.Test;

import org.testng.annotations.Test;

import com.HippoVideo.pages.EmailVerification;
import com.HippoVideo.pages.ScreenRecording;
import com.HippoVideo.pages.SignUp;
import com.HippoVideo.pages.VideoCampaings;

//This Class will Act as a Caller Function, this class only initiate first and from here all the class will start execute
public class HippoVideoRecorder extends BaseClass {

	public final static SignUp signUp = new SignUp();
	public final static ScreenRecording rec = new ScreenRecording();
	public final static VideoCampaings video = new VideoCampaings();
	public final static EmailVerification email = new EmailVerification();

	@Test
	public static void helper() {
		try {
			logger = report.createTest("Account Creation and Initial Setup ");
			signUp.initiate();
			logger = report.createTest("Video Creation and Personalization of the Video");
			rec.createVideo();
			logger = report.createTest("Video Campign");
			video.videoCamping();
			logger = report.createTest("Email Validation of the Created Video");
			email.emailValidation();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
