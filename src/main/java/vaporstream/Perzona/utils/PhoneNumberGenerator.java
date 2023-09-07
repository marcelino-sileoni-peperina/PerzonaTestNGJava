package vaporstream.Perzona.utils;

import java.util.Random;

public class PhoneNumberGenerator {

	public static String getNewPhoneNumber(String countryCode) {

    int minRange = 00000;
    int maxRange = 77999;
    int randomNumber1 = 0;
    int randomNumber2 = 0;
    boolean isValidPhoneNumber = false;
    String phoneNumber = "";
    
    while (!isValidPhoneNumber) {
    	
			Random random = new Random();
			randomNumber1 = random.nextInt(maxRange - minRange + 1) + minRange;
			randomNumber2 = random.nextInt(maxRange - minRange + 1) + minRange;
			
			phoneNumber = String.valueOf(randomNumber1) + String.valueOf(randomNumber2);
			String completePhoneNumber = "+" + countryCode + phoneNumber;
//			System.out.print("Phone Number: " + completePhoneNumber);

			// Phone Number Validation

			isValidPhoneNumber = PhoneValidator.isPhoneNumberValid(completePhoneNumber);
//			System.out.println("Valid Phone Number: " + isValidPhoneNumber);
			
			if (isValidPhoneNumber) {
				System.out.println("Phone Number: " + completePhoneNumber);
			}
    }
		
		return phoneNumber;
	}
	

}
