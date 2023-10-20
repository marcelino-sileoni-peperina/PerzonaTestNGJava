package vaporstream.Perzona.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Random;

public class ExternalServices {
  private static String JWT;
  
  static {
    Properties prop = new Properties();
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(
              System.getProperty("user.dir") + "/src/main/java/vaporstream/Perzona/resources/extServ.properties");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    try {
      prop.load(fis);
      fis.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    JWT = prop.getProperty("jwtToken");
    System.out.println("JWT length obtained: " + JWT.length());
  }
  
  public static void newOTP(String countryCode, String phoneNumber) throws InterruptedException {
    
    String apiUrl1 = "https://api-personas-t.vaporstream.com/auth/login-tickets/phone";
    String jsonInputString = "{\"phoneNumber\": \"+" + countryCode + phoneNumber + "\"}";
    
    try {
      URL url = new URL(apiUrl1);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("content-type", "application/json");
      connection.setDoOutput(true);
      
      try (OutputStream os = connection.getOutputStream()) {
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
      }
      
      int responseCode = connection.getResponseCode();
      if (responseCode == 204) {
        System.out.println("Phone Number Registered - OTP Generated");
      } else {
        System.out.println("Error on Phone Number Registration - Response Code: " + responseCode);
      }
      
      connection.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static String getOTP(String countryCode, String phoneNumber) throws Exception {
/*    String JWT = "";

    Properties props = new Properties();
    FileInputStream fis = new FileInputStream(
            System.getProperty("user.dir") + "/src/main/java/vaporstream/Perzona/resources/extServ.properties");
    props.load(fis);
    JWT = props.getProperty("jwtToken");
    System.out.println("JWT obtained: " + JWT);
    fis.close();*/
    
    System.out.println("Asking for OTP");
    String otpCode = "";
    String apiUrl2 = "https://5eqr0uj731.execute-api.us-east-1.amazonaws.com/dev/k6/otp";
    String authorizationHeader = JWT;
    
    // Create the JSON request payload
    JsonObject requestBody = new JsonObject();
    requestBody.addProperty("phoneNumber", "+" + countryCode + phoneNumber);
    // Convert the JSON payload to a string
    String jsonInputString = requestBody.toString();

//		Integer intentos = 3;
//
//		while (intentos > 0) {
//			System.out.println("Getting OTP. Attempt " + (4 - intentos) + " de 3.");
    try {
      // Create the URL object and open a connection
      URL url = new URL(apiUrl2);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      Thread.sleep(1000);
      // Set up the HTTP request
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("Accept", "application/json");
      connection.setRequestProperty("Authorization", authorizationHeader);
      connection.setDoOutput(true);
      
      // Write the JSON payload to the request body
      try (OutputStream os = connection.getOutputStream()) {
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
      }
      
      // Get the HTTP response code
      int responseCode = connection.getResponseCode();
//				Thread.sleep(5000);
      
      if (responseCode == HttpURLConnection.HTTP_OK) {
//					System.out.println("HTTP Response Code Obtained: " + responseCode);
        // Read the JSON response from the input stream
        InputStream responseStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
          responseBuilder.append(line);
        }
        // Parse the JSON response
        String jsonResponse = responseBuilder.toString();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        // Extract the value of the "code" key
        otpCode = jsonObject.get("code").getAsString();
        // Print the OTP code
        System.out.println("OTP Code Obtained: " + otpCode);
      } else {
        System.out.println("Error Getting OTP from OTP service - Response Code: " + responseCode);
      }
//				intentos = 0;
      // Disconnect the connection
      connection.disconnect();
    } catch (Exception e) {
      System.out.println("Error getting OTP: " + e.getMessage());
//				intentos--;
      e.printStackTrace();
    }

//		}
    
    return otpCode;
  }
  
  // 09/10/2023 getToken its not been used. New Timeout: 1 year
  public static String getToken(String countryCode, String phoneNumber, String code) throws InterruptedException {
    System.out.println("Asking for JASON WEB TOKEN");
    String token = "";
    String apiUrl = "https://api-personas-t.vaporstream.com/auth/login-refresh/phone";
    
    // Create the JSON request payload
    JsonObject requestBody = new JsonObject();
    requestBody.addProperty("code", code);
    requestBody.addProperty("phoneNumber", "+" + countryCode + phoneNumber);
    // Convert the JSON payload to a string
    String jsonInputString = requestBody.toString();
    
    try {
      // Create the URL object and open a connection
      URL url = new URL(apiUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      Thread.sleep(2000);
      // Set up the HTTP request
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setRequestProperty("Accept", "application/json");
      connection.setDoOutput(true);
      // Write the JSON payload to the request body
      try (OutputStream os = connection.getOutputStream()) {
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
      }
      // Get the HTTP response code
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
//					System.out.println("HTTP Response Code Obtained: " + responseCode);
        // Read the JSON response from the input stream
        InputStream responseStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          responseBuilder.append(line);
        }
        // Parse the JSON response
        String jsonResponse = responseBuilder.toString();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        // Extract the value of the "code" key
        token = jsonObject.get("token").getAsString();
        // Print the OTP code
        System.out.println("  TOKEN Obtained: " + token);
      } else {
        System.out.println("  Error Getting TOKEN - Response Code: " + responseCode);
      }
      
      // Disconnect the connection
      connection.disconnect();
    } catch (Exception e) {
      System.out.println("  Error getting TOKEN: " + e.getMessage());
      
      e.printStackTrace();
    }
//		}
    return token;
  }
  
  public static String invalidOTP(String validOTPCode) throws InterruptedException {
    System.out.println("Asking for Invalid OTP");
    String invalidOTPCode = validOTPCode;
    char[] charArray = validOTPCode.toCharArray();
    if (charArray.length > 0) {
      while (invalidOTPCode == validOTPCode) {
        char randomChar;
        for (int i = 0; i < 6; i++) {
          randomChar = generateRandomChar();
          charArray[i] = randomChar;
        }
        invalidOTPCode = new String(charArray);
      }
      System.out.println("Valid OTP: " + validOTPCode);
      System.out.println("Invalid OTP: " + invalidOTPCode);
    } else {
      System.out.println("Input string is empty.");
    }
    return invalidOTPCode;
  }
  
  // Auxiliary Function to generate a random character
  private static char generateRandomChar() {
    Random random = new Random();
    // ASCII values for printable characters (excluding control characters and
    // whitespace)
    int lowerBound = 48; // '0' character
    int upperBound = 57; // '9' character
    int randomAsciiValue = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
    return (char) randomAsciiValue;
  }
  
  public static void deletePhoneNumberFromDB(String countryCode, String phoneNumber) throws IOException {

    try {
      // Set the request URL
      String url = "https://5eqr0uj731.execute-api.us-east-1.amazonaws.com/dev/k6/phonenumber/+" + countryCode
              + phoneNumber;
      
      // Create a new HttpURLConnection object
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      
      // Set the request method to DELETE
      connection.setRequestMethod("DELETE");
      
      // Set the request headers
      String authorizationHeader = JWT;
      connection.setRequestProperty("Authorization", authorizationHeader);
      connection.setRequestProperty("Accept", "application/json");
      connection.setRequestProperty("Content-Type", "application/json");
      
      // Connect to the server
      connection.connect();
      
      // Get the response code
      int responseCode = connection.getResponseCode();
      
      // Check the response code
      if (responseCode == 200) {
        // The request was successful
        System.out.println(
                "In order to be sure we are testing a Sign Up Case we must delete the phone number \nfrom the previously registered numbers in the DB.");
        System.out.println("The phone number +" + countryCode + phoneNumber + " was successfully deleted in the DB.");
        
      } else {
        // The request failed
        System.out.println(
                "Can't delete phone number +" + countryCode + phoneNumber + " in DB. Response code: " + responseCode);
      }
      
      // Disconnect from the server
      connection.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static boolean validatePreviousSignUp(String countryCode, String phoneNumber) throws Exception {
    
    System.out.println("Verifying previous Sign Up");
    
    boolean validate = false;
    try {
      // URL for the GET request
      String url = "https://5eqr0uj731.execute-api.us-east-1.amazonaws.com/dev/get-profiles/+" + countryCode
              + phoneNumber;
      
      // Create a URL object
      URL obj = new URL(url);
      // Open a connection to the URL
      HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
      // Set the HTTP request method to GET
      connection.setRequestMethod("GET");
      // Set request headers
      String AuthorizationHeader = JWT;
      connection.setRequestProperty("Authorization", AuthorizationHeader);
      connection.setRequestProperty("Accept", "application/json");
      connection.setRequestProperty("Content-Type", "application/json");
      // Get the response code
      int responseCode = connection.getResponseCode();
      System.out.println("Response Code: " + responseCode);
      
      if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
        System.out.println("Phone Number NOT Registered");
        validate = false;
      }
      // Read the response
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        String jsonResponse = response.toString();
        JSONArray jsonArray = new JSONArray(jsonResponse);
        if (!jsonArray.isEmpty()) { // If the response has no elements means the phone number is not registered
          validate = true;
        }
      }
      connection.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Previous Sign Up: " + validate);
    return validate;
  }
  
}
