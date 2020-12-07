package common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.codeborne.selenide.Configuration;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public abstract class UiTestBase {

	private static final String REPORTDIR = "REPORT.UITEST.DIR";
	private static final String BROWSER = "UITEST.BROWSER";
	private static final String HEADLESS = "UITEST.BROWSER.HEADLESS";

	protected static final String BASEURL = "BASE.URL";
	protected SecureRandom wheel = new SecureRandom();

	private static ExtentReports report;
	private static int screenshotCounter = 100;
	private ExtentTest reportLogger;

	@BeforeClass
	public static void setUpClass() {
		//System.setProperty("webdriver.chrome.logfile", "/tmp/chromedriver.log");
		//System.setProperty("webdriver.chrome.verboseLogging", "true");
		if (System.getProperty(REPORTDIR) == null) {
			System.setProperty(REPORTDIR, "build/reports/extent");
		}
		else {
			new File(System.getProperty(REPORTDIR)).mkdirs();
		}
		if (System.getProperty(BROWSER) == null) {
			System.setProperty(BROWSER, "chrome");
		}
		if (System.getProperty(BASEURL) == null) {
			System.setProperty(BASEURL, "http://localhost:8080");
		}
		if (System.getProperty(HEADLESS) == null) {
			System.setProperty(HEADLESS, "true");
		}

		String reportPath = System.getProperty(REPORTDIR) + "/" + "index.html";
		System.out.println("Writing Report to " + reportPath);

		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
		htmlReporter.setAppendExisting(true);

		report = new ExtentReports();
		report.attachReporter(htmlReporter);

		Configuration.reportsFolder = System.getProperty(REPORTDIR);
		Configuration.browser = System.getProperty(BROWSER);
		Configuration.headless = Boolean.valueOf(System.getProperty(HEADLESS));
		Configuration.savePageSource = false;
		Configuration.browserSize = "1920x1080";
        // Configuration.timeout = 8000;
		System.setProperty("chromeoptions.args", "--lang=de");
	}

	@Before
	public void setUp() {
		reportLogger = null;
	}

	@After
	public void tearDown() {
		report.flush();
	}

	protected void startReport(String testName) {
		startReport(testName, "");
	}

    protected void flushReport() {
        report.flush();
    }

    protected void startReport(String testName, String testDescription) {
		if (StringUtils.isBlank(testName)) {
			testName = "Default-Test";
		}
		reportLogger = report.createTest(testName, testDescription);
		System.out.println("Starting Test " + testName);
	}

	protected void report(Status status, String msg) {
		report(status, msg, true);
	}

	protected void report(Status status, String msg, boolean captureScreen) {
		if (reportLogger != null) {
			if (captureScreen) {
				logWithScreenshot(status, msg);
			}
			else {
				reportLogger.log(status, msg);
			}
			System.out.println(msg);
		}
	}

	protected void failOnException(Throwable e) {
		failWithMessage(e.toString(), e);
	}

	private void failWithMessage(String msg, Throwable t) {
		if (reportLogger == null) {
			startReport("Error Report");
		}
		try {
			report(Status.FAIL, msg + " caused by " + t.toString(), true);
		}
		catch (Exception e) {
			// Probleme mit Screenshot, also ohne Screenshot loggen
			report(Status.FAIL, msg + " caused by " + t.toString() + e.toString(), false);
		}
		org.junit.Assert.fail();
	}

	protected void logWithScreenshot(Status status, String msg) {
		if (reportLogger != null) {
			String fileNameCounter = String.valueOf(screenshotCounter++);
			String fileName = "screen-" + fileNameCounter + ".png";

			TakesScreenshot screenshot = (TakesScreenshot) getWebDriver();
			try {
				File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(sourceFile, new File(System.getProperty(REPORTDIR) + "/", fileName));
				MediaEntityModelProvider mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(fileName).build();
				reportLogger.log(status, msg, mediaModel);
			}
			catch (WebDriverException | IOException e) {
				msg = " (Could not save Screenshot: " + e.getMessage() + ")";
				reportLogger.log(Status.WARNING, msg);
			}
		}
	}

}
