package vaporstream.Perzona.testUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import vaporstream.Perzona.utils.AppiumUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SignInListeners extends AppiumUtils implements ITestListener {
  
  ExtentReports extent = ExtentReporterNG.getReporterObject();
  ExtentTest test;
  AppiumDriver driver;
  
  @Override
  public void onTestStart(ITestResult result) {
    test = extent.createTest(result.getMethod().getMethodName(), result.getTestContext().getName());
    Object[] parameterValues = result.getParameters();
    String countryCode = (String) parameterValues[0];
    String countryName = (String) parameterValues[1];
    String phoneNumber = (String) parameterValues[2];
    Boolean invalidPhoneNumberTest = (Boolean) parameterValues[3];
    Boolean editPhoneNumberTest = (Boolean) parameterValues[4];
    Boolean wrongOTPTest = (Boolean) parameterValues[5];
    Boolean delayedOTPTest = (Boolean) parameterValues[6];
    
    test.log(Status.INFO, MarkupHelper.createLabel("<b>Testing Parameters</b>", ExtentColor.GREY));
    test.log(Status.INFO, "Country Code: " + countryCode);
    test.log(Status.INFO, "Country Name: " + countryName);
    test.log(Status.INFO, "Phone Number: " + phoneNumber);
    test.log(Status.INFO, "Test Invalid Phone Number: " + (invalidPhoneNumberTest ? "Yes" : "No"));
    test.log(Status.INFO, "Test Phone Number Edition: " + (editPhoneNumberTest ? "Yes" : "No"));
    test.log(Status.INFO, "Test Invalid OTP: " + (wrongOTPTest ? "Yes" : "No"));
    test.log(Status.INFO, "Test OTP timeout: " + (delayedOTPTest ? "Yes" : "No"));
    
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
  }
  
  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    // TODO Auto-generated method stub
    test.warning("Warning Test Message");
  }
  
  @Override
  public void onStart(ITestContext context) {
    // TODO Auto-generated method stub
    // System.out.println("Context: " + context.getName());
  }
  
  @Override
  public void onFinish(ITestContext context) {
    // Update Reports
    extent.flush();
    // Apertura Automática de Página de Reporte
    File file = new File(System.getProperty("user.dir") + File.separator + "reports" + File.separator + "index.html");
    try {
      Desktop.getDesktop().browse(file.toURI());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}