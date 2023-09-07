package vaporstream.Perzona.pageObjects.android;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import vaporstream.Perzona.utils.AndroidActions;

public class SignUpScreen extends AndroidActions{

	AndroidDriver driver;	

	public SignUpScreen(AndroidDriver driver) {
		super(driver); 
		this.driver = driver; 
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	// LOCATORS
	
	@AndroidFindBy(accessibility="singUpScreen:phoneInput")
	private WebElement phoneInput;
	
	// @AndroidFindBy(id="singUpScreen:countrySelector")  // ID A CONFIRMAR
	@AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.ImageView")
	private WebElement countrySelector;

//	@AndroidFindBy(id="text-input-country-filter") // NO FUNCIONA
	@AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.EditText")
	private WebElement countryFilter;

	// ACTIONS METHODS
	
	public void setCountrySelection(String countryName, String countryCode){
		countrySelector.click();
//		driver.findElement(By.id("text-input-country-filter")).sendKeys(countryName); //text-input-country-filter
		countryFilter.sendKeys(countryName);
		//		scrollToText(countryName); // Se dejó de usar al usar el filtro de texto de arriba
		//		driver.findElement(By.xpath("//android.widget.TextView[@text='"+countryName+"']")).click(); // Se dejó de usar con el uso del filtro de texto de arriba
		driver.findElement(By.xpath("//android.widget.TextView[@text='"+countryName+" (+"+countryCode+")"+"']")).click();
	}
	
	public void setPhoneNumber(String phoneNumber) {
		phoneInput.sendKeys(phoneNumber);
		driver.hideKeyboard();
	}
}
