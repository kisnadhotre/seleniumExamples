package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class FileDownloadTest {

    WebDriver driver;
    File targetFolder;

    @BeforeTest()
    public void setUp() {

        /*targetFolder = new File(System.getProperty("user.home"),"Downloads");
        Map<String, Object> prefs= new HashMap<>();
        prefs.put("download.default_directory", targetFolder);

        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(chromeOptions);*/

        //System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
        WebDriverManager.firefoxdriver().setup();
        targetFolder = new File(".");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addPreference("browser.download.dir",targetFolder.getAbsolutePath());
        firefoxOptions.addPreference("browser.download.folderList", 1);
        driver = new FirefoxDriver(firefoxOptions);


    }

    @Test
    public void downloadTest() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/download.html");
        driver.findElement(By.xpath("//a[@download='webdrivermanager.png']")).click();
        ConditionFactory await = Awaitility.await().atMost(Duration.ofSeconds(5));
        File logo = new File(targetFolder, "webdrivermanager.png");
        await.until( () -> logo.exists() );

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }






}
