package vaporstream.Perzona.pageObjects.ios;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import vaporstream.Perzona.utils.IOSActions;

public class VerifyScreen extends IOSActions{

	IOSDriver driver;	

	public VerifyScreen(IOSDriver driver) {
		super(driver); 
		this.driver = driver; 
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	// LOCATORS
	//    driver.findElement(AppiumBy.accessibilityId("verifyScreen:codeField")).sendKeys(otpCode);;
	@iOSXCUITBy(accessibility="verifyScreen:codeField")//revisar si iOSXCUITBy es la opcion correcta
	private WebElement codeField;
	
	// ACTIONS METHODS
	
	public void setCodeField(String otpCode) {
		codeField.sendKeys(otpCode);
//		driver.hideKeyboard();
	}
}
