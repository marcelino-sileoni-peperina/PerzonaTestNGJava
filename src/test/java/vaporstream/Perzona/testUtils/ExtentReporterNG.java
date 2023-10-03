package vaporstream.Perzona.testUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

	public static ExtentReports getReporterObject() {
		// ExtentRepors & ExtentSparkReporter
		String path = System.getProperty("user.dir") + "\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Perzona Automation Tests - Reports");
		reporter.config().setDocumentTitle("Perzona Tests");

		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Peperina QA");
//		extent.setSystemInfo("Any System Info", "Any value");
//		extent.setAnalysisStrategy(null); // Ver 
		return extent;
	}

}
