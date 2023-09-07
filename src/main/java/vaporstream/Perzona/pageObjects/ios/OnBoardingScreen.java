package vaporstream.Perzona.pageObjects.ios;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import vaporstream.Perzona.utils.IOSActions;

public class OnBoardingScreen extends IOSActions{

	IOSDriver driver;	

	public OnBoardingScreen(IOSDriver driver) {
		super(driver); 
		this.driver = driver; 
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	// LOCATORS
	
	@iOSXCUITBy(id="onBoardingScreen:getStarted")
	private WebElement getStarted;

	// ACTIONS METHODS
	
	public void getStartedClick() {
		getStarted.click();
	}
}
