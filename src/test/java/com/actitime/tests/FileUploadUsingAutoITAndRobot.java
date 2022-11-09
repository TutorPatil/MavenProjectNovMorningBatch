package com.actitime.tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.actitime.base.BaseClass;

public class FileUploadUsingAutoITAndRobot extends BaseClass{
	
	
	
	//@Test
	public static void fileUploadUsingSendKeys() throws IOException, InterruptedException
	{			
		driver.get("https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_fileupload_get");
		
		// Uploading using the absolute path
		driver.switchTo().frame("iframeResult");
		//driver.findElement(By.id("myFile")).sendKeys("D:\\sample.txt");
		
		// getting the absolute path from relative path and then uploading using the absolute path...
		File f = new File("./src/test/java/utilities/Sample.txt");
		String filePath = f.getAbsolutePath();
		driver.findElement(By.id("myFile")).sendKeys(filePath);
		Thread.sleep(4000);
		
	}
	
	@Test
	public static void fileUploadUsingRobotClass() throws IOException, InterruptedException, AWTException
	{			
		driver.get("https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_fileupload_get");
		driver.switchTo().frame("iframeResult");
		
		Actions action = new Actions(driver);		
		action.click(driver.findElement(By.id("myFile"))).build().perform();		
		Thread.sleep(4000);
		
		//driver.findElement(By.id("myFile")).click();
		
		
		//Copy the path of the file to the clipboard
		StringSelection stringSelection = new StringSelection("D:\\sample.txt");
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	    		
	    // Create the object of Robot class
	    Robot robot = new Robot();
	    
	    
	    robot.keyPress(KeyEvent.VK_CONTROL);
		Thread.sleep(3000);		
		robot.keyPress(KeyEvent.VK_V);
		Thread.sleep(3000);		
		robot.keyRelease(KeyEvent.VK_CONTROL);		
		robot.keyPress(KeyEvent.VK_TAB);
		Thread.sleep(3000);		
		robot.keyPress(KeyEvent.VK_TAB);
		Thread.sleep(3000);		
		//robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);		
		robot.keyPress(KeyEvent.VK_ENTER);	
		
		Thread.sleep(4000);
		
	}
	
	@Test
	public static void fileUploadUsingAUtoIT() throws IOException, InterruptedException
	{						
				
		driver.get("https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_fileupload_get");
		
		driver.switchTo().frame("iframeResult");
		
		Actions action = new Actions(driver);		
		action.click(driver.findElement(By.id("myFile"))).build().perform();	
		
		Thread.sleep(4000);	
		
		// FIle Upload usingAutoIT Relative Path
		File f = new File("./src/test/java/utilities/fileupload.exe");
		String filePath = f.getAbsolutePath();
		
		// To kill anyTask using the command
		//Runtime.getRuntime().exec("taskkill /IM "firefox.exe" /F");
		
		// To run any commands ( DOS or Linux terminal )
		Runtime.getRuntime().exec(filePath);
		
		// File upload using autoIT Absolute Path
		//Runtime.getRuntime().exec("D:\\FileUpload.exe");
		
		
		
		Thread.sleep(4000);
		
	}
	
	
	
	

}
