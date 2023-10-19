package vaporstream.Perzona.pageObjects.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import vaporstream.Perzona.utils.AndroidActions;

import java.time.Duration;

public class DiscoveryScreen extends AndroidActions{

	AndroidDriver driver;	

	public DiscoveryScreen(AndroidDriver driver) {
		super(driver); 
		this.driver = driver; 
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	// LOCATORS
	
	// 
	@AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]") // v67
	private WebElement syncSwitch;
	
	@AndroidFindBy(accessibility="discoveryScreen.inviteContactsManually") // v67 - ESTA MAL - DEBERIA SER connectionScreen.inviteContactsManually - NO coincide el nomobre de screen de este codigo con el de la App. Acá lo llamamos connectionScreen, en la app se llama discoveryScreen. VER
	private WebElement inviteContactsManually;
	
//	@AndroidFindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[3]") v60
//	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc='profileScreen.continueButton']") // v67
	@AndroidFindBy(accessibility="profileScreen.continueButton") // v67 - ESTA MAL - DEBERIA SER connectionScreen.continueButton - NO coincide el nomobre de screen de este codigo con el de la App. Acá lo llamamos connectionScreen, en la app se llama discoveryScreen. VER
	private WebElement continueButton;

//	@AndroidFindBy(xpath="//android.widget.TextView[@text='Skip for now']") // v60
	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"discoveryScreen.adviceBox.skipSync\"]/android.widget.TextView") // v67 - AL 01/10/23 FUNCIONA
	private WebElement skipForNowAdviceBox;
	
//	@AndroidFindBy(xpath="//android.widget.TextView[@text='Sync contacts']") // v60
	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"discoveryScreen.adviceBox.syncContacts\"]/android.widget.TextView") // v67 - PENDIENTE DE PRUEBA AL 28/09/23
	private WebElement syncContactsAdviceBox;
	
	@AndroidFindBy(id="com.android.permissioncontroller:id/permission_allow_button") // v67
	private WebElement allowPerzonaAccessContacts;
	
	@AndroidFindBy(id = "com.android.permissioncontroller:id/permission_deny_button")
	private WebElement denyPerzonaAccessContacts;
	
	
	// ACTIONS METHODS
	
	public void continueWithoutSyncContacts() {
		System.out.println("Continue without Sync Contacts");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
		wait.until(ExpectedConditions.visibilityOf(continueButton)).click();
		wait.until(ExpectedConditions.visibilityOf(skipForNowAdviceBox)).click();
	}
	
	public void shareInvitationManually() {
		inviteContactsManually.click();
		// 28/09/23: Falta completar siguientes pasos o Assert
	}
	
	public void syncContactsSwitchOn() {
		syncSwitch.click();
		allowPerzonaAccessContacts.click();
	}
}
