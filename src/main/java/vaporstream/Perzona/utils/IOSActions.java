package vaporstream.Perzona.utils;

import io.appium.java_client.ios.IOSDriver;

public class IOSActions extends AppiumUtils{

IOSDriver driver;
	
	public IOSActions (IOSDriver driver) {
		//super(driver); // Puesto que IOSActions extiende a AppiumUtils y esta clase tiene un constructor que requiere de un driver para su creación, con este comando decimos que tome el driver que recibe del invocante y lo envie a AppiumUtils para su uso. 
		this.driver =  driver; // Además tomamos el driver que viene del invocante para usarlo en la propia instancia de esta clase.
	}
	
	
}
