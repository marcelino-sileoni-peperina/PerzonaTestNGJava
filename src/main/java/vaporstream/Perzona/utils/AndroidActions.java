package vaporstream.Perzona.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AndroidActions extends AppiumUtils{
	
	AndroidDriver driver;
	
	public AndroidActions (AndroidDriver driver) {
		//super(driver); // Puesto que IOSActions extiende a AppiumUtils y esta clase tiene un constructor que requiere de un driver para su creación, con este comando decimos que tome el driver que recibe del invocante y lo envie a AppiumUtils para su uso.
		this.driver =  driver; // Además tomamos el driver que viene del invocante para usarlo en la propia instancia de esta clase.
	}

	public void longPressAction(WebElement elem) {
		((JavascriptExecutor)driver).executeScript("mobile: longClickGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement)elem).getId(),
						"duration",2000));
	}
	
	public void scrollToEndAction() {
		boolean canScrollMore;
		do {
		canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
		    "left", 100, "top", 100, "width", 300, "height", 300,
		    "direction", "down",
		    "percent", 3.0
		));
		} while (canScrollMore==true);
	}
	
	public void scrollToText(String text) {
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));"));
	}
	
	public void swipeAction(WebElement elem, String direction) {
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
				"elementId",((RemoteWebElement)elem).getId(),
		    "direction", direction,
		    "percent", 0.75
		));
	}
	
}
