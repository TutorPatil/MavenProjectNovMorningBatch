package com.actitime.dataproviders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DataProviders {
	
	
	@DataProvider(name = "logindata")
	public static Object[][] supplyLoginData()
	{
		
		Object[][] login = new String[2][2];
		
		login[0][0] = "admin";		login[0][1] = "manager";
		login[1][0] = "admin";		login[1][1] = "manager";		
		return login;
		
	}
	
	
	@DataProvider(name = "logindatafromexcel")
	public static Object[][] supplyLoginDataFromExcel() throws IOException
	{		

		File f = new File("./data/testdata.xlsx");		
		FileInputStream fio = new FileInputStream(f);
		
		// Creating the object of the Work Book	
		XSSFWorkbook wb = new XSSFWorkbook(fio);
		
		// Creating the object of WOrk Sheet
		XSSFSheet ws = wb.getSheet("Sheet2");
		
		// To get the number of used rows	
		int rows = ws.getLastRowNum();		
	
		Object[][] obj = new String[rows+1][2];
		
		for(int x=1; x<=rows;x++)
		{
			
			String page = ws.getRow(x).getCell(0).getStringCellValue();
			String element = ws.getRow(x).getCell(1).getStringCellValue();
			
			obj[x][0] = page;
			obj[x][1] = element;			
			
		}	
		wb.close();	
		return obj;
	}		
	
	@DataProvider(name = "customerdatafromexcel")
	public static Object[][] supplyCustomerDataFromExcel() throws IOException
	{		

		File f = new File("./data/testdata.xlsx");		
		FileInputStream fio = new FileInputStream(f);
		
		// Creating the object of the Work Book	
		XSSFWorkbook wb = new XSSFWorkbook(fio);
		
		// Creating the object of WOrk Sheet
		XSSFSheet ws = wb.getSheet("Sheet3");
		
		// To get the number of used rows	
		int rows = ws.getLastRowNum();		
	
		Object[][] obj = new String[rows+1][4];
		
		for(int x=1; x<=rows;x++)
		{
			
			String fn = ws.getRow(x).getCell(0).getStringCellValue();
			String ln = ws.getRow(x).getCell(1).getStringCellValue();
			String mobile = ws.getRow(x).getCell(1).getStringCellValue();
			String email = ws.getRow(x).getCell(1).getStringCellValue();
			
			obj[x][0] = fn;
			obj[x][1] = ln;
			obj[x][2] = mobile;
			obj[x][3] = email;
			
		}	
		wb.close();	
		return obj;
	}		
	


}
