package vaporstream.Perzona.testUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import vaporstream.Perzona.utils.AppiumUtils;

public class AndroidTestBase extends AppiumUtils{

	public AndroidDriver driver;

	@BeforeClass
	public void ConfigureAppium() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\vaporstream\\Perzona\\resources\\data.properties");
		prop.load(fis);
		String ipAddress = prop.getProperty("ipAddress");
		String port = prop.getProperty("port");
		//Start Appium Server
//		service = startAppiumServer(ipAddress,Integer.parseInt(port));
//		service = startAppiumServer("127.0.0.1",4723);
		//Set Up Android Driver
		UiAutomator2Options options = new UiAutomator2Options();
		String apk = prop.getProperty("apk");
		options.setApp(apk);
		String AndroidDeviceName = prop.getProperty("AndroidDeviceName");
		options.setDeviceName(AndroidDeviceName);
		String PlatformName = prop.getProperty("PlatformName");
		options.setPlatformName(PlatformName);
		String platformVersion = prop.getProperty("platformVersion");
		options.setCapability("platformVersion", platformVersion);
		String noReset = prop.getProperty("noReset");
		options.setCapability("noReset", noReset);
//		driver = new AndroidDriver(service.getUrl(), options);
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
		// Fijamos un timeout para la busqueda de elementos de 10 segundos:
//		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20)); 
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
