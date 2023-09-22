package vaporstream.Perzona.pageObjects.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import vaporstream.Perzona.utils.AndroidActions;

public class VerifyScreen extends AndroidActions{

	AndroidDriver driver;	

	public VerifyScreen(AndroidDriver driver) {
		super(driver); 
		this.driver = driver; 
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	// LOCATORS
	
	@AndroidFindBy(accessibility="verifyScreen:codeField")
	private WebElement codeField;
	
	@AndroidFindBy(id="android:id/button1")
	private WebElement invalidCodeOK;
	
//	@AndroidFindBy(accessibility="verifyScreen.requestNewCode")
//	@AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.TextView")
	@AndroidFindBy(xpath="//android.widget.TextView[@text='Request a new one ']")
	private WebElement newOTP;

	// ACTIONS METHODS
	
	public void setCodeField(String otpCode) {
		codeField.sendKeys(otpCode);
	}
	
	public void clickOK() {
		invalidCodeOK.click();;
	}
	
	public void requestNewOTP() {
		System.out.println("Asking for new OTP");
		newOTP.click();
	}
}
