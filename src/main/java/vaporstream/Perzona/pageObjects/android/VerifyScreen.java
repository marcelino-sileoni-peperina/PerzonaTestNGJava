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
	
	// ACTIONS METHODS
	
	public void setCodeField(String otpCode) {
		codeField.sendKeys(otpCode);
//		driver.hideKeyboard();
	}
}
