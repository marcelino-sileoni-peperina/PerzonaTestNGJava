package vaporstream.Perzona;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.appium.java_client.android.Activity;
import vaporstream.Perzona.pageObjects.android.*;
import vaporstream.Perzona.testUtils.AndroidTestBase;
import vaporstream.Perzona.utils.*;

import java.io.FileWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Test01_SignUp extends AndroidTestBase {

	@SuppressWarnings("deprecation")
	@BeforeMethod
	public void SetupTest() {
		// To find App Package & App Activity:
		// 1- Open the app on the emulator and leave it on the page you want to identify
		// 2- Run the following command:
		// Windows: adb shell dumpsys window | findstr "mCurrentFocus"
		// Linux/Mac: adb shell dumpsys window | grep -e "mCurrentFocus"
		// The result would be of the form:
		// mCurrentFocus=Window{c04cf29 u0 PACKAGE/ACTIVITY}
		driver.resetApp(); // deprecado pero funciona !!
//		Activity firstPage = new Activity("vaporstream.perzonas_tst", "vaporstream.perzonas_tst.MainActivity");
//		driver.startActivity(firstPage);
	}

	@Test(dataProvider = "userData")
	public void SignUpTest(String countryCode, String countryName, String phoneNumber, String fullName, String username,
			String aboutUser, String websiteUrl, String profileGender, boolean randomPhoneNumber, boolean invalidPhoneNumberTest,
			boolean editPhoneNumberTest, boolean wrongOTPTest, boolean delayedOTPTest, boolean randomUsername,
			boolean setAvatar, boolean setAdditionalInfo, boolean contactSyncTest) throws InterruptedException, IOException {

		System.out.println("---- SignUp Test Started ----");
		System.out.println("Testing Paramaters:");
		System.out.println("Country Code: " + countryCode);
		System.out.println("Country Name: " + countryName);
		System.out.println("Use Aleatory Phone Number: " + (randomPhoneNumber == true ? "Yes" : "No"));
		if (!randomPhoneNumber)
			System.out.println("Phone Number: " + phoneNumber);
		System.out.println("Test Invalid Phone Number: " + (invalidPhoneNumberTest == true ? "Yes" : "No"));
		System.out.println("Test Phone Number Edition: " + (editPhoneNumberTest == true ? "Yes" : "No"));
		System.out.println("Test Invalid OTP: " + (wrongOTPTest == true ? "Yes" : "No"));
		System.out.println("Test OTP timeout: " + (delayedOTPTest == true ? "Yes" : "No"));
		System.out.println("Full Name: " + fullName);
		System.out.println("Use Aleatory Username Value: " + (randomUsername == true ? "Yes" : "No"));
		if (!randomUsername)
			System.out.println("User Name: " + username);
		System.out.println("Include Avatar on Test: " + (setAvatar == true ? "Yes" : "No"));
		System.out.println("Add User Additional Information: " + (setAdditionalInfo == true ? "Yes" : "No"));
		if (setAdditionalInfo) {
			System.out.println("About User Info: " + aboutUser);
			System.out.println("WebSite of User: " + websiteUrl);
		}
		System.out.println("Add Contacts Syncronizaton Test: " + (contactSyncTest == true ? "Yes" : "No"));
		System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

		SoftAssert softAssert = new SoftAssert();
		// On Boarding Screen
		OnBoardingScreen onBoardingScreen = new OnBoardingScreen(driver);
		onBoardingScreen.getStartedClick();

		// Aleatory Phone Number Generator
		if (randomPhoneNumber) {
			phoneNumber = PhoneNumberGenerator.getNewPhoneNumber(countryCode, true);
		}
		// Delete Phone Number Registered from Database
		User.deletePhoneNumberFromDB(countryCode, phoneNumber);

		// Validate Pre-existing User
//		boolean SignUped = User.validatePreviousSignUp(countryCode, phoneNumber);

		// Sign up Screen
		SignUpScreen signUpScreen = new SignUpScreen(driver);
		signUpScreen.setCountrySelection(countryName, countryCode);

		if (invalidPhoneNumberTest) {
			System.out.println("--- Starting Invalid Phone Number Test ---");
			String invalidPhoneNumber = PhoneNumberGenerator.getNewPhoneNumber(countryCode, false);
			signUpScreen.setPhoneNumber(invalidPhoneNumber);
			signUpScreen.verifyInvalidPhoneMessage(softAssert);
//			signUpScreen.setPhoneNumber(phoneNumber);
			System.out.println("--- End of Invalid Phone Number Test ---");
		} else {
			System.out.println("--- Sign Up with Given Phone Number Test ---");
//			signUpScreen.setPhoneNumber(phoneNumber);
		}

		if (editPhoneNumberTest) {
			String permutedPhoneNuber = PhoneNumberGenerator.getPermutedPhoneNumber(phoneNumber);
			signUpScreen.setPhoneNumber(permutedPhoneNuber);
			Thread.sleep(500);
			signUpScreen.continueToVerifyPhoneNumber();
			Thread.sleep(1000);
			signUpScreen.editPhoneNumber();
			Thread.sleep(1000);
			;
		}
		signUpScreen.setPhoneNumber(phoneNumber);
		Thread.sleep(500);
		signUpScreen.continueToVerifyPhoneNumber();
		// Go to OTP
		Thread.sleep(1000); // SIN ESTA PAUSA NO FUNCIONA !!
		signUpScreen.continueToOTP();

		System.out.println("--- Start of OTP Test ---");
		// Get OTP Code
		String otpCode = OTPGenerator.getOTP(countryCode, phoneNumber);

		// Verify Screen (OTP Validation)
		VerifyScreen verifyScreen = new VerifyScreen(driver);
		// Wrong OTP TEST
		if (wrongOTPTest) {
			System.out.println("--- Starting Invalid OTP Test ---");
			String invalidOtpCode = OTPGenerator.invalidOTP(otpCode);
			verifyScreen.setCodeField(invalidOtpCode);
//			Thread.sleep(1000);
			verifyScreen.clickOK();
			System.out.println("--- End of Invalid OTP Test ---");
		}
		// Delayed OTP TEST
		if (delayedOTPTest) {
			System.out.println("--- Starting Timeout OTP Test ---");
			Thread.sleep(55000); // ESTA SALTANDO POR TIMEOUT TODO EL SCRIPT - REVISAR !!!
//			verifyScreen.setCodeField(otpCode); // ESTE DEBERIA FALLAR PERO AL 19/09/23 NO FALLA PORQUE LO HAN CONFIGURADO ASI EN LA APP
//			verifyScreen.clickOK(); // SE COMENTA HASTA QUE SE RESUELVA LO COMENTADO ARRIBA
			verifyScreen.requestNewOTP();
			Thread.sleep(2000);
			otpCode = OTPGenerator.getOTP(countryCode, phoneNumber);
			System.out.println("--- End of Timeout OTP Test ---");
		}
		// Normal OTP Test
		verifyScreen.setCodeField(otpCode);
		System.out.println("--- End of OTP Test ---");

		// Your Information Screen -------------------------
		System.out.println("--- Starting Profile Screen Test ---");
		ProfileScreen profileScreen = new ProfileScreen(driver);

		profileScreen.setFullName(fullName); // Set Full Name

		if (randomUsername) {
			username = User.generateRandomUsername();
		}
		profileScreen.setUserName(username); // Set Username predefined in json file
		Thread.sleep(500);
		Boolean userAlreadyTaken = profileScreen.usernameTakenMessage(softAssert);

		while (userAlreadyTaken) {
			username = User.generateRandomUsername();
			profileScreen.setUserName(username); // Set aleatory Username
			Thread.sleep(500);
			userAlreadyTaken = profileScreen.usernameTakenMessage(softAssert);
		}

		if (setAvatar) {
			profileScreen.setAvatar(profileGender);
		}

		if (setAdditionalInfo) {
			profileScreen.setMoreInfo(aboutUser, websiteUrl);
		}

		profileScreen.continueToNextScreen();

		System.out.println("--- End of Profile Screen Test ---");

		// Connections Screen -------------------------
		System.out.println("--- Starting Connections Screen Test ---");
		ConnectionsScreen connectionsScreen = new ConnectionsScreen(driver);
		if (contactSyncTest) {
			// TODO: se debe incluir la posibilidad de enviar invitacion
		} else {
			connectionsScreen.continueWithoutSyncContacts();
		}

		softAssert.assertAll();

		// Save Login Data Data to Test SignIN
		WriteJSONToFile.save(countryCode, countryName, phoneNumber);

		Thread.sleep(500);
		System.out.println("--- End of Connections Screen Test ---");

		System.out.println("---- SignUp Test Finished ----");
	}

	@AfterMethod
	public void ScreenCaptureForFailures() {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("user.dir"));
	}

	@DataProvider(name = "userData")
	public static Iterator<Object[]> provideTestData() throws IOException {
		List<Object[]> testDataList = new ArrayList<>();

		// Specify the path to your JSON file
		String jsonFilePath = System.getProperty("user.dir")
				+ "\\src\\test\\java\\vaporstream\\Perzona\\testData\\PerzonaTestData_SignUp.json";

		// Use Json to parse the JSON file
		JsonElement jsonData = JsonParser.parseReader(new FileReader(jsonFilePath));
		JsonArray jsonArray = jsonData.getAsJsonArray();

		for (JsonElement element : jsonArray) {
			String countryCode = element.getAsJsonObject().get("countryCode").getAsString();
			String countryName = element.getAsJsonObject().get("countryName").getAsString();
			String phoneNumber = element.getAsJsonObject().get("phoneNumber").getAsString();
			String fullName = element.getAsJsonObject().get("fullName").getAsString();
			String username = element.getAsJsonObject().get("username").getAsString();
			String aboutUser = element.getAsJsonObject().get("aboutUser").getAsString();
			String websiteUrl = element.getAsJsonObject().get("websiteUrl").getAsString();
			String profileGender = element.getAsJsonObject().get("profileGender").getAsString();
			boolean randomPhoneNumber = element.getAsJsonObject().get("randomPhoneNumber").getAsBoolean();
			boolean invalidPhoneNumberTest = element.getAsJsonObject().get("invalidPhoneNumberTest").getAsBoolean();
			boolean editPhoneNumberTest = element.getAsJsonObject().get("editPhoneNumberTest").getAsBoolean();
			boolean wrongOTPTest = element.getAsJsonObject().get("wrongOTPTest").getAsBoolean();
			boolean delayedOTPTest = element.getAsJsonObject().get("delayedOTPTest").getAsBoolean();
			boolean randomUsername = element.getAsJsonObject().get("randomUsername").getAsBoolean();
			boolean setAvatar = element.getAsJsonObject().get("setAvatar").getAsBoolean();
			boolean setAdditionalInfo = element.getAsJsonObject().get("setAdditionalInfo").getAsBoolean();
			boolean contactSyncTest = element.getAsJsonObject().get("contactSyncTest").getAsBoolean();

			// Add the test data as an object array to the list
			testDataList.add(new Object[] { countryCode, countryName, phoneNumber, fullName, username, aboutUser, websiteUrl,
					profileGender, randomPhoneNumber, invalidPhoneNumberTest, editPhoneNumberTest, wrongOTPTest, delayedOTPTest,
					randomUsername, setAvatar, setAdditionalInfo, contactSyncTest });
		}

		return testDataList.iterator();
	}

	public class WriteJSONToFile {

		@SuppressWarnings("unchecked")
		public static void save(String countryCode, String countryName, String phoneNumber) {
			// Create a JSON object to hold your data
			JSONObject jsonObject = new JSONObject();

			// Add data to the JSON object
			jsonObject.put("countryCode", countryCode);
			jsonObject.put("countryName", countryName);
			jsonObject.put("phoneNumber", phoneNumber);

			// Create a JSON array to hold multiple objects if needed
			JSONArray jsonArray = new JSONArray();
			jsonArray.add(jsonObject);

			try {
				// Define the file path and name
				String jsonFilePath = System.getProperty("user.dir")
						+ "\\src\\test\\java\\vaporstream\\Perzona\\testData\\PerzonaTestData_SignIn.json";
				// Create a FileWriter
				FileWriter fileWriter = new FileWriter(jsonFilePath);
				// Write the JSON data to the file
				fileWriter.write(jsonArray.toJSONString());
				// Close the FileWriter
				fileWriter.close();

				System.out.println("Data has been written to " + jsonFilePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
