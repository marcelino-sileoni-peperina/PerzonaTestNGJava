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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Test01_SignUp extends AndroidTestBase {

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
		Activity firstPage = new Activity("vaporstream.perzonas_tst", "vaporstream.perzonas_tst.MainActivity");
//		driver.startActivity(firstPage);
	}

	@Test(dataProvider = "userData")
	public void SignUpTest(String countryCode, String countryName, String fullName, String username, String aboutUser,
			String websiteUrl) throws MalformedURLException, InterruptedException {
		System.out.println("SignUp Test Initiated ----------------------");
		// Phone Number Generation
		String phoneNumber = PhoneNumberGenerator.getNewPhoneNumber(countryCode);
		// GET OTP CODE
		String otpCode = OTPGenerator.getOTP(countryCode, phoneNumber);

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

		Thread.sleep(3000);

		// Your Information Screen
		YourInformationScreen yourInformationScreen = new YourInformationScreen(driver);
		yourInformationScreen.setAvatar();
		Thread.sleep(300);
		yourInformationScreen.setFullName(fullName);
		// Sobrescribimos el username definido en el json hasta que no se genere uno
		// aleatorio porque se resolvio la posibilidad de dar de baja una linea y
		// username.
		username = "u" + countryCode + phoneNumber;
		yourInformationScreen.setMoreInfo(aboutUser, websiteUrl, username);

		Thread.sleep(1000);

		// Connections Screen
		ConnectionsScreen connectionsScreen = new ConnectionsScreen(driver);
		connectionsScreen.continueWithoutSyncContacts();

		// Save Login Data Data to Test SignIN
		WriteJSONToFile.save(countryCode, countryName, phoneNumber);
		
		Thread.sleep(5000);
		System.out.println("SignUp Test Finished ----------------------");
	}

	// JSON DATA PROVIDER - Toma todos los elementos del JSON con los parametros de prueba

	public class JsonDataProvider {

		public static Iterator<Object[]> provideTestData() throws IOException {
			List<Object[]> testDataList = new ArrayList<>();

			// Specify the path to your JSON file
			String jsonFilePath = System.getProperty("user.dir")
					+ "\\src\\test\\java\\vaporstream\\Perzona\\testData\\PerzonaTestData_SignUp.json";

			// Use Gson to parse the JSON file
			JsonElement jsonData = JsonParser.parseReader(new FileReader(jsonFilePath));
			JsonArray jsonArray = jsonData.getAsJsonArray();

			for (JsonElement element : jsonArray) {
				String countryCode = element.getAsJsonObject().get("countryCode").getAsString();
				String countryName = element.getAsJsonObject().get("countryName").getAsString();
				String fullName = element.getAsJsonObject().get("fullName").getAsString();
				String username = element.getAsJsonObject().get("username").getAsString();
				String aboutUser = element.getAsJsonObject().get("aboutUser").getAsString();
				String websiteUrl = element.getAsJsonObject().get("websiteUrl").getAsString();

				// Add the test data as an object array to the list
				testDataList.add(new Object[] { countryCode, countryName, fullName, username, aboutUser, websiteUrl });
			}

			return testDataList.iterator();
		}
	}

	@DataProvider(name = "userData")
	public Iterator<Object[]> provideTestData() throws IOException {
		return JsonDataProvider.provideTestData();

	}


	public class WriteJSONToFile{

		@SuppressWarnings("unchecked")
		public static void save(
				String countryCode, String countryName, String phoneNumber) {
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
//				String filePath = "your_file_path.json";
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
