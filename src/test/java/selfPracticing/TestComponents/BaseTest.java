package selfPracticing.TestComponents;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import selfPracticing.pages.LandingPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
public  WebDriver driver;

public LandingPage landingPage;
public Properties property;
    public WebDriver driverInitialization() throws IOException {
        property = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//selfPracticing//resources//PropertiesFile.properties");
        property.load(fis);
        String browserType = System.getProperty("browser") != null ? System.getProperty("browser") : property.getProperty("browser");

        if (browserType.contains("chrome"))
        {
            ChromeOptions chromeOptions= new ChromeOptions();
           // WebDriverManager.chromedriver().setup();  >> Selenium manager is used
            if (browserType.contains("headless"))
            chromeOptions.addArguments("headless");
            driver = new ChromeDriver(chromeOptions);
        }
        else if (browserType.contains("firefox"))
        {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
        //    WebDriverManager.firefoxdriver().setup(); >> Selenium manager is used
            if (browserType.contains("headless"))
            firefoxOptions.addArguments("--headless");
            driver = new FirefoxDriver(firefoxOptions);
        }
        else if (browserType.contains("edge"))
        {
            EdgeOptions edgeOptions= new EdgeOptions();
        //    WebDriverManager.edgedriver().setup(); >> Selenium manager is used
            if (browserType.contains("headless"))
                edgeOptions.addArguments("headless");
                driver = new EdgeDriver(edgeOptions);
            //The upcoming lines for running with selenium grid > after intializing hub and node
            // 1 - cd to the selenium server.jar file
            // 2- java -jar selenium-server-4.16.1.jar hub >> for hub
            // 3- java -jar selenium-server-4.16.1.jar node --detect-drivers true >> For node
            // comment line 61
//            DesiredCapabilities capabilities = new DesiredCapabilities() ;
//            capabilities.setCapability(CapabilityType.BROWSER_NAME,"firefox");
//            driver = new RemoteWebDriver(new URL("http://192.168.178.93:4444"), capabilities );
        }
        driver.manage().window().setSize(new Dimension(14440,900));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();

        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        driver= driverInitialization();
        landingPage=new LandingPage(driver);
        landingPage.gotToLandingPage();
        return landingPage;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown()
    {
        driver.quit();
    }

    public String getJsonData(String key) throws IOException {
        File jsonFile =new File(System.getProperty("user.dir") +"//src//main//java//selfPracticing//resources//testDataManipulation//testData.json");
        String value = JsonPath.read(jsonFile,key);
        return value;
    }

    public String getScreenshot(String testCaseName , WebDriver driver) throws IOException {
        String destinationPath = System.getProperty("user.dir")+"//Reports//"+testCaseName+".png";
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(destinationPath);
        FileUtils.copyFile(sourceFile,destinationFile);
        return destinationPath;
    }
}
