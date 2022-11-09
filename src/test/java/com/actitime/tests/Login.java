package com.actitime.tests;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.actitime.base.BaseClass;
import com.actitime.utils.CommonUtils;

public class Login extends BaseClass {
	



	

		
	//@Parameters({ "username","password" })
	//@Test(dataProvider = "logindata") // to take the data from the data provider present in the same class
	//@Test(dataProvider = "logindatafromexcel",dataProviderClass = com.actitime.dataproviders.DataProviders.class)
	@Test
	public static void login_001() throws InterruptedException, IOException
	{
		CommonUtils.loginToActitime();
		writeInfoLogs("Chcking for the logout link post login");
		boolean logoutLink = false;	
		logoutLink = driver.findElement(By.xpath(getLocatorDataFromMap("Home", "Logout_link"))).isDisplayed();		
		Assert.assertTrue(logoutLink, "The logout link not seen Login_001 Failed");	
		
	}
	
	
	//@Test(dependsOnMethods = { "login_001"}) // login_002 will only run if login_001 is Passed else it will skipped...	
	//@Test(priority = 1) // Runs this test case as first priority	
	//@Test(groups = { "regression", "login","login_002" })
	
	@Test
	public static void login_002() throws InterruptedException, IOException
	{
				
		CommonUtils.loginToActitime();
		writeInfoLogs("Chcking for the logout link post login");
		boolean logoutLink = false;
		logoutLink = driver.findElement(By.xpath(getLocatorDataFromMap("Home", "Logout_link"))).isDisplayed();
		Assert.assertFalse(logoutLink, "The Logout link not seen Login_002 Failed");
		
		
	}
	
	// Soft Assertion will not stop if any assertion fails... it will show all the failed assertions at the end...
	//@Test
	public static void testNGSoftAssertionEx()
	{
		SoftAssert softAssert = new SoftAssert();		
		System.out.println("*** test case two started ***");
		// This assertion will pass
		softAssert.assertEquals("Hello", "Hello", "First soft assert Passed");
		System.out.println("hard assert success....");
		// This assertion will fail
		softAssert.assertTrue("Hello".equals("hello"), "Second soft assert failed");
		// This assertion will fail
		softAssert.assertTrue("Welcome".equals("welcomeeee"), "Third soft assert failed");
		System.out.println("*** test case two executed successfully ***");
		softAssert.assertEquals("Hello", "Hello", "First soft assert Passed");
		softAssert.assertAll();
	}
	
	@Test
	public static void login_004() throws InterruptedException, IOException
	{
				
		CommonUtils.loginToActitime();
		writeInfoLogs("Chcking for the logout link post login");
		boolean logoutLink = false;
		logoutLink = driver.findElement(By.xpath(getLocatorDataFromMap("Home", "Logout_link"))).isDisplayed();
		Assert.assertFalse(logoutLink, "The Logout link not seen Login_002 Failed");
		
		
	}


}
