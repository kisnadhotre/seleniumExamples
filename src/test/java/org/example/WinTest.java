package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class WinTest {
    DesktopOptions options = new DesktopOptions();
    WiniumDriverService service;
    WiniumDriver driver;

    @BeforeTest
    public void setUp() {
        options.setApplicationPath("C:\\Windows\\System32\\calc.exe");
    }

    @Test
    public void MyTest() throws InterruptedException, MalformedURLException {

        driver = new WiniumDriver(new URL("http://localhost:9999"), options);

        Thread.sleep(5000);
        driver.findElement(By.id("num9Button")).click();

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }


}
