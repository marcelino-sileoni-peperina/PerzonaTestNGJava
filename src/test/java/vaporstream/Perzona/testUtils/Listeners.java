package vaporstream.Perzona.testUtils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.appium.java_client.AppiumDriver;
import vaporstream.Perzona.utils.AppiumUtils;

public class Listeners extends AppiumUtils implements ITestListener, IHookable {

	ExtentReports extent = ExtentReporterNG.getReporterObject();
	ExtentTest test;
	AppiumDriver driver;

	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName(), result.getTestContext().getName());
		
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
//		System.out.println("Context: " + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		// Update Reports
		extent.flush();
		// Apertura Automática de Página de Reporte
		File file = new File(System.getProperty("user.dir") + "\\reports\\index.html");
		try {
			Desktop.getDesktop().browse(file.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run(IHookCallBack callBack, ITestResult testResult) {
		// TODO Auto-generated method stub
		Object[] parameterValues = callBack.getParameters();
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

		test.log(Status.INFO, MarkupHelper.createLabel("<b>Testing Paramaters</b>", ExtentColor.GREY));
		test.log(Status.INFO, "Country Code: " + countryCode);
		test.log(Status.INFO, "Country Name: " + countryName);
		test.log(Status.INFO, "Use Aleatory Phone Number: " + (randomPhoneNumber == true ? "Yes" : "No"));
		if (!randomPhoneNumber)
			test.log(Status.INFO, "Phone Number: " + phoneNumber);
		test.log(Status.INFO, "Test Invalid Phone Number: " + (invalidPhoneNumberTest == true ? "Yes" : "No"));
		test.log(Status.INFO, "Test Phone Number Edition: " + (editPhoneNumberTest == true ? "Yes" : "No"));
		test.log(Status.INFO, "Test Invalid OTP: " + (wrongOTPTest == true ? "Yes" : "No"));
		test.log(Status.INFO, "Test OTP timeout: " + (delayedOTPTest == true ? "Yes" : "No"));
		test.log(Status.INFO, "Full Name: " + fullName);
		test.log(Status.INFO, "Use Aleatory Username Value: " + (randomUsername == true ? "Yes" : "No"));
		if (!randomUsername)
			test.log(Status.INFO, "User Name: " + username);
		test.log(Status.INFO, "Include Avatar on Test: " + (setAvatar == true ? "Yes" : "No"));
		if (setAvatar)
			test.log(Status.INFO, "Profile Gender: " + profileGender);
		test.log(Status.INFO, "Add User Additional Information: " + (setAdditionalInfo == true ? "Yes" : "No"));
		if (setAdditionalInfo) {
			test.log(Status.INFO, "About User Info: " + aboutUser);
			test.log(Status.INFO, "WebSite of User: " + websiteUrl);
		}
		test.log(Status.INFO, "Add Contacts Syncronizaton Test: " + (contactSyncTest == true ? "Yes" : "No"));

		callBack.runTestMethod(testResult);
	}

}

// PRUEBAS
//test.log(Status.INFO, "Mensaje de Prueba > <i>Texto con formato ITALICO</i>");
//test.log(Status.INFO, "Mensaje de Prueba > <b>Texto con formato BOLD</b>");
//test.log(Status.INFO, "Mensaje de Prueba > <u>Texto con formato UNDERLINE</u>");

// Log Exception
//try {
//int a = 9 / 0;
//} catch (Exception e) {
//test.fail(e);
//}

// XML Content
//String xmlContent = "<menu id=\"file\" value=\"This is an example of XML Content on a report log\">\r\n"
//	+ "  <popup>\r\n" + "    <menuitem value=\"New\" onclick=\"CreateNewDoc()\" />\r\n"
//	+ "    <menuitem value=\"Open\" onclick=\"OpenDoc()\" />\r\n"
//	+ "    <menuitem value=\"Close\" onclick=\"CloseDoc()\" />\r\n" + "  </popup>\r\n" + "</menu>";
//test.log(Status.INFO, MarkupHelper.createCodeBlock(xmlContent, CodeLanguage.XML));

// JSON Content
//String jsonContent = "{\"This is an example of JSON Content on a report log\": {\r\n" + "  \"id\": \"file\",\r\n"
//	+ "  \"value\": \"File\",\r\n" + "  \"popup\": {\r\n" + "    \"menuitem\": [\r\n"
//	+ "      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\r\n"
//	+ "      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\r\n"
//	+ "      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\r\n" + "    ]\r\n" + "  }\r\n" + "}}";
//test.log(Status.INFO, MarkupHelper.createCodeBlock(jsonContent, CodeLanguage.JSON));

// Array List - Ordered
//ArrayList<String> alist = new ArrayList<String>();
//alist.add("Sunday");
//alist.add("Monday");
//alist.add("Tuesday");
//alist.add("Wednesday");
//alist.add("Thursday");
//alist.add("Friday");
//alist.add("Saturday");
//test.log(Status.INFO, MarkupHelper.createOrderedList(alist));

// Array List - Unordered
//ArrayList<String> alist2 = new ArrayList<String>();
//alist2.add("Red");
//alist2.add("Blue");
//alist2.add("Green");
//alist2.add("Yellow");
//alist2.add("Black");
//alist2.add("Purple");
//alist2.add("White");
//test.log(Status.INFO, MarkupHelper.createUnorderedList(alist2));

// HashSet - Ordered List
//HashSet<String> aset1 = new HashSet<String>();
//aset1.add("Sunday");
//aset1.add("Monday");
//aset1.add("Tuesday");
//aset1.add("Wednesday");
//aset1.add("Thursday");
//aset1.add("Friday");
//aset1.add("Saturday");
//test.log(Status.INFO, MarkupHelper.createOrderedList(aset1));

// HashSet - UnOrdered List
//HashSet<String> aset2 = new HashSet<String>();
//aset2.add("Red");
//aset2.add("Blue");
//aset2.add("Green");
//aset2.add("Yellow");
//aset2.add("Black");
//aset2.add("Purple");
//aset2.add("White");
//test.log(Status.INFO, MarkupHelper.createUnorderedList(aset2));

// HashMap - HashMap
//HashMap<String, Object> hmap1 = new HashMap<String, Object>();
//hmap1.put("countryCode", "49");
//hmap1.put("countryName", "Germany");
//hmap1.put("randomPhoneNumber", true);
//hmap1.put("phoneNumber", "3490217");
//hmap1.put("invalidPhoneNumberTest", false);
//hmap1.put("fullName", "Aut Test Germany 002");
//hmap1.put("randomUsername", true);
//hmap1.put("username", "AutTGerman001");
//hmap1.put("wrongOTPTest", false);
//hmap1.put("delayedOTPTest", false);
//hmap1.put("setAvatar", false);
//hmap1.put("setAdditionalInfo", false);
//hmap1.put("aboutUser", "Automation Test User");
//hmap1.put("websiteUrl", "www.automationtest.io");
//hmap1.put("contactSyncTest", false);
//test.log(Status.INFO, MarkupHelper.createUnorderedList(hmap1));

//try {
//driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
//} catch (Exception e) {
//e.printStackTrace();
//}
