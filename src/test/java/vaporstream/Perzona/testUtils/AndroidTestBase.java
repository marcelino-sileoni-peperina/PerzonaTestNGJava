package vaporstream.Perzona.testUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import vaporstream.Perzona.utils.AppiumUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class AndroidTestBase extends AppiumUtils {
  
  public AndroidDriver driver;
  
  @BeforeClass(alwaysRun = true)
  public void ConfigureAppium() throws IOException {
    Properties prop = new Properties();
    FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "vaporstream" + File.separator + "Perzona" + File.separator + "resources" + File.separator + "data.properties");
    prop.load(fis);
    // System.getProperty(ipAddress) is not null if we introduce its value from command line:
    // mvn test -PTESTIDENTIFIER -DipAddress=127.0.0.1
    // This way we can override the default properties defined in data.properties file
    @SuppressWarnings("unused")
    String ipAddress = System.getProperty("ipAddress") != null ? System.getProperty("ipAddress") : prop.getProperty("ipAddress");
    //		String ipAddress = prop.getProperty("ipAddress");
    // mvn test -PTestIdentifier -Dport=4723
    @SuppressWarnings("unused")
    String port = System.getProperty("port") != null ? System.getProperty("port") : prop.getProperty("port");
    // Start Appium Server - Only if NOT started from outside this code
    //		service = startAppiumServer(ipAddress,Integer.parseInt(port));
    //		service = startAppiumServer("127.0.0.1",4723);
    // Set Up Android Driver
    UiAutomator2Options options = new UiAutomator2Options();
    String apk = prop.getProperty("apk");
    options.setApp(apk);
    String AndroidDeviceName = prop.getProperty("AndroidDeviceName");
    options.setDeviceName(AndroidDeviceName);
    String PlatformName = prop.getProperty("PlatformName");
    options.setPlatformName(PlatformName);
    String platformVersion = prop.getProperty("platformVersion");
    options.setCapability("platformVersion", platformVersion);
    //		String noReset = prop.getProperty("noReset");
    //		options.setCapability("noReset", noReset);
    //		String fullReset = prop.getProperty("fullReset");
    //		options.setCapability("fullReset", fullReset);
    //		driver = new AndroidDriver(service.getUrl(), options);
    driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
    // Fijamos un timeout para la busqueda de elementos de 5 segundos:
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    //		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
    //		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(90));
    fis.close();
  }
  
  
  @AfterClass(alwaysRun = true)
  public void tearDownAndroidDriver() {
    //Cerramos la app en el celular:
    //		driver.resetApp();
    driver.quit();
    
  }
  
  @AfterClass(alwaysRun = true)
  public void tearDownAppiumServer() {
//	Stop Appium Server - COMENTADO PORQUE SE COMENTO EL ARRANQE DEL SERVICIO ARRIBA
//	service.stop();
  }
  
}
