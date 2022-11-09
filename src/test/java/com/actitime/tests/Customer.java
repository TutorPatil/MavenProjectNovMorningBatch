package com.actitime.tests;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.actitime.base.BaseClass;
import com.actitime.utils.CommonUtils;

public class Customer extends BaseClass {
	
	//@Test(groups = { "smoke", "customer","customer_001" })
	@Test
	public static void customer_001() throws InterruptedException, IOException
	{
				
		CommonUtils.loginToActitime();
		writeLogsToFile("Chcking for the logout link post login");
		boolean logoutLink = false;
		logoutLink = driver.findElement(By.xpath(getLocatorDataFromMap("Home", "Logout_link"))).isDisplayed();
		Assert.assertTrue(logoutLink, "The logout link not seen customer_001 Failed");
				
		
	}

}
