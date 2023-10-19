package vaporstream.Perzona.utils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppiumUtils {

	// EN ESTA CLASE DEFINIREMOS OBJETOS Y METODOS COMUNES A ANDROID y IOS

	// INTERESANTE: la clase AppiumDriver es padre de IOSDriver y de AndroidDriver.
	// Asi nos permite definir metodos comunes a cualquiera de las plataformas.
	// AppiumDriver driver;
	// public AppiumUtils(AppiumDriver driver) {
	// this.driver = driver;
	// }
	// FINALMENTE eliminamos esta porcion de codigo para evitar tener que crear una
	// instancia de driver aun llamando a un metodo que no lo requiere.
	// En caso que el metodo lo requiera, se lo incluye dentro de los parametros del
	// mismo. Ver abajo el metodo waitForElementToAppear.
	public AppiumDriverLocalService service;

	public AppiumDriverLocalService startAppiumServer(String ipAddress, int port) {
		// start Appium Server - Codigo comun tanto para pruebas con Android como con
		// IOS
		service = new AppiumServiceBuilder()
				.withAppiumJS(
						new File("C:/Users/Marcelino Sileoni/AppData/Roaming/npm/node_modules/appium/build/lib/main.js"))
				.withIPAddress(ipAddress).usingPort(port).build();
//		service = new AppiumServiceBuilder().withAppiumJS(new File("C:/Users/Marcelino Sileoni/AppData/Roaming/npm/node_modules/appium/build/lib/main.js"))
//				.withIPAddress("127.0.0.1").usingPort(4723).build();
		service.start();
		return service;
	}

	public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException {
		// convert json file content to json string
		// System.getProperty("user.dir")+"/src/test/java/org/msGroupAppium/testData/eCommerce.json"
//		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		@SuppressWarnings("deprecation")
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath));

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}

	// El sig es un metodo generico que se usa para esperar a que aparezca la
	// proxima pagina identificada por el toolbar_title.
	public void waitForElementToAppear(WebElement element, String toolbarTitle, AppiumDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.attributeContains((element), "text", toolbarTitle));

	}

	public String getScreenshotPath(String testCaseName, AppiumDriver driver) throws IOException {
		// Step 1 of 2: generate screenshot and save it to disk
		// Step 2 of 2: add screenshot from file to report (see Listeners.java)
		File source = driver.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "/reports/" + testCaseName + "-" + CurrentDateTime()
				+ ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
	}

	public static String CurrentDateTime() {
		// Get the current date and time
		LocalDateTime currentDateTime = LocalDateTime.now();
		// Define a date-time formatter to specify the desired format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		// Format the current date and time as a string
		String formattedDateTime = currentDateTime.format(formatter);
		return formattedDateTime;
	}
}
