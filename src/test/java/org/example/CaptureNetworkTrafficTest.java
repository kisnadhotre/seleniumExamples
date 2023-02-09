package org.example;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CaptureNetworkTrafficTest {
    WebDriver driver;
    WebDriverWait wait;
    Faker faker;

    @BeforeTest()
    public void setUp() {
        //System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
        //System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void fakerTest() throws Exception {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/data-types.html");
        faker = new Faker();
        driver.findElement(By.name("first-name")).sendKeys(faker.name().firstName());
        driver.findElement(By.name("last-name")).sendKeys(faker.name().lastName());
        driver.findElement(By.name("e-mail")).sendKeys(faker.internet().emailAddress());
        driver.findElement(By.xpath("//*[contains(text(),'Submit')]")).click();
        Thread.sleep(3000);

    }

    @Test
    public void printGoogleProductNames() throws Exception {
        driver.get("https://www.google.com");
        driver.findElement(By.xpath("//a[@class='gb_e']")).click();
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@role='presentation' and @name='app']")));
        //driver.findElement(By.xpath("//li[@class='j1ei8c']")) );

        List<WebElement> products = driver.findElements(By.xpath("//li[@class='j1ei8c']/a/span"));
        for(WebElement product: products) {
            System.out.println(product.getText());
        }



    }

    @Test
    public void printWindowHandles() throws Exception {

        driver.get("https://www.google.com");

        ((JavascriptExecutor) driver).executeScript("window.open('https://www.facebook.com', '_blank');");

        ((JavascriptExecutor) driver).executeScript("window.open('https://www.facebook.com', '_blank');");

        wait.until( ExpectedConditions.numberOfWindowsToBe(2) );
//        wait.until( driver-> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete") );
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            System.out.println(driver.getTitle());

        }

    }


    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
