package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager; //log4j
import org.apache.logging.log4j.Logger; //log4j


public class BaseTest {
    public static WebDriver driver;
    public Logger logger; //log4j logging
    public Properties properties;

    @BeforeClass(groups = {"Sanity", "Regression", "DataDriven", "Master"})
    @Parameters({"os", "browser"})
    public void setup(String os, String br) throws IOException {  //os parameter will be used when we implement Selenium Grid
        logger = LogManager.getLogger(this.getClass()); //log4j: dynamically takes the current subclass which extends this base class

        //load config.properties file
        FileReader file= new FileReader("./src/test/resources/config.properties");
        properties = new Properties();
        properties.load(file);

        //when our execution environment is remote(grid)
        if (properties.getProperty("execution_env").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            //set OS
            if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);
            } else if (os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN11);
            } else if (os.equalsIgnoreCase("linux")) {
                    capabilities.setPlatform(Platform.LINUX);
            } else {
                System.out.println("No matching OS...");
                return;
            }

            //set browser
            switch (br.toLowerCase()) {
                case "chrome" : capabilities.setBrowserName("chrome");
                    break;
                case "firefox" : capabilities.setBrowserName("firefox");
                    break;
                case "edge" : capabilities.setBrowserName("MicrosoftEdge");
                    break;
                default : System.out.println("No matching browser...");
                    return;
            }

            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        }

        //when our execution environment is local
        if (properties.getProperty("execution_env").equalsIgnoreCase("local")) {

            switch (br.toLowerCase()) { //browser coming from masterTest.xml file
                case "chrome": driver = new ChromeDriver();
                    break;
                case "safari": driver = new SafariDriver();
                    break;
                case "firefox": driver = new FirefoxDriver();
                    break;
                default:
                    System.out.println("Invalid browser name...");
                    return;
            }
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(properties.getProperty("appURL"));
        driver.manage().window().maximize();
    }

    public String captureScreen(String testName) {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + testName + "_" + timeStamp;
        File targetFile = new File(targetFilePath);

        sourceFile.renameTo(targetFile);

        return targetFilePath;
    }

    @AfterClass(groups = {"Sanity", "Regression", "DataDriven", "Master"})
    public void tearDown() {

        driver.quit();

    }

}
