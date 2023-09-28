package vaporstream.Perzona.testUtils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

public class Listeners implements ITestListener {

	ExtentReports extent = ExtentReporterNG.getReporterObject();
	ExtentTest test;
	AppiumDriver driver;

	@Override
	public void onTestStart(ITestResult result) {
		// Obtiene el nombre del metodo del test y lo usa para dar nombre al test en el reporte
		test = extent.createTest(result.getMethod().getMethodName(),"Esta es la descripción del Test.");

		test.log(Status.INFO, MarkupHelper.createLabel("<b>Test Parameters</b>", ExtentColor.INDIGO));

		// Prueba de mostrar los parametros de las pruebas
		Object[] parameters = result.getParameters();
		String parametersString = parameters.toString();
		test.log(Status.INFO, parametersString);


	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.fail(result.getThrowable()); // provocamos la registracion de la falla en el reporter
		test.log(Status.INFO, "Mensaje de Prueba > <i>Texto con formato ITALICO</i>");
		test.log(Status.INFO, "Mensaje de Prueba > <b>Texto con formato BOLD</b>");
		test.log(Status.INFO, "Mensaje de Prueba > <u>Texto con formato UNDERLINE</u>");

		// Log Exception
		try {
			int a = 9 / 0;
		} catch (Exception e) {
			test.fail(e);
		}

		// XML Content
		String xmlContent = "<menu id=\"file\" value=\"This is an example of XML Content on a report log\">\r\n"
				+ "  <popup>\r\n" + "    <menuitem value=\"New\" onclick=\"CreateNewDoc()\" />\r\n"
				+ "    <menuitem value=\"Open\" onclick=\"OpenDoc()\" />\r\n"
				+ "    <menuitem value=\"Close\" onclick=\"CloseDoc()\" />\r\n" + "  </popup>\r\n" + "</menu>";
		test.log(Status.INFO, MarkupHelper.createCodeBlock(xmlContent, CodeLanguage.XML));

		// JSON Content
		String jsonContent = "{\"This is an example of JSON Content on a report log\": {\r\n" + "  \"id\": \"file\",\r\n"
				+ "  \"value\": \"File\",\r\n" + "  \"popup\": {\r\n" + "    \"menuitem\": [\r\n"
				+ "      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\r\n"
				+ "      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\r\n"
				+ "      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\r\n" + "    ]\r\n" + "  }\r\n" + "}}";
		test.log(Status.INFO, MarkupHelper.createCodeBlock(jsonContent, CodeLanguage.JSON));

		// Array List - Ordered
		ArrayList<String> alist = new ArrayList<String>();
		alist.add("Sunday");
		alist.add("Monday");
		alist.add("Tuesday");
		alist.add("Wednesday");
		alist.add("Thursday");
		alist.add("Friday");
		alist.add("Saturday");
		test.log(Status.INFO, MarkupHelper.createOrderedList(alist));

		// Array List - Unordered
		ArrayList<String> alist2 = new ArrayList<String>();
		alist2.add("Red");
		alist2.add("Blue");
		alist2.add("Green");
		alist2.add("Yellow");
		alist2.add("Black");
		alist2.add("Purple");
		alist2.add("White");
		test.log(Status.INFO, MarkupHelper.createUnorderedList(alist2));

		// HashSet - Ordered List
		HashSet<String> aset1 = new HashSet<String>();
		aset1.add("Sunday");
		aset1.add("Monday");
		aset1.add("Tuesday");
		aset1.add("Wednesday");
		aset1.add("Thursday");
		aset1.add("Friday");
		aset1.add("Saturday");
		test.log(Status.INFO, MarkupHelper.createOrderedList(aset1));

		// HashSet - UnOrdered List
		HashSet<String> aset2 = new HashSet<String>();
		aset2.add("Red");
		aset2.add("Blue");
		aset2.add("Green");
		aset2.add("Yellow");
		aset2.add("Black");
		aset2.add("Purple");
		aset2.add("White");
		test.log(Status.INFO, MarkupHelper.createUnorderedList(aset2));

		// HashMap - HashMap
		HashMap<String, Object> hmap1 = new HashMap<String, Object>();
		hmap1.put("countryCode", "49");
		hmap1.put("countryName", "Germany");
		hmap1.put("randomPhoneNumber", true);
		hmap1.put("phoneNumber", "3490217");
		hmap1.put("invalidPhoneNumberTest", false);
		hmap1.put("fullName", "Aut Test Germany 002");
		hmap1.put("randomUsername", true);
		hmap1.put("username", "AutTGerman001");
		hmap1.put("wrongOTPTest", false);
		hmap1.put("delayedOTPTest", false);
		hmap1.put("setAvatar", false);
		hmap1.put("setAdditionalInfo", false);
		hmap1.put("aboutUser", "Automation Test User");
		hmap1.put("websiteUrl", "www.automationtest.io");
		hmap1.put("contactSyncTest", false);
		test.log(Status.INFO, MarkupHelper.createUnorderedList(hmap1));

		try {
			driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			test.addScreenCaptureFromPath(getScreenshotPath(result.getMethod().getMethodName(),driver), result.getMethod().getMethodName());
//		} catch (IOException e){
//			e.printStackTrace();
//		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		test.warning("Mensaje de Prueba > Warning");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		test.warning("Este es un mensaje Warning de Prueba");

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
		// Apertura Automática de Página de Reporte
		File file = new File(System.getProperty("user.dir") + "\\reports\\index.html");
		try {
			Desktop.getDesktop().browse(file.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
