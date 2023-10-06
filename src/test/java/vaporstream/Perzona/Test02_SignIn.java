package vaporstream.Perzona;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.appium.java_client.android.Activity;
import vaporstream.Perzona.pageObjects.android.*;
import vaporstream.Perzona.testUtils.AndroidTestBase;
import vaporstream.Perzona.utils.*;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Test02_SignIn extends AndroidTestBase {

	// To find App Package & App Activity:
	// 1- Open the app on the emulator and leave it on the page you want to identify
	// 2- Run the following command:
	// Windows: adb shell dumpsys window | findstr "mCurrentFocus"
	// Linux/Mac: adb shell dumpsys window | grep -e "mCurrentFocus"
	// The result would be of the form:
	// mCurrentFocus=Window{c04cf29 u0 PACKAGE/ACTIVITY}

	@SuppressWarnings("deprecation")
	@BeforeMethod
	public void SetupTest() {
//		Activity firstPage = new Activity("vaporstream.perzonas_tst", "vaporstream.perzonas_tst.MainActivity");
//		driver.startActivity(firstPage);
		driver.resetApp();
	}

	@Test(dataProvider = "userData")
	public void SignInTest(String phoneNumber, String countryCode, String countryName)
			throws MalformedURLException, InterruptedException {

		System.out.println("SignIn Test Started ----------------------");
		System.out.println("Phone Number: +" + countryCode + phoneNumber);
		// GET OTP CODE
		String otpCode = ExternalServices.getOTP(countryCode, phoneNumber);

		// On Boarding Screen
		OnBoardingScreen onBoardingScreen = new OnBoardingScreen(driver);
		onBoardingScreen.getStartedClick();

		// Sign up Screen
		SignUpScreen singUpScreen = new SignUpScreen(driver);
		singUpScreen.setCountrySelection(countryName, countryCode);
		singUpScreen.setPhoneNumber(phoneNumber);

		// Verify Screen
		VerifyScreen verifyScreen = new VerifyScreen(driver);
		verifyScreen.setCodeField(otpCode);

		Thread.sleep(5000);
		System.out.println("SignIn Test Finished ----------------------");
	}

	// JSON DATA PROVIDER - Toma todos los elementos del JSON con los parametros de
	// prueba

	@DataProvider(name = "userData")
	public static Iterator<Object[]> provideTestData() throws IOException {
		List<Object[]> testDataList = new ArrayList<>();

		// Specify the path to your JSON file
		String jsonFilePath = System.getProperty("user.dir")
				+ "\\src\\test\\java\\vaporstream\\Perzona\\testData\\PerzonaTestData_SignIn.json";

		// Use Gson to parse the JSON file
		JsonElement jsonData = JsonParser.parseReader(new FileReader(jsonFilePath));
		JsonArray jsonArray = jsonData.getAsJsonArray();

		for (JsonElement element : jsonArray) {
			String phoneNumber = element.getAsJsonObject().get("phoneNumber").getAsString();
			String countryCode = element.getAsJsonObject().get("countryCode").getAsString();
			String countryName = element.getAsJsonObject().get("countryName").getAsString();

			// Add the test data as an object array to the list
			testDataList.add(new Object[] { phoneNumber, countryCode, countryName });
		}

		return testDataList.iterator();
	}

}
