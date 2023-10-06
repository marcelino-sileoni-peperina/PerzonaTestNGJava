package vaporstream.Perzona.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ExternalServices {

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
				System.out.println("Error on Phone Number Resgistration - Response Code: " + responseCode);
			}

			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getOTP(String countryCode, String phoneNumber) throws InterruptedException {
		System.out.println("Asking for OTP");
		String otpCode = "";
		String apiUrl2 = "https://5eqr0uj731.execute-api.us-east-1.amazonaws.com/dev/k6/otp";
		String authorizationHeader = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEzZTk0MzY0LTMzZDMtNDM3OC1iNDFkLWQ4YmVhMTFiMTNiNCIsImlhdCI6MTY5MTQxNzY0NiwiZXhwIjoxNjk2Njc3MjQ2LCJpc3MiOiJcImh0dHBzOi8vdnMuaWRlbnRpdHkuY29tXCIifQ.cddSybV_pbTidUqPS5NvxtWKAJfsVAcAB042u2QLwQ1fbctOyUh5mdHL9VnDaFzGoUdbsM7ElEWm6rv3cwdHMaoXyV9BXjiY7SXLnKvcFn0FCBvEIwe8phLGWZbsccDlhwn5lfQiC56yrTfG-WtDhB7VQfqgG24HI6SohBZIA6bDNwLalw_Ux6_NKEMUikxZ1_HgJRq82NNNkFim9GUlLDUVZENZymar6b8PVx-SIDI5kRmTcuyV0iuVKMoUyXGr6GXgeQNa54Ol4ig_G_LD5SofXbXsTbux4QskAWRijXVRWVE4pigCDK0RHdjrRcnbp31FLHxG-L-htQeli87Yvg";

		// Create the JSON request payload
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("phoneNumber", "+" + countryCode + phoneNumber);
		// Convert the JSON payload to a string
		String jsonInputString = requestBody.toString();

		Integer intentos = 3;

		while (intentos > 0) {
			System.out.println("Getting OTP. Attempt " + (4 - intentos) + " de 3.");
			try {
				// Create the URL object and open a connection
				URL url = new URL(apiUrl2);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				Thread.sleep(2000);
				// Set up the HTTP request
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setRequestProperty("Accept", "application/json");
				connection.setRequestProperty("Authorization", authorizationHeader);
				connection.setDoOutput(true);
//				Thread.sleep(5000);

				// Write the JSON payload to the request body
				try (OutputStream os = connection.getOutputStream()) {
					byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
					os.write(input, 0, input.length);
				}
//				Thread.sleep(3000);

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

				intentos = 0;

				// Disconnect the connection
				connection.disconnect();
			} catch (Exception e) {
				System.out.println("Error getting OTP: " + e.getMessage());
				intentos--;
//				Thread.sleep(2000);

				e.printStackTrace();
			}

		}

		return otpCode;
	}

	public static String getToken(String countryCode, String phoneNumber, String code) throws InterruptedException {
		System.out.println("Asking for TOKEN");
		String token = "";
		String apiUrl = "https://api-personas-t.vaporstream.com/auth/login-refresh/phone";
//		String authorizationHeader = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEzZTk0MzY0LTMzZDMtNDM3OC1iNDFkLWQ4YmVhMTFiMTNiNCIsImlhdCI6MTY5MTQxNzY0NiwiZXhwIjoxNjk2Njc3MjQ2LCJpc3MiOiJcImh0dHBzOi8vdnMuaWRlbnRpdHkuY29tXCIifQ.cddSybV_pbTidUqPS5NvxtWKAJfsVAcAB042u2QLwQ1fbctOyUh5mdHL9VnDaFzGoUdbsM7ElEWm6rv3cwdHMaoXyV9BXjiY7SXLnKvcFn0FCBvEIwe8phLGWZbsccDlhwn5lfQiC56yrTfG-WtDhB7VQfqgG24HI6SohBZIA6bDNwLalw_Ux6_NKEMUikxZ1_HgJRq82NNNkFim9GUlLDUVZENZymar6b8PVx-SIDI5kRmTcuyV0iuVKMoUyXGr6GXgeQNa54Ol4ig_G_LD5SofXbXsTbux4QskAWRijXVRWVE4pigCDK0RHdjrRcnbp31FLHxG-L-htQeli87Yvg";

		// Create the JSON request payload
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("code", code);
		requestBody.addProperty("phoneNumber", "+" + countryCode + phoneNumber);
		// Convert the JSON payload to a string
		String jsonInputString = requestBody.toString();

		Integer intentos = 3;

		while (intentos > 0) {
			System.out.println("Getting TOKEN. Attempt " + (4 - intentos) + " de 3.");
			try {
				// Create the URL object and open a connection
				URL url = new URL(apiUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				Thread.sleep(2000);
				// Set up the HTTP request
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setRequestProperty("Accept", "application/json");
//				connection.setRequestProperty("Authorization", authorizationHeader);
				connection.setDoOutput(true);
//				Thread.sleep(5000);

				// Write the JSON payload to the request body
				try (OutputStream os = connection.getOutputStream()) {
					byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
					os.write(input, 0, input.length);
				}
//				Thread.sleep(3000);

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
					token = jsonObject.get("token").getAsString();

					// Print the OTP code
					System.out.println("  TOKEN Obtained: " + token);
				} else {
					System.out.println("  Error Getting TOKEN - Response Code: " + responseCode);
				}

				intentos = 0;

				// Disconnect the connection
				connection.disconnect();
			} catch (Exception e) {
				System.out.println("  Error getting TOKEN: " + e.getMessage());
				intentos--;
//				Thread.sleep(2000);

				e.printStackTrace();
			}
		}
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
			String authorizationHeader = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImFiOTU3YjVhLTc1MTAtNDRhZi04YWZhLWZiZTM4YTc4NDEzYyIsImlhdCI6MTY5NDA5MjU1MiwiZXhwIjozMzg4MTg4NzA0LCJpc3MiOiJcImh0dHBzOi8vdnMuaWRlbnRpdHkuY29tXCIifQ.K_ahYyTQh5pSBmiV6QNlZMht16PdG1BtiY6f68fVgXDlYGWVxfP4u1hpBp392dLeA7PYyOa-q8HuNc5RFbdCh96L4HGnWThVp1ct_aD3xcHfQJyLnxd5fDdWzSCkIhJjaXBqUoRP-I8tHgMfDtgGtc3cYB4LB8Yl7xv1UJxjrqxGMCoSPrfE1mUSQwoQ8MzlPFKs3c5dw4SVnHDk2G29I2sl7CdwrXLiYRUjkw5oEmYhXMht3rQIUu_nW0lz6u-ZmfcV4co0b71kYpz3kTShNGBU-kp3OT98umQnScU62cjNcX5g5xzjlC8I9PeHQ5RwSQhvvLJwhh3ZvWvIylVSyA";
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

	public static boolean validatePreviousSignUp(String countryCode, String phoneNumber) throws InterruptedException {
		newOTP(countryCode,phoneNumber);
		String otp = getOTP(countryCode,phoneNumber);
		String jwt = getToken(countryCode,  phoneNumber, otp);
		
		boolean validate = true;
		try {
			// URL for the GET request
			String url = "https://5eqr0uj731.execute-api.us-east-1.amazonaws.com/dev/k6/phonenumber/+" + countryCode
					+ phoneNumber;
			System.out.println("URL: " + url);

			// Create a URL object
			URL obj = new URL(url);

			// Open a connection to the URL
			HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

			// Set the HTTP request method to GET
			connection.setRequestMethod("GET");

			// Set request headers
//			String AuthorizationHeader = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImIzMTllZWNjLTM3ZmYtNDg4NS04OGE1LWFhZDk2NWFlYWJiYSIsImlhdCI6MTY5NDQ0NzgxOSwiZXhwIjoyMjMyMzQ1NTYzOCwiaXNzIjoiXCJodHRwczovL3ZzLmlkZW50aXR5LmNvbVwiIn0.edVKUwj1qi4VkbNY3A4-XZlO6HPz1Mec8pZi0xqQsMuGJBtgLqROPJmIri-Ke_Gv-KTtSIMnpCIG4SyYhHEHkhWQjsvtve8U8z6p2ufeMGNv9hnSpqHV4MdKCn-PY_hljkxxa-5uu7k3yL7CU1ByYUFjTBfhL9s9e4X9PqJyEUY594E-4gBRxelKmPGKJzHR6019HyBS4q02q6chqeFYMI4PnNbcU5qq8AHqn_AFRgdnB_L7M2dcYpfdk8a1GYoXrQ2_8ZyO7EvfuxsDsf5g6rEKTwn9fWUJXKmq_QFwYIIASh6Bx86FlzXacgWIX02a4LzTmB4z8CIUSQwq1HNSGg";
			String AuthorizationHeader = jwt;
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
				JSONObject jsonObject = new JSONObject(jsonResponse);
				String token = jsonObject.get("token").toString();
				System.out.println("Token: " + token);
				if (token != null) {
					validate = true;
				}
			}

			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return validate;
	}

}
