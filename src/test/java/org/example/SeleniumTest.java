package org.example;

import junit.framework.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class SeleniumTest {
    WebDriver driver;

    @BeforeTest ()
    public void setUp() {
//        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver");
        System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary("/logs/khk/test1/FIREFOX_BROWSER/firefox/firefox");
        firefoxOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        firefoxOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        firefoxOptions.addArguments("--headless", "--enable-javascript", "--enable-touch-drag-drop","--disable-gpu","--no-sandbox");
        driver = new FirefoxDriver(firefoxOptions);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        chromeOptions.addArguments("--headless", "--enable-javascript", "--enable-touch-drag-drop","--disable-gpu","--no-sandbox");
        driver = new ChromeDriver(chromeOptions);


    }

    @Test
    public void TestOne() {
        System.out.println("Starting...");
        driver.get("https://www.google.com");
        System.out.println( "title: " + driver.getTitle() );
    }

    @Test
    public void testTwo() throws Exception {

        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_CONTROL);
        String text = "Hello";
        StringSelection stringSelection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,stringSelection);
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_V);
        r.keyRelease(KeyEvent.VK_V);
        r.keyRelease(KeyEvent.VK_CONTROL);

    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }
}
