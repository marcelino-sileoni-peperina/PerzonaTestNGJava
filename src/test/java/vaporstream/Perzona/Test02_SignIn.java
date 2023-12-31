package vaporstream.Perzona;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import vaporstream.Perzona.pageObjects.android.OnBoardingScreen;
import vaporstream.Perzona.pageObjects.android.SignUpScreen;
import vaporstream.Perzona.pageObjects.android.VerifyScreen;
import vaporstream.Perzona.testUtils.AndroidTestBase;
import vaporstream.Perzona.testUtils.DataSupplier;
import vaporstream.Perzona.utils.ExternalServices;
import vaporstream.Perzona.utils.PhoneNumberGenerator;

//Comentamos la linea el Annotations del Listeners antes de la clase porque lo incluimos para todas las clases dentro de la definicion del Test Suite
//@Listeners({vaporstream.Perzona.testUtils.SignInListeners.class})
public class Test02_SignIn extends AndroidTestBase {

  @SuppressWarnings("deprecation")
  @BeforeMethod
  public void SetupTest() {
    // To find App Package & App Activity:
    // 1- Open the app on the emulator and leave it on the page you want to identify
    // 2- Run the following command:
    // Windows: adb shell dumpsys window | findstr "mCurrentFocus"
    // Linux/Mac: adb shell dumpsys window | grep -e "mCurrentFocus"
    // The result would be of the form:
    // mCurrentFocus=Window{c04cf29 u0 PACKAGE/ACTIVITY}
    driver.resetApp();
    // Activity firstPage = new Activity("vaporstream.perzonas_tst",
    // "vaporstream.perzonas_tst.MainActivity");
    // driver.startActivity(firstPage);
  }

  @Test( testName = "Sign-In Test", dataProviderClass = DataSupplier.class, dataProvider = "signInData")
  public void SignInTest(String countryCode, String countryName, String phoneNumber, boolean invalidPhoneNumberTest,
      boolean editPhoneNumberTest, boolean wrongOTPTest, boolean delayedOTPTest)
      throws Exception {

    System.out.println("\n---- SignIn Test Started ----");
    System.out.println("Phone Number: +" + countryCode + phoneNumber);
    System.out.println("Testing Paramaters:");
    System.out.println("\tCountry Code: " + countryCode);
    System.out.println("\tCountry Name: " + countryName);
    System.out.println("\tPhone Number: " + phoneNumber);
    System.out.println("\tTest Invalid Phone Number: " + (invalidPhoneNumberTest ? "Yes" : "No"));
    System.out.println("\tTest Phone Number Edition: " + (editPhoneNumberTest ? "Yes" : "No"));
    System.out.println("\tTest Invalid OTP: " + (wrongOTPTest ? "Yes" : "No"));
    System.out.println("\tTest OTP timeout: " + (delayedOTPTest ? "Yes" : "No"));
    System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

    // Validate Pre-existing User
    System.out.println("\nValidating Previous SignUp of Phone Number Provided.");
    boolean signedUp = false;
    signedUp = ExternalServices.validatePreviousSignUp(countryCode, phoneNumber);
    // boolean signedUp = true; // Puesto porque no funciona la verificacion de
    // SignUp porque no se puede
    // obtener el token
    if (signedUp) {
      SoftAssert softAssert = new SoftAssert();
      // On Boarding Screen
      System.out.println(">---- Starting SignUp Test ----");
      System.out.println("Pressing Get Started");
      OnBoardingScreen onBoardingScreen = new OnBoardingScreen(driver);
      onBoardingScreen.getStartedClick();

      // Sign up Screen
      SignUpScreen signUpScreen = new SignUpScreen(driver);
      signUpScreen.setCountrySelection(countryName, countryCode);

      if (invalidPhoneNumberTest) {
        System.out.println(">-- Starting Invalid Phone Number Test ---");
        String invalidPhoneNumber = PhoneNumberGenerator.getNewPhoneNumber(countryCode, false);
        signUpScreen.setPhoneNumber(invalidPhoneNumber);
        signUpScreen.verifyInvalidPhoneMessage(softAssert);
        System.out.println("<--- End of Invalid Phone Number Test ---");
      } else {
        System.out.println(">--- Sign Up with Given Phone Number Test ---");
      }

      if (editPhoneNumberTest) {
        System.out.println(">>--- Starting Phone Number Edition Test ---");
        String permutedPhoneNuber = PhoneNumberGenerator.getPermutedPhoneNumber(phoneNumber);
        signUpScreen.setPhoneNumber(permutedPhoneNuber);
        Thread.sleep(500);
        signUpScreen.continueToVerifyPhoneNumber();
        Thread.sleep(500);
        signUpScreen.editPhoneNumber();
        Thread.sleep(500);
        System.out.println("<<--- Ending Phone Number Edition Test ---");
      }

      signUpScreen.setPhoneNumber(phoneNumber);
      Thread.sleep(500);
      signUpScreen.continueToVerifyPhoneNumber();
      // Go to OTP
      Thread.sleep(1000); // SIN ESTA PAUSA NO FUNCIONA !!
      signUpScreen.continueToOTP();

      System.out.println(">--- Start of OTP Test ---");
      // Get OTP Code
      String otpCode = ExternalServices.getOTP(countryCode, phoneNumber);

      // Verify Screen (OTP Validation)
      VerifyScreen verifyScreen = new VerifyScreen(driver);
      // Wrong OTP TEST
      if (wrongOTPTest) {
        System.out.println(">>--- Starting Invalid OTP Test ---");
        String invalidOtpCode = ExternalServices.invalidOTP(otpCode);
        verifyScreen.setCodeField(invalidOtpCode);
        verifyScreen.clickOK();
        System.out.println("<<--- End of Invalid OTP Test ---");
      }
      // Delayed OTP TEST
      if (delayedOTPTest) {
        System.out.println(">>--- Starting Timeout OTP Test ---");
        Thread.sleep(55000); // ESTA SALTANDO POR TIMEOUT - REVISAR !!!
        // verifyScreen.setCodeField(otpCode); // ESTE DEBERIA FALLAR PERO AL 19/09/23
        // NO FALLA PORQUE LO HAN CONFIGURADO ASI EN LA APP
        // verifyScreen.clickOK(); // SE COMENTA HASTA QUE SE RESUELVA LO COMENTADO
        // ARRIBA
        verifyScreen.requestNewOTP();
        Thread.sleep(2000);
        otpCode = ExternalServices.getOTP(countryCode, phoneNumber);
        System.out.println("<<--- End of Timeout OTP Test ---");
      }
      // Normal OTP Test
      verifyScreen.setCodeField(otpCode);
      System.out.println("<--- End of OTP Test ---");
      System.out.println("\n--- SignIn Test Finished  ---");
    } else {
      System.out.println("\n--- Not possible to execute SignIn Test. User never SignedUp ---");
      Assert.assertTrue(false, "Not possible to execute SignIn Test. User never SignedUp");
    }
  }
  
}
