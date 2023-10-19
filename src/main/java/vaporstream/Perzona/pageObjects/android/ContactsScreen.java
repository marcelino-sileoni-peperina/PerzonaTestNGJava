package vaporstream.Perzona.pageObjects.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import vaporstream.Perzona.utils.AndroidActions;

public class ContactsScreen extends AndroidActions {
  
  AndroidDriver driver;
  
  public ContactsScreen(AndroidDriver driver) {
    super(driver);
    this.driver = driver;
    PageFactory.initElements(new AppiumFieldDecorator(driver), this);
  }
  
  // LOCATORS
  
  @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.PathView")
  // v67
  private WebElement closeContactsScreen;
  @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Sync phonebook again']/android.widget.TextView")
  // v67
  private WebElement syncContactsAgain;
  @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Search...']") // v67
  private WebElement searchContact;
  @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"contactsScreen.inviteButton\"])[1]/android.widget.TextView")
  // v67
  private WebElement inviteContact1;
  @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"contactsScreen.inviteButton\"])[2]/android.widget.TextView")
  // v67
  private WebElement inviteContact2;
  @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"contactsScreen.inviteButton\"])[3]/android.widget.TextView")
  // v67
  private WebElement inviteContact3;
  
  
  // ACTIONS METHODS
  
  public void syncPhoneBookAgain() {
    System.out.println("Sync Phonebook - Contacts");
    syncContactsAgain.click();
  }
  
}
