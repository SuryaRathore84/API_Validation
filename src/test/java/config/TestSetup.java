package config;

import java.lang.reflect.Method;
import java.util.HashMap;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import reusablecomponents.Utilities;

/**
 * 
 * Base file for all tests. Utilized for initializing test data files and
 * 
 * setting up driver/other required setups.
 *
 * 
 * 
 */

public class TestSetup {

	public static ExtentReports report, log;
	public static ExtentTest logger, loggerForLogs;
	public static String testName, browser, reportName, excelReport;
	public static int testCasePassed = 0, testCaseFailed = 0, testCaseExecuted = 0, testCaseSkipped = 0;
	public static HashMap<String, String> testCasesToBeExecuted = new HashMap<String, String>();

	public static HashMap<String, String> testCaseBrowser = new HashMap<String, String>();

	public static HashMap<String, String> testCaseCategory = new HashMap<String, String>();

	public static HashMap<Integer, String> testCaseResult = new HashMap<Integer, String>(),

			testCaseName = new HashMap<Integer, String>(), testCaseId = new HashMap<Integer, String>(),

			testCaseResults = new HashMap<Integer, String>();

	public static String[][] testCases;

	public boolean toBeTested = true;

	public boolean localSiteTest = false;

	public static String runMode = "local";

	public static int testCaseCount;

	public static final HashMap<String, String> urlRedirectionResult = new HashMap<String, String>();

	public static final HashMap<String, String> urlRedirectionActualResult = new HashMap<String, String>();

	/**
	 * 
	 * This function will be executed before each execution run.
	 *
	 * 
	 * 
	 * @throws Throwable
	 * 
	 * @throws FrameworkException
	 * 
	 */

	@BeforeSuite

	public void beforeSuite() throws FrameworkException, Throwable {

		reportName = "LocalRun_";

		reportName = Utilities.dateFormat("T+0", "MM_dd_yyyy") + "/" + reportName

				+ Utilities.dateFormat("T+0", "MM_dd_yyyy") + "_"

				+ Utilities.getTimeStamp("local").replace("-", "").replace(":", "");

		report = new ExtentReports("Reports/" + reportName + ".html");

		log = new ExtentReports("Logs/" + reportName + ".html");

		testCaseCount = 0;

	}

	/**
	 * 
	 * This method will be executed before each test run and is designed to invoke
	 * 
	 * respective browser.
	 *
	 * 
	 * 
	 * For Mobile execution - It will invoke automatically appium server using
	 * 
	 * AppiumServerJava.startServer() method
	 *
	 * 
	 * 
	 * @param method
	 * 
	 * @throws Throwable
	 * 
	 */

	@BeforeMethod

	public void beforeMethod(Method method) throws Throwable {

		testCaseCount++;

		testName = method.getName();

		///// start the HTML report ////

		logger = report.startTest(testName, "");

		loggerForLogs = log.startTest(testName);

		testCaseName.put(testCaseCount, testName);

		try {

			System.out.println("================== Execution Started for ===================>> " + testName);

			if (runMode.equalsIgnoreCase("remote")) {

				if (!testCaseCategory.get(testName).toLowerCase().equals("api")) {

					testCaseCategory.put(testName, System.getProperty("TestCategory"));

					testCaseBrowser.put(testName, System.getProperty("BrowserDevice"));

				}

			}

		} catch (NullPointerException e) {

			// toBeTested = false;

			logger.log(LogStatus.SKIP,

					testName + " not configured. Please check data file and function name for consistency.");

		}

	}

	/**
	 * 
	 * This function will be executed after each test run. If test is a web test
	 * 
	 * then this function will close the driver.
	 *
	 * 
	 * 
	 * @throws InterruptedException
	 * 
	 */

	@AfterMethod

	public void afterMethod(ITestResult result) throws InterruptedException {

		report.endTest(logger);

		report.flush();

		log.endTest(loggerForLogs);

		log.flush();

		String testCaseStatus = null;

		// ITestResult result = Reporter.getCurrentTestResult();

		switch (result.getStatus()) {

		case ITestResult.SUCCESS:

			testCasePassed++;

			testCaseStatus = "PASS";

			break;

		case ITestResult.FAILURE:

			testCaseFailed++;

			testCaseStatus = "FAIL";

			break;

		case ITestResult.SKIP:

			testCaseSkipped++;

			testCaseStatus = "SKIP";

			break;

		}

		testCaseResult.put(testCaseCount, testCaseStatus);

		testCaseExecuted = testCasePassed + testCaseFailed;

	}

	/**
	 * 
	 * This function will be executed before each execution run.
	 *
	 * 
	 * 
	 * @throws Throwable
	 * 
	 * @throws FrameworkException
	 * 
	 */

	@AfterSuite

	public void afterSuite() throws FrameworkException, Throwable {

		System.out.println(" Report Location " + System.getProperty("user.dir") + "/Reports/" + reportName + ".html");

		try {

			System.out.println("Test Cases Executed: " + testCaseExecuted);

			System.out.println("Test Cases Passed: " + testCasePassed);

			System.out.println("Test Cases Failed: " + testCaseFailed);

			System.out.println("Test Cases Skipped: " + testCaseSkipped);

		} catch (Exception e) {

			System.out.println(" Exception while writing the log " + e.getMessage());

		}

	}

	public static void loggerLogReportFail(String failDesc) {

		try {

			System.out.println(" FAIL :: " + failDesc);

			logger.log(LogStatus.FAIL, failDesc);

		} catch (Exception e) {

			System.out.println(" Exception while writing the log " + e.getMessage());

		}

	}

	public static void loggerLogReportPass(String passDesc) {

		try {

			System.out.println(" Pass :: " + passDesc);

			logger.log(LogStatus.PASS, passDesc);

		} catch (Exception e) {

			System.out.println(" Exception while writing the log " + e.getMessage());

		}

	}

	public static void loggerLogReportSkip(String skipDesc) {

		try {

			System.out.println(" Skip :: " + skipDesc);

			logger.log(LogStatus.SKIP, skipDesc);

		} catch (Exception e) {

			System.out.println(" Exception while writing the log " + e.getMessage());

		}

	}

	public static void loggerLogReportInfo(String infoDesc) {

		try {

			System.out.println(" Info :: " + infoDesc);

//                                            logger.log(LogStatus.INFO, infoDesc);

		} catch (Exception e) {

			System.out.println(" Exception while writing the log " + e.getMessage());

		}

	}

	public static void loggerLogReportWarning(String warningDesc) {

		try {

			System.out.println(" Warning !!! " + warningDesc);

			logger.log(LogStatus.WARNING, warningDesc);

		} catch (Exception e) {

			System.out.println(" Exception while writing the log " + e.getMessage());

		}

	}

}