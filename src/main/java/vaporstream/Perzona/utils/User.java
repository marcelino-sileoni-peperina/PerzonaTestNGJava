package vaporstream.Perzona.utils;

import java.util.Random;

public class User {

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
		System.out.println("Username ramdomly generated: " + usernameGenerated);
		return (usernameGenerated);
	}
}
