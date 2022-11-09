package com.actitime.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;



import org.apache.logging.log4j.core.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.google.common.io.Files;

public class BaseClass implements ITestListener{
	
	public static HashMap<String,String> locatorMap = new HashMap<String,String>();	
	public static HashMap<String,String> testDataMap = new HashMap<String,String>();
	
	
	
	public static WebDriver driver;
	
	public static Logger logger = (Logger) LogManager.getLogger(BaseClass.class);	
	
	
	
	public static void writeInfoLogs(String msg)
	{		
		logger.info(msg);
	
	}
	
	

	public static void writeErrorLogs(Throwable t)
	{
		// Capture the exception stack trace and convert it as a String then write the error logs
		String s = Arrays.toString(t.getStackTrace());		
		String s1 = s.replaceAll(",", "\n");
		logger.error(s1);
	
		
	}

	
	@BeforeMethod(alwaysRun = true)
	public static void launchActiTimeApplication()
	{		
		writeInfoLogs(" This method will run before every test case. i.e every @Test method...");
		launchBrowser();
		driver.get(getConfigData("url"));		

	}
	
	public static void launchBrowser()
	{
		String browser = getConfigData("browser");
		
		writeInfoLogs(" Running the test cases using chrome browser.....");
		System.setProperty("webdriver.chrome.driver", "./src/test/java/utilities/chromedriver.exe");		
		driver = new ChromeDriver();
		
		/*
		switch(browser)
		{
			case "firefox":
			{
				writeLogsToFile(" Running the test cases using Firefox browser.....");
				System.setProperty("webdriver.gecko.driver", "./utilities/geckodriver.exe");
				driver = new FirefoxDriver();
			}
		
			case "chrome":
			{	
				writeLogsToFile(" Running the test cases using chrome browser.....");
				System.setProperty("webdriver.chrome.driver", "./src/test/java/utilities/chromedriver.exe");		
				driver = new ChromeDriver();
				break;
			}
			default :
			{
				writeLogsToFile(" Browser not specified , using chrome as the default browser..");
				System.setProperty("webdriver.chrome.driver", "./utilities/chromedriver.exe");		
				driver = new ChromeDriver();
				break;
			}
		}	
		*/
		driver.manage().window().maximize();
		// Applying implicit wait . it is applied only once where the driver is created and applicable through out the life cycle of the driver..
		// polling interval is 500 MS ( half second )
		String s = getConfigData("timeout"); // fetching the timeout from properties file and converting it to Long
		Long t = Long.parseLong(s);		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(t));
	}
	
	public static void launchApplication(String url)
	{
		launchBrowser();
		driver.get(url);
	}
	
	
	@AfterMethod(alwaysRun = true)
	public static void closeBrowser()	
	{
		writeInfoLogs(" This method will run after every test case. i.e every @Test method...");
		driver.quit();
	}
	
	
	
	public static String getConfigData(String key) 
	{		
				
			Properties prop = new Properties();
				try {				
					File f = new File("./src/test/java/data/config.properties");	
					FileInputStream fio = new FileInputStream(f);
					prop.load(fio);					
				} catch (Exception e) {
					e.printStackTrace();
				}			
				String val = prop.getProperty(key);					
				return val;
				
				
	}
	
	
	public static void captureScreenShotOnFailure(String fileName) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;		
		// this method will return an object of File class
		File src = ts.getScreenshotAs(OutputType.FILE);
		
		// Creating an object of File class where we want to save  the screenshot
		File dest = new File("./src/test/java/results/screenshots/"+fileName+".png");
		
		// Copy the screenshot to the destination mentioned above
		Files.copy(src, dest);	
		
	}
	
	
	public static void writeResultsToFile(String testCaseName, String testCaseStatus) throws IOException
	{
		File f = new File("./src/test/java/results/results.txt");
		
		FileWriter fw = new FileWriter(f,true);
		
		fw.write(testCaseName+"-----"+testCaseStatus+"\n");
		
		
		fw.flush();
		fw.close();
		
	}
	
	
	public static void writeLogsToFile(String logMessage) throws IOException
	{
		File f = new File("./src/test/java/results/logs.txt");
		
		FileWriter fw = new FileWriter(f,true);
		
		fw.write(logMessage+"\n");
		
		//writeLogsToFile(logMessage);
		
		fw.flush();
		fw.close();
		
	}
	
	
	public static String getLocatorDataFromExcel(String pageName, String elementName) throws IOException
	{
		String locator = "";
		
		File f = new File("./src/test/java/data/locatordata.xlsx");
		FileInputStream fio = new FileInputStream(f);
		
		// Creating the object of the workbook
		XSSFWorkbook wb = new XSSFWorkbook(fio);
		
		// Creating the object of worksheet
		XSSFSheet ws= wb.getSheet("Sheet1");
		
		// getting the number of rows in the worksheet
		int rows = ws.getLastRowNum();
		
		for(int x=1;x<=rows;x++)
		{
			String page = ws.getRow(x).getCell(0).getStringCellValue();
			String element = ws.getRow(x).getCell(1).getStringCellValue();
			
			if(pageName.equalsIgnoreCase(page) && (elementName.equalsIgnoreCase(element)))
			{
				locator =  ws.getRow(x).getCell(2).getStringCellValue();
				break;
			}			
		}		
		wb.close();		
		return locator;		
	}

	
	public static String getTestDataFromExcel(String pageName, String elementName) throws IOException
	{
		String testData = "";
		
		File f = new File("./src/test/java/data/testdata.xlsx");
		FileInputStream fio = new FileInputStream(f);
		
		// Creating the object of the workbook
		XSSFWorkbook wb = new XSSFWorkbook(fio);
		
		// Creating the object of worksheet
		XSSFSheet ws= wb.getSheet("Sheet1");
		
		// getting the number of rows in the worksheet
		int rows = ws.getLastRowNum();
		
		for(int x=1;x<=rows;x++)
		{
			String page = ws.getRow(x).getCell(0).getStringCellValue();
			String element = ws.getRow(x).getCell(1).getStringCellValue();
			
			if(pageName.equalsIgnoreCase(page) && (elementName.equalsIgnoreCase(element)))
			{
				testData =  ws.getRow(x).getCell(2).getStringCellValue();
				break;
			}			
		}	
		wb.close();		
		return testData;		
	}
	
	//@BeforeSuite
	public static void beforeSuiteMethod() throws IOException
	{
		writeLogsToFile(" This method is the first method to run....");
	}
	
	//@AfterSuite
	public static void afterSuiteMethod() throws IOException
	{
		writeLogsToFile(" This method is the last and final method to run....");
	}
	
	@BeforeClass
	public static void beforeClassMethod() throws IOException
	{
		writeLogsToFile(" This method is will run before every class....");
	}
	
	@AfterClass
	public static void afterClassMethod() throws IOException
	{
		writeLogsToFile(" This method is will run after every class....");
	}
	
	@BeforeTest
	public static void  beforeTestMethod() throws IOException
	{
		writeLogsToFile(" This method will run before every testng.xml test");
	}
	
	@AfterTest
	public static void  afterTestMethod() throws IOException
	{
		writeLogsToFile(" This method will run after every testng.xml test");
	}
	
	// Implementation for TestNG ITestListerner Methods...
	
	

	
	// ITest Listener interface default methods.. these will help us to capture the running status of the tests 
	// as same as Before Suite
	public void onStart(ITestContext context) 
	{
		try {
			writeLogsToFile(" ##### This is a listener method and this will run in the begining of the entire suite####");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		File f = new File("./src/test/java/results/results.txt");
		
		// Creating the object of File Writer to write the results by appending to the existing data
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			fw.write("Results for the current run \n");
			fw.flush();
			fw.close();	
		} catch (IOException e1) { 
			e1.printStackTrace();
		}
	
		
		
		// Cleaning up Log files for every run
		
		File f1 = new File("./src/test/java/results/logs.txt");
		
		// Creating the object of File Writer to write the results by appending to the existing data
		FileWriter fw1;
		try {
			fw1 = new FileWriter(f1);
			fw1.write("Logs for the current run \n");
			fw1.flush();
			fw1.close();	
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		
		try {
			getAndStoreLocatorDataInToMap();
			getAndStoreTestDataInToMap();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		
		
	}	
	
	// This is just like after suite.. and will run at the end of execution..
	public void onFinish(ITestContext context) {
		try {
			writeLogsToFile(" ##### This is a listener method and this will run in at the end of entire suite####");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public void onTestStart(ITestResult result) {	
		
	
				writeInfoLogs(" Starting the test case "+result.getName());
	
		
	}
	
	public void onTestSuccess(ITestResult result) {	
		try {
			writeResultsToFile(result.getName(), "Pass");
			writeInfoLogs(" The test case by name "+result.getName() +" is Pass");
		} catch (IOException e)
		{		
			e.printStackTrace();
		}	
	}
	
	
	public void onTestFailure(ITestResult result) {
		try {		
			writeResultsToFile(result.getName(), "Failed");
			captureScreenShotOnFailure(result.getName());			
			writeErrorLogs(result.getThrowable());
		} catch (IOException e) {			
			e.printStackTrace();
		}	
	}
	
	public static String getAndStoreLocatorDataInToMap() throws IOException
	{
		String locator ="";
		
		File f = new File("./src/test/java/data/locatordata.xlsx");		
		FileInputStream fio = new FileInputStream(f);
		
		// Creating the object of the Work Book	
		XSSFWorkbook wb = new XSSFWorkbook(fio);
		
		// Creating the object of WOrk Sheet
		XSSFSheet ws = wb.getSheet("Sheet1");
		
		// To get the number of used rows
		int rows = ws.getLastRowNum();		
		//writeLogsToFile(rows);
		
		for(int x=1; x<=rows;x++)
		{
			
			String page = ws.getRow(x).getCell(0).getStringCellValue();
			String element = ws.getRow(x).getCell(1).getStringCellValue();
			locator = ws.getRow(x).getCell(2).getStringCellValue();
			
			locatorMap.put(page+"#"+element, locator);
		}	
		wb.close();	
		return locator;
	}
	
	
	public static String getAndStoreTestDataInToMap() throws IOException
	{
		String locator ="";
		
		File f = new File("./src/test/java/data/testdata.xlsx");		
		FileInputStream fio = new FileInputStream(f);
		
		// Creating the object of the Work Book	
		XSSFWorkbook wb = new XSSFWorkbook(fio);
		
		// Creating the object of WOrk Sheet
		XSSFSheet ws = wb.getSheet("Sheet1");
		
		// To get the number of used rows
		int rows = ws.getLastRowNum();		
		//writeLogsToFile(rows);
		
		for(int x=1; x<=rows;x++)
		{
			
			String page = ws.getRow(x).getCell(0).getStringCellValue();
			String element = ws.getRow(x).getCell(1).getStringCellValue();
			locator = ws.getRow(x).getCell(2).getStringCellValue();
			
			testDataMap.put(page+"#"+element, locator);
		}	
		wb.close();	
		return locator;
	}
	
	
	
	public static String getLocatorDataFromMap(String pageName, String elementName) throws IOException
	{
		String locator = "";
		
			locator = locatorMap.get(pageName+"#"+elementName);
			
		return locator;
		
	}
	
	//************************
	
	
	public static String getTestDataFromMap(String pageName, String elementName) throws IOException
	{
		String locator = "";
		
			locator = testDataMap.get(pageName+"#"+elementName);
			
		return locator;
		
	}
		
	

}


