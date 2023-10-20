package vaporstream.Perzona.testUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataSupplier {
  @DataProvider(name = "signUpData")
  public static Iterator<Object[]> provideSignUpTestData() throws IOException {
    
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
      testDataList.add(new Object[]{
              countryCode,
              countryName,
              phoneNumber,
              fullName,
              username,
              aboutUser,
              websiteUrl,
              profileGender,
              randomPhoneNumber,
              invalidPhoneNumberTest,
              editPhoneNumberTest,
              wrongOTPTest,
              delayedOTPTest,
              randomUsername,
              setAvatar,
              setAdditionalInfo,
              contactSyncTest});
    }
    
    return testDataList.iterator();
  }
  
  @DataProvider(name = "signInData")
  public static Iterator<Object[]> provideSignInTestData() throws IOException {
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
      testDataList.add(new Object[]{countryCode, countryName, phoneNumber, invalidPhoneNumberTest,
              editPhoneNumberTest, wrongOTPTest, delayedOTPTest});
    }
    
    return testDataList.iterator();
  }
  
}
