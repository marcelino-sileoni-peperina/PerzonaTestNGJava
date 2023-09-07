package vaporstream.Perzona.testUtils;

import java.net.MalformedURLException;
import java.time.Duration;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import vaporstream.Perzona.utils.AppiumUtils;

public class IOSTestBase extends AppiumUtils{

	public IOSDriver driver;

	// NORMALIZAR como se hizo con el de AndroidTestBase
	
	@BeforeClass
	public void ConfigureAppium() throws MalformedURLException {
		//Start Appium Server
		service = startAppiumServer("127.0.0.1",4723); // DESCOMENTAR SI NO SE USA SERVICIO EXTERNO
		//Set Up IOS Driver
		UiAutomator2Options options = new UiAutomator2Options();
//		options.setApp("D:\\09-PROGRAMACION\\QA\\Perzona\\Perzona\\src\\test\\java\\resources\\Perzona_latest.apk");
		options.setDeviceName("XXXXXXXXXXXX");
		options.setPlatformName("ios");
		options.setCapability("appium:platformVersion", "xxx");
		options.setCapability("noReset", "false");
		driver = new IOSDriver(service.getUrl(), options);
//		driver = new IOSDriver(new URL("http://127.0.0.1:4723"), options);
		// Fijamos un timeout para la busqueda de elementos de 10 segundos:
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 
	}
	

	@AfterClass
	public void tearDownAndroidDriver() {
		//Cerramos la app en el celular:
		driver.quit(); 

	}
	
	@AfterClass
	public void tearDownAppiumServer() {
//	Stop Appium Server - COMENTADO PORQUE SE COMENTO EL ARRANQE DEL SERVICIO ARRIBA
//	service.stop();
	}

}
