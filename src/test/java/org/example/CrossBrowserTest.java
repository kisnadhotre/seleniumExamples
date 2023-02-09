package org.example;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.apache.tools.ant.taskdefs.Java;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;


public class CrossBrowserTest {

    WebDriver driver;


    @BeforeTest
    @Parameters("browser")
    public void setup(@Optional ("chrome") String browser) throws Exception {
        //Check if parameter passed from TestNG is 'firefox'
        if (browser.equalsIgnoreCase("firefox")) {
            //create firefox instance
            System.setProperty("webdriver.firefox.driver", ".\\geckodriver");
            driver = new FirefoxDriver();
        }
        //Check if parameter passed as 'chrome'
        else if (browser.equalsIgnoreCase("chrome")) {
            //System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
            WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
            ChromeOptions options = new ChromeOptions();

            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable( LogType.PERFORMANCE, Level.ALL );
            options.setCapability( "goog:loggingPrefs", logPrefs );
            driver = new ChromeDriver(options);
        }
        //Check if parameter passed as 'Edge'
        else if (browser.equalsIgnoreCase("Edge")) {
            //set path to Edge driver.exe
            System.setProperty("webdriver.edge.driver", ".\\MicrosoftWebDriver.exe");
            //create Edge instance
            driver = new EdgeDriver();
        } else {
            //If no browser passed throw exception
            throw new Exception("Browser is not correct");
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

    @Test (enabled = false)
    public void brokenLinks() throws InterruptedException {

        driver.get("http://demo.guru99.com/seo/page-1.html");
        List<WebElement> webElementList = driver.findElements(By.tagName("a"));

        Iterator<WebElement> webElementIterator = webElementList.listIterator();
        String url;
        HttpURLConnection httpURLConnection;
        while(webElementIterator.hasNext()) {
            url = webElementIterator.next().getAttribute("href");
            try {
                httpURLConnection = (HttpURLConnection)(new URL(url).openConnection());
                httpURLConnection.setRequestMethod("HEAD");
                httpURLConnection.connect();

                if (httpURLConnection.getResponseCode() == 200) {
                    System.out.println(url + " : a valid link");
                } else {
                    System.out.println(url + " : broken link");
                }

            } catch(Exception e) {

            }

        }
    }



    @Test (enabled = false)
    public void handleWindows() {
        driver.get("http://demo.guru99.com/popup.php");

        String mainHandle = driver.getWindowHandle();

        driver.findElement(By.xpath("//*[contains(@href, \"popup.php\")]")).click();

        Set<String> windowHandles = driver.getWindowHandles();
        Iterator<String> iterator = windowHandles.iterator();
        while (iterator.hasNext()) {
            String child = iterator.next();
            if (! mainHandle.equalsIgnoreCase(child)) {
                driver.switchTo().window(child);

                driver.findElement(By.name("emailid")).sendKeys("abc@gmail.com");
                driver.close();
            }

        }
        driver.switchTo().window(mainHandle);

    }


    @Test
    public void browserLogs() {
        driver.get("https://www.google.com");
        List<LogEntry> entries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();

        System.out.println(entries.size() + " " + LogType.PERFORMANCE + " log entries found");
        for (LogEntry entry : entries) {
            System.out.println(" " + entry.getLevel() + " " + entry.getMessage());
        }
    }

    @Test(enabled = false)
    public void dynamicTable() {
        driver.get("http://demo.guru99.com/test/web-table-element.php");
        List  col = driver.findElements(By.xpath(".//*[@id='leftcontainer']/table/thead/tr/th"));
        List  rows = driver.findElements(By.xpath(".//*[@id='leftcontainer']/table/tbody/tr"));

        for (int i=1; i< rows.size(); i++) {
            String temp = driver.findElement(By.xpath(".//*[@id='leftcontainer']/table/tbody/tr[" + i + "]/td[1]")).getText();
            System.out.println(temp);
        }
    }

    @Test
    public void anotherDynamicTable() {
        driver.get("https://www.espncricinfo.com/series/icc-world-test-championship-2019-2021-1195334/points-table-standings");
        List col = driver.findElements(By.xpath("//*[@class='table-responsive']/table/thead/tr/th"));
        for (int i=0; i<col.size(); i++) {

            System.out.println();
        }
    }

    @Test
    public void scrollPage() throws  Exception {

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

        driver.get("http://demo.guru99.com/test/guru99home/");
        WebElement email = driver.findElement(By.xpath("//input[@placeholder='Enter Email']"));
        javascriptExecutor.executeScript("arguments[0].scrollIntoView();", email);

        WebElement emailwait = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(email));
        email.sendKeys("abc@test.com");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

    }

    @Test
    public void dropDown() {
        driver.get("https://demo.guru99.com/test/newtours/register.php");
        Select country = new Select(driver.findElement(By.xpath("//select[@name='country']")));
        country.selectByValue("INDIA");
        System.out.println ( country.isMultiple() );
        driver.findElement(By.xpath("//input[@type='submit']")).click();

    }

    @Test
    public void checkBox() {
        driver.get("https://demo.guru99.com/test/radio.html");
        WebElement radio1 = driver.findElement(By.xpath("//input[@type='radio' and @value='Option 1']"));
        if (radio1.isSelected()) {
            System.out.println("Its already selected");
        } else {
            radio1.click();
        }
    }

    @Test
    public void uploadDownload() {
        driver.get("https://demo.guru99.com/test/upload/");
        driver.findElement(By.xpath("//input[@type='file']"));

    }

    @Test
    public void alert() throws Exception {
        driver.get("https://demo.guru99.com/test/delete_customer.php");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//input[@name='cusid']")).sendKeys("123");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        driver.switchTo().alert().dismiss();
        Thread.sleep(3000);

        driver.findElement(By.xpath("//input[@type='submit']")).click();
        driver.switchTo().alert().accept();
        Thread.sleep(3000);


    }

    @Test
    public void dragAndDrop() throws Exception {
        driver.get("https://demo.guru99.com/test/drag_drop.html");
        WebElement from = driver.findElement(By.xpath("//li[@data-id='2']"));
        WebElement to = driver.findElement(By.xpath("//ol[@id='amt7']/li"));
        Actions actions = new Actions(driver);
        actions.dragAndDrop(from, to).build().perform();
        Thread.sleep(5000);

    }

    @Test
    public void cookieManage() {
        driver.get("https://demo.guru99.com/test/cookie/selenium_aut.php");
        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("test");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("test");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Set<Cookie>  cookies =      driver.manage().getCookies();
        for(Cookie cookie: cookies) {
            System.out.println(cookie);
        }
    }

    @Test
    public void toolTip() {
        driver.get("https://demo.guru99.com/test/tooltip.html");
        driver.manage().window().maximize();
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

        WebElement download = driver.findElement(By.xpath("//a[@id='download_now']"));
        javascriptExecutor.executeScript("arguments[0].scrollIntoView();", download );

        Actions actions = new Actions(driver);
        actions.clickAndHold().moveToElement(download).build().perform();

        WebElement tooltipElem = driver.findElement(By.xpath("//div[@class='box']/div/a"));
        System.out.println(tooltipElem.getText());


    }


}