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

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.appium.java_client.android.Activity;
import vaporstream.Perzona.pageObjects.android.*;
import vaporstream.Perzona.testUtils.AndroidTestBase;
import vaporstream.Perzona.utils.*;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Test02_SignIn extends AndroidTestBase {

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
		driver.resetApp();
//		Activity firstPage = new Activity("vaporstream.perzonas_tst", "vaporstream.perzonas_tst.MainActivity");
//		driver.startActivity(firstPage);
	}

	@Test(dataProvider = "userData", testName = "Sign-In Test")
	public void SignInTest(String phoneNumber, String countryCode, String countryName, boolean invalidPhoneNumberTest,
			boolean editPhoneNumberTest, boolean wrongOTPTest, boolean delayedOTPTest)
			throws MalformedURLException, InterruptedException {

		System.out.println("\n---- SignIn Test Started ----");
		System.out.println("Phone Number: +" + countryCode + phoneNumber);
		System.out.println("Testing Paramaters:");
		System.out.println("\tCountry Code: " + countryCode);
		System.out.println("\tCountry Name: " + countryName);
		System.out.println("\tPhone Number: " + phoneNumber);
		System.out.println("\tTest Invalid Phone Number: " + (invalidPhoneNumberTest == true ? "Yes" : "No"));
		System.out.println("\tTest Phone Number Edition: " + (editPhoneNumberTest == true ? "Yes" : "No"));
		System.out.println("\tTest Invalid OTP: " + (wrongOTPTest == true ? "Yes" : "No"));
		System.out.println("\tTest OTP timeout: " + (delayedOTPTest == true ? "Yes" : "No"));
		System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

		// Validate Pre-existing User
		System.out.println("\nValidating Previous SignUp of Phone Number Provided.");
//		boolean signedUp = false;
//		signedUp = ExternalServices.validatePreviousSignUp(countryCode, phoneNumber);
		boolean signedUp = true; // Puesto porque no funciona la verificacion de SignUp porque no se puede
															// obtener el token
		if (signedUp) {
			SoftAssert softAssert = new SoftAssert();
			// On Boarding Screen
			System.out.println(">---- Starting SignUp Test ----");
			System.out.println("Pressing Get Started");
			OnBoardingScreen onBoardingScreen = new OnBoardingScreen(driver);
			onBoardingScreen.getStartedClick();

			// Sign up Screen
			SignUpScreen signUpScreen = new SignUpScreen(driver);
			signUpScreen.setCountrySelection(countryName, countryCode);

			if (invalidPhoneNumberTest) {
				System.out.println(">-- Starting Invalid Phone Number Test ---");
				String invalidPhoneNumber = PhoneNumberGenerator.getNewPhoneNumber(countryCode, false);
				signUpScreen.setPhoneNumber(invalidPhoneNumber);
				signUpScreen.verifyInvalidPhoneMessage(softAssert);
				System.out.println("<--- End of Invalid Phone Number Test ---");
			} else {
				System.out.println(">--- Sign Up with Given Phone Number Test ---");
			}

			if (editPhoneNumberTest) {
				System.out.println(">>--- Starting Phone Number Edition Test ---");
				String permutedPhoneNuber = PhoneNumberGenerator.getPermutedPhoneNumber(phoneNumber);
				signUpScreen.setPhoneNumber(permutedPhoneNuber);
				Thread.sleep(500);
				signUpScreen.continueToVerifyPhoneNumber();
				Thread.sleep(500);
				signUpScreen.editPhoneNumber();
				Thread.sleep(500);
				System.out.println("<<--- Ending Phone Number Edition Test ---");
			}

			signUpScreen.setPhoneNumber(phoneNumber);
			Thread.sleep(500);
			signUpScreen.continueToVerifyPhoneNumber();
			// Go to OTP
			Thread.sleep(1000); // SIN ESTA PAUSA NO FUNCIONA !!
			signUpScreen.continueToOTP();

			System.out.println(">--- Start of OTP Test ---");
			// Get OTP Code
			String otpCode = ExternalServices.getOTP(countryCode, phoneNumber);

			// Verify Screen (OTP Validation)
			VerifyScreen verifyScreen = new VerifyScreen(driver);
			// Wrong OTP TEST
			if (wrongOTPTest) {
				System.out.println(">>--- Starting Invalid OTP Test ---");
				String invalidOtpCode = ExternalServices.invalidOTP(otpCode);
				verifyScreen.setCodeField(invalidOtpCode);
				verifyScreen.clickOK();
				System.out.println("<<--- End of Invalid OTP Test ---");
			}
			// Delayed OTP TEST
			if (delayedOTPTest) {
				System.out.println(">>--- Starting Timeout OTP Test ---");
				Thread.sleep(55000); // ESTA SALTANDO POR TIMEOUT - REVISAR !!!
//				verifyScreen.setCodeField(otpCode); // ESTE DEBERIA FALLAR PERO AL 19/09/23 NO FALLA PORQUE LO HAN CONFIGURADO ASI EN LA APP
//				verifyScreen.clickOK(); // SE COMENTA HASTA QUE SE RESUELVA LO COMENTADO ARRIBA
				verifyScreen.requestNewOTP();
				Thread.sleep(2000);
				otpCode = ExternalServices.getOTP(countryCode, phoneNumber);
				System.out.println("<<--- End of Timeout OTP Test ---");
			}
			// Normal OTP Test
			verifyScreen.setCodeField(otpCode);
			System.out.println("<--- End of OTP Test ---");
			System.out.println("\n--- SignIn Test Finished Succesfully ---");
		} else {
			System.out.println("\n--- Not possible to execute SignIn Test. User never SignedUp ---");
			Assert.assertTrue(signedUp, "Not possible to execute SignIn Test. User never SignedUp");
		}
	}

// --------------------------------------------------------------------

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
			boolean invalidPhoneNumberTest = element.getAsJsonObject().get("invalidPhoneNumberTest").getAsBoolean();
			boolean editPhoneNumberTest = element.getAsJsonObject().get("editPhoneNumberTest").getAsBoolean();
			boolean wrongOTPTest = element.getAsJsonObject().get("wrongOTPTest").getAsBoolean();
			boolean delayedOTPTest = element.getAsJsonObject().get("delayedOTPTest").getAsBoolean();

			// Add the test data as an object array to the list
			testDataList.add(new Object[] { phoneNumber, countryCode, countryName, invalidPhoneNumberTest,
					editPhoneNumberTest, wrongOTPTest, delayedOTPTest });
		}

		return testDataList.iterator();
	}

}
