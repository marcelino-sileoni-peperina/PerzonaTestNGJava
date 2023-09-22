package vaporstream.Perzona.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class PhoneValidator {

	// this method return true if the passed phone number is valid as per the region specified
	public static boolean isPhoneNumberValid(String phone) {
		// creating an instance of PhoneNumber Utility class
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

		// creating a variable of type PhoneNumber
		PhoneNumber phoneNumber = null;

		try {
			// the parse method parses the string and returns a PhoneNumber in the format of specified region
			phoneNumber = phoneUtil.parse(phone, "IN");
		} catch (NumberParseException e) {

			// if the phoneUtil is unable to parse any phone number an exception occurs and
			// gets caught in this block
			System.out.println("Unable to parse the given phone number: " + phone);
			e.printStackTrace();
		}

		boolean isValidNumber = phoneUtil.isValidNumber(phoneNumber);
		if (isValidNumber) {
			System.out.println("Valid Phone Number Type: " + phoneUtil.getNumberType(phoneNumber));
		}

		// return the boolean value of the validation performed
		return isValidNumber;
	}
}
