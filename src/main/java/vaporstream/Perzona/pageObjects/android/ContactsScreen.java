package vaporstream.Perzona.pageObjects.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import vaporstream.Perzona.utils.AndroidActions;

public class ContactsScreen extends AndroidActions{

	AndroidDriver driver;	

	public ContactsScreen(AndroidDriver driver) {
		super(driver); 
		this.driver = driver; 
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	// LOCATORS
	
	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"Sync phonebook\"]/android.widget.TextView") // v67
	private WebElement syncPhonebook;
	
	@AndroidFindBy(xpath="//android.widget.EditText[@hint='Search...']") // v67
	private WebElement searchContact;
	
	// ACTIONS METHODS
	
	public void syncPhoneBook() {
		System.out.println("Sync Phonebook - Contacts");
		syncPhonebook.click();
	}
	
}
