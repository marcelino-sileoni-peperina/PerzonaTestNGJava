package vaporstream.Perzona.pageObjects.ios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import vaporstream.Perzona.utils.IOSActions;

public class SignUpScreen extends IOSActions{

	IOSDriver driver;	

	public SignUpScreen(IOSDriver driver) {
		super(driver); 
		this.driver = driver; 
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	// LOCATORS
	
	@iOSXCUITBy(id="singUpScreen:phoneInput")
	private WebElement phoneInput;
	
	@iOSXCUITBy(id="singUpScreen:countrySelector")
	private WebElement countrySelector;

	// ACTIONS METHODS
	
	public void setCountrySelection(String countryName){
		countrySelector.click();
//		scrollToText(countryName);
		driver.findElement(By.xpath("//android.widget.TextView[@text='"+countryName+"']")).click();
	}
	
	public void setPhoneNumber(String phoneNumber) {
		phoneInput.sendKeys(phoneNumber);
	}
}
