package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {


	@DataProvider(name="LoginData")
	public String[][] getAllData() throws IOException
	{
		String path=System.getProperty("user.dir")+"/testData/Opencart_LoginData.xlsx";
		XLUtility xl=new XLUtility(path);
	
		int totalRows=xl.getRowCount("Sheet1");
		int totalColumns=xl.getCellCount("Sheet1",1);
		
		String[][] loginData=new String[totalRows][totalColumns];
		
		for(int i=1;i<=totalRows;i++)
		{		
			for(int j=0;j<totalColumns;j++)
			{
				loginData[i-1][j]= xl.getCellData("Sheet1",i, j);  
			}
		}
	
		return loginData;
	}
	
	@DataProvider(name="UserNames") //we do not need this, we can create more data provider as needed
	public String[] getUserNames() throws IOException
	{
		String path=System.getProperty("user.dir")+"//testData//Userdata.xlsx";
		XLUtility xl=new XLUtility(path);
	
		int rownum=xl.getRowCount("Sheet1");
			
		String[] data=new String[rownum];
		
		for(int i=1;i<=rownum;i++)
		{		
			data[i-1]= xl.getCellData("Sheet1",i, 1);  
			
		}
	
		return data;
	}
	
}
