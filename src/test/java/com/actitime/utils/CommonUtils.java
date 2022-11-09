package com.actitime.utils;

import java.io.IOException;

import org.openqa.selenium.By;

import com.actitime.base.BaseClass;

public class CommonUtils extends BaseClass {
	
	
	
	
	
	public static void loginToActitime(String userName, String password) throws InterruptedException, IOException
	{
		driver.findElement(By.xpath(getLocatorDataFromMap("Login", "UserName_TextBox"))).sendKeys(userName);
		driver.findElement(By.xpath(getLocatorDataFromMap("Login", "Password_TextBox"))).sendKeys(password);
		driver.findElement(By.xpath(getLocatorDataFromMap("Login", "Login_Button"))).click();		
		Thread.sleep(5000);
	}
	
	public static void loginToActitime() throws InterruptedException, IOException
	{
		driver.findElement(By.xpath(getLocatorDataFromMap("Login", "UserName_TextBox"))).sendKeys(getTestDataFromMap("Login", "UserName_TextBox"));
		driver.findElement(By.xpath(getLocatorDataFromMap("Login", "Password_TextBox"))).sendKeys(getTestDataFromMap("Login", "Password_TextBox"));
		driver.findElement(By.xpath(getLocatorDataFromMap("Login", "Login_Button"))).click();		
		Thread.sleep(5000);
	}
	
	public static void selectModule(String moduleName)
	{
		String locator = "//div[text()='--TEXTREPLACE--']";		
		String xpath = locator.replace("--TEXTREPLACE--", moduleName);		
		driver.findElement(By.xpath(xpath)).click();
		
		
	}

}
