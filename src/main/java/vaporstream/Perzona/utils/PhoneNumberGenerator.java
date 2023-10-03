package vaporstream.Perzona.utils;

import java.util.Random;

public class PhoneNumberGenerator {

	public static String getNewPhoneNumber(String countryCode, boolean wantsValid) {

		int minRange = 00000;
		int maxRange = 77999;
		int randomNumber1 = 0;
		int randomNumber2 = 0;
		boolean satisfied = false;
		String phoneNumber = "";

		while (!satisfied) {

			Random random = new Random();
			randomNumber1 = random.nextInt(maxRange - minRange + 1) + minRange;
			randomNumber2 = random.nextInt(maxRange - minRange + 1) + minRange;

			phoneNumber = String.valueOf(randomNumber1) + String.valueOf(randomNumber2);
			String completePhoneNumber = "+" + countryCode + phoneNumber;

			// Phone Number Validation
			boolean isValidPhoneNumber = PhoneValidator.isPhoneNumberValid(completePhoneNumber);
			satisfied = (isValidPhoneNumber && wantsValid) || (!isValidPhoneNumber && !wantsValid);

			if (satisfied) {
				System.out.println(
						(isValidPhoneNumber == true ? "  V" : "  Inv") + "alid Phone Number Generated: " + completePhoneNumber);
			}
		}

		return phoneNumber;
	}
	
	public static String getPermutedPhoneNumber(String phoneNumber) {

		if (phoneNumber.length()<2) {
			return phoneNumber;
		} else {
			char[] charArray = phoneNumber.toCharArray();
      char temp = charArray[phoneNumber.length()-1];
      charArray[phoneNumber.length()-1] = charArray[phoneNumber.length()-2];
      charArray[phoneNumber.length()-2] = temp;
      return new String(charArray);
		}

	}

}
