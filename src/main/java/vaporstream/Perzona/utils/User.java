package vaporstream.Perzona.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.json.JSONObject;

public class User {

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
				System.out.println(" In order to be sure we are testing a Sign Up Case we must delete the phone number \nfrom the previously registered numbers in the DB.");
				System.out.println(" The phone number +" + countryCode + phoneNumber + " was successfully deleted in the DB.");

			} else {
				// The request failed
				System.out.println(
						" Can't delete phone number +" + countryCode + phoneNumber + " in DB. Response code: " + responseCode);
			}

			// Disconnect from the server
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean validatePreviousSignUp(String countryCode, String phoneNumber) throws InterruptedException {
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
			String AuthorizationHeader = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImIzMTllZWNjLTM3ZmYtNDg4NS04OGE1LWFhZDk2NWFlYWJiYSIsImlhdCI6MTY5NDQ0NzgxOSwiZXhwIjoyMjMyMzQ1NTYzOCwiaXNzIjoiXCJodHRwczovL3ZzLmlkZW50aXR5LmNvbVwiIn0.edVKUwj1qi4VkbNY3A4-XZlO6HPz1Mec8pZi0xqQsMuGJBtgLqROPJmIri-Ke_Gv-KTtSIMnpCIG4SyYhHEHkhWQjsvtve8U8z6p2ufeMGNv9hnSpqHV4MdKCn-PY_hljkxxa-5uu7k3yL7CU1ByYUFjTBfhL9s9e4X9PqJyEUY594E-4gBRxelKmPGKJzHR6019HyBS4q02q6chqeFYMI4PnNbcU5qq8AHqn_AFRgdnB_L7M2dcYpfdk8a1GYoXrQ2_8ZyO7EvfuxsDsf5g6rEKTwn9fWUJXKmq_QFwYIIASh6Bx86FlzXacgWIX02a4LzTmB4z8CIUSQwq1HNSGg";
			connection.setRequestProperty("Authorization", AuthorizationHeader);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");

			// Get the response code
			int responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
				System.out.println(" Phone Number NOT Registered");
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

	public static String generateRandomUsername() {
		// Define the character set from which to generate the username
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		// Create a StringBuilder to store the generated username
		StringBuilder username = new StringBuilder();

		// Create a Random object for generating random indices
		Random random = new Random();

		// Generate a random username of ten characters
		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(characters.length());
			char randomChar = characters.charAt(index);
			username.append(randomChar);
		}
		String usernameGenerated = "UN" + username.toString();
		System.out.println(" Username ramdomly generated: " + usernameGenerated);
		return (usernameGenerated);
	}
}
