package vaporstream.Perzona.pageObjects.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import vaporstream.Perzona.utils.AndroidActions;

public class ConnectionsScreen extends AndroidActions{

	AndroidDriver driver;	

	public ConnectionsScreen(AndroidDriver driver) {
		super(driver); 
		this.driver = driver; 
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	// LOCATORS
	
	@AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]")
	private WebElement continueButton;

	@AndroidFindBy(xpath="//android.widget.TextView[@text='Skip for now']")
	private WebElement skipForNow;
	
	// ACTIONS METHODS
	
	public void continueWithoutSyncContacts() {
		System.out.println("Continue without Sync Contacts");
		continueButton.click();
		skipForNow.click();
	}
}