package vaporstream.Perzona.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class OTPGenerator {

	public static void newOTP(String countryCode, String phoneNumber) throws InterruptedException {

//		String otpCode = "";
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

//		Thread.sleep(1000);
//
//		String apiUrl2 = "https://5eqr0uj731.execute-api.us-east-1.amazonaws.com/dev/k6/otp";
//		String authorizationHeader = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEzZTk0MzY0LTMzZDMtNDM3OC1iNDFkLWQ4YmVhMTFiMTNiNCIsImlhdCI6MTY5MTQxNzY0NiwiZXhwIjoxNjk2Njc3MjQ2LCJpc3MiOiJcImh0dHBzOi8vdnMuaWRlbnRpdHkuY29tXCIifQ.cddSybV_pbTidUqPS5NvxtWKAJfsVAcAB042u2QLwQ1fbctOyUh5mdHL9VnDaFzGoUdbsM7ElEWm6rv3cwdHMaoXyV9BXjiY7SXLnKvcFn0FCBvEIwe8phLGWZbsccDlhwn5lfQiC56yrTfG-WtDhB7VQfqgG24HI6SohBZIA6bDNwLalw_Ux6_NKEMUikxZ1_HgJRq82NNNkFim9GUlLDUVZENZymar6b8PVx-SIDI5kRmTcuyV0iuVKMoUyXGr6GXgeQNa54Ol4ig_G_LD5SofXbXsTbux4QskAWRijXVRWVE4pigCDK0RHdjrRcnbp31FLHxG-L-htQeli87Yvg";
//
//		try {
//			URL url = new URL(apiUrl2);
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setRequestMethod("POST");
//			connection.setRequestProperty("Content-Type", "application/json");
//			connection.setRequestProperty("Accept", "application/json");
//			connection.setRequestProperty("Authorization", authorizationHeader);
//			connection.setDoOutput(true);
//
//			try (OutputStream os = connection.getOutputStream()) {
//				byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//				os.write(input, 0, input.length);
//			}
//
//			int responseCode = connection.getResponseCode();
//			if (responseCode == HttpURLConnection.HTTP_OK) {
////				System.out.println("OTP Obtained");
//			} else {
//				System.out.println("Error Getting OTP from OTP service - Response Code: " + responseCode);
//			}
//
//
//			if (responseCode == HttpURLConnection.HTTP_OK) {
//				InputStream responseStream = connection.getInputStream();
//				BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
//				StringBuilder responseBuilder = new StringBuilder();
//				String line;
//				while ((line = reader.readLine()) != null) {
//					responseBuilder.append(line);
//				}
//				reader.close();
//
//				String jsonResponse = responseBuilder.toString();
//				JSONObject jsonObject = new JSONObject(jsonResponse);
//
//				otpCode = jsonObject.get("code").toString();
//				System.out.println("OTP Code Obtained: " + otpCode);
//			}
//
//			connection.disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return otpCode;
	}

	
	public static String getOTP(String countryCode, String phoneNumber) throws InterruptedException {
		System.out.println("Asking for OTP");
		String otpCode = "";
//		String apiUrl1 = "https://api-personas-t.vaporstream.com/auth/login-tickets/phone";
		String jsonInputString = "{\"phoneNumber\": \"+" + countryCode + phoneNumber + "\"}";
//
//		try {
//			URL url = new URL(apiUrl1);
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setRequestMethod("POST");
//			connection.setRequestProperty("content-type", "application/json");
//			connection.setDoOutput(true);
//
//			try (OutputStream os = connection.getOutputStream()) {
//				byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//				os.write(input, 0, input.length);
//			}
//
//			int responseCode = connection.getResponseCode();
//			if (responseCode == 204) {
////				System.out.println("Phone Number Registered - OTP Generated");
//			} else {
//				System.out.println("Error on Phone Number Resgistration - Response Code: " + responseCode);
//			}
//
//			connection.disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		Thread.sleep(1000);

		String apiUrl2 = "https://5eqr0uj731.execute-api.us-east-1.amazonaws.com/dev/k6/otp";
		String authorizationHeader = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEzZTk0MzY0LTMzZDMtNDM3OC1iNDFkLWQ4YmVhMTFiMTNiNCIsImlhdCI6MTY5MTQxNzY0NiwiZXhwIjoxNjk2Njc3MjQ2LCJpc3MiOiJcImh0dHBzOi8vdnMuaWRlbnRpdHkuY29tXCIifQ.cddSybV_pbTidUqPS5NvxtWKAJfsVAcAB042u2QLwQ1fbctOyUh5mdHL9VnDaFzGoUdbsM7ElEWm6rv3cwdHMaoXyV9BXjiY7SXLnKvcFn0FCBvEIwe8phLGWZbsccDlhwn5lfQiC56yrTfG-WtDhB7VQfqgG24HI6SohBZIA6bDNwLalw_Ux6_NKEMUikxZ1_HgJRq82NNNkFim9GUlLDUVZENZymar6b8PVx-SIDI5kRmTcuyV0iuVKMoUyXGr6GXgeQNa54Ol4ig_G_LD5SofXbXsTbux4QskAWRijXVRWVE4pigCDK0RHdjrRcnbp31FLHxG-L-htQeli87Yvg";

		try {
			URL url = new URL(apiUrl2);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Authorization", authorizationHeader);
			connection.setDoOutput(true);

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("OTP Obtained");
			} else {
				System.out.println("Error Getting OTP from OTP service - Response Code: " + responseCode);
			}


			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream responseStream = connection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
				StringBuilder responseBuilder = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					responseBuilder.append(line);
				}
				reader.close();

				String jsonResponse = responseBuilder.toString();
				JSONObject jsonObject = new JSONObject(jsonResponse);

				otpCode = jsonObject.get("code").toString();
				System.out.println("OTP Code Obtained: " + otpCode);
			}

			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return otpCode;
	}

}
