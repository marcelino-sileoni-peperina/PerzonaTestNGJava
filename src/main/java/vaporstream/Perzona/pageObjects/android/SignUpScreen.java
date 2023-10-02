package vaporstream.Perzona.pageObjects.android;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

//import dev.failsafe.internal.util.Assert;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import vaporstream.Perzona.utils.AndroidActions;

public class SignUpScreen extends AndroidActions {

	AndroidDriver driver;

	public SignUpScreen(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	// LOCATORS

	@AndroidFindBy(accessibility = "singUpScreen.phoneInput")
	private WebElement phoneInput;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Your phone number is invalid']")
	private WebElement invalidPhone;

	// @AndroidFindBy(id="singUpScreen.countryInput") // ID A AGREGAR
	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ImageView")
	private WebElement countryInput;

	// @AndroidFindBy(id="singUpScreen.countryCode") // ID A AGREGAR
	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ImageView")
	private WebElement countryCode;

	// @AndroidFindBy(id="singUpScreen.notValidNumber") // ID A AGREGAR
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Your phone number is invalid']")
	private WebElement notValidNumber;

	// @AndroidFindBy(id="text-input-country-filter") // NO FUNCIONA
	@AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.EditText")
	private WebElement countryFilter;

	// @AndroidFindBy(accessibility="Continue") // VERIFICAR
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Continue']")
	private WebElement continueToVerifyPhoneNumber;

	//
//	@AndroidFindBy(xpath = "//android.view.ViewGroup[@text='Edit']") // VERIFICAR
	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"Edit\"]/android.widget.TextView") // VERIFICAR
	private WebElement editPhoneButton;
	
//@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"Continue\"]/android.widget.TextView") // VERIFICAR
//@AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@text,'Continue')]") // VERIFICAR
	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc='Continue']") // FUNCIONA
private WebElement continueCorrectPhoneButton;

	// ACTIONS METHODS

	public void setCountrySelection(String countryName, String countryCode) {
		countryInput.click();
//		driver.findElement(By.id("text-input-country-filter")).sendKeys(countryName); //text-input-country-filter
		countryFilter.sendKeys(countryName);
		// scrollToText(countryName); // Se dejó de usar al usar el filtro de texto de
		// arriba
		// driver.findElement(By.xpath("//android.widget.TextView[@text='"+countryName+"']")).click();
		// // Se dejó de usar con el uso del filtro de texto de arriba
		driver.findElement(By.xpath("//android.widget.TextView[@text='" + countryName + " (+" + countryCode + ")" + "']"))
				.click();
	}

	public void setPhoneNumber(String phoneNumber) {
		System.out.println("Introducing Phone Number: " + phoneNumber);
		phoneInput.sendKeys(phoneNumber);
		driver.hideKeyboard();
	}

	public void verifyInvalidPhoneMessage(SoftAssert softAssert) {
		String message = invalidPhone.getText();
		softAssert.assertEquals(message, "Your phone number is invalid",
				"Invalid Phone Number Message NOT IDENTICAL TO WHAT EXPECTED");
		System.out.println("Invalid Phone Number Message: verified.");
	}

	public void continueToVerifyPhoneNumber() {
		continueToVerifyPhoneNumber.click();
	}

	public void editPhoneNumber() {
		System.out.println("Correcting Phone Number.");
		editPhoneButton.click();
	}
	
	public void continueToOTP() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(continueCorrectPhoneButton)).click();;
//		continueCorrectPhoneButton.click();
		System.out.println("Pressing Correct Phone Button.");
	}
}
