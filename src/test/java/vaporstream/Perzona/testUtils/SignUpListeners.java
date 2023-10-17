package vaporstream.Perzona.testUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.appium.java_client.AppiumDriver;
import org.testng.*;
import org.testng.annotations.Listeners;
import vaporstream.Perzona.utils.AppiumUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@Listeners(vaporstream.Perzona.testUtils.SignUpListeners.class)
// public class SignUpListeners extends AppiumUtils implements ITestListener,
// IHookable {
public class SignUpListeners extends AppiumUtils implements ITestListener {
	
	ExtentReports extent = ExtentReporterNG.getReporterObject();
	ExtentTest test;
	AppiumDriver driver;
	
	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName(), result.getTestContext().getName());
		// System.out.println(Arrays.toString(result.getParameters()));
		Object[] parameterValues = result.getParameters();
		String countryCode = (String) parameterValues[0];
		String countryName = (String) parameterValues[1];
		String phoneNumber = (String) parameterValues[2];
		String fullName = (String) parameterValues[3];
		String username = (String) parameterValues[4];
		String aboutUser = (String) parameterValues[5];
		String websiteUrl = (String) parameterValues[6];
		String profileGender = (String) parameterValues[7];
		Boolean randomPhoneNumber = (Boolean) parameterValues[8];
		Boolean invalidPhoneNumberTest = (Boolean) parameterValues[9];
		Boolean editPhoneNumberTest = (Boolean) parameterValues[10];
		Boolean wrongOTPTest = (Boolean) parameterValues[11];
		Boolean delayedOTPTest = (Boolean) parameterValues[12];
		Boolean randomUsername = (Boolean) parameterValues[13];
		Boolean setAvatar = (Boolean) parameterValues[14];
		Boolean setAdditionalInfo = (Boolean) parameterValues[15];
		Boolean contactSyncTest = (Boolean) parameterValues[16];
		
		test.log(Status.INFO, MarkupHelper.createLabel("<b>Testing Parameters</b>", ExtentColor.GREY));
		test.log(Status.INFO, "Country Code: " + countryCode);
		test.log(Status.INFO, "Country Name: " + countryName);
		test.log(Status.INFO, "Use Aleatory Phone Number: " + (randomPhoneNumber ? "Yes" : "No"));
		if (!randomPhoneNumber)
			test.log(Status.INFO, "Phone Number: " + phoneNumber);
		test.log(Status.INFO, "Test Invalid Phone Number: " + (invalidPhoneNumberTest ? "Yes" : "No"));
		test.log(Status.INFO, "Test Phone Number Edition: " + (editPhoneNumberTest ? "Yes" : "No"));
		test.log(Status.INFO, "Test Invalid OTP: " + (wrongOTPTest ? "Yes" : "No"));
		test.log(Status.INFO, "Test OTP timeout: " + (delayedOTPTest ? "Yes" : "No"));
		test.log(Status.INFO, "Full Name: " + fullName);
		test.log(Status.INFO, "Use Aleatory Username Value: " + (randomUsername ? "Yes" : "No"));
		if (!randomUsername)
			test.log(Status.INFO, "User Name: " + username);
		test.log(Status.INFO, "Include Avatar on Test: " + (setAvatar ? "Yes" : "No"));
		if (setAvatar)
			test.log(Status.INFO, "Profile Gender: " + profileGender);
		test.log(Status.INFO, "Add User Additional Information: " + (setAdditionalInfo ? "Yes" : "No"));
		if (setAdditionalInfo) {
			test.log(Status.INFO, "About User Info: " + aboutUser);
			test.log(Status.INFO, "WebSite of User: " + websiteUrl);
		}
		test.log(Status.INFO, "Add Contacts Syncronizaton Test: " + (contactSyncTest ? "Yes" : "No"));
		
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, result.getMethod().getMethodName() + " Test Passed");
		try {
			driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			test.addScreenCaptureFromPath(getScreenshotPath(result.getMethod().getMethodName(), driver),
							result.getMethod().getMethodName() + " final screen");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		test.fail(result.getThrowable()); // provocamos la registracion de la falla en el reporter
		try {
			driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			test.addScreenCaptureFromPath(getScreenshotPath(result.getMethod().getMethodName(), driver),
							result.getMethod().getMethodName() + "failure screen");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		test.warning("Test Skipped");
		test.warning(result.getThrowable());
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		test.warning("Warning Test Message");
	}
	
	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println("Context: " + context.getName());
		
	}
	
	@Override
	public void onFinish(ITestContext context) {
		// Update Reports
		extent.flush();
		// Automatic Opening of Report Web Page
		File file = new File(System.getProperty("user.dir") + "\\reports\\index.html");
		try {
			Desktop.getDesktop().browse(file.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}