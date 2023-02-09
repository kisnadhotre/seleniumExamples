package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;

public class WiniumTest {

    DesktopOptions options = new DesktopOptions();
    WiniumDriverService service;
    WiniumDriver driver;

    @BeforeTest
    public void setUp() {

//        options.setApplicationPath("C:\\Windows\\System32\\calc.exe");
        options.setApplicationPath("C:\\Program Files (x86)\\elevate\\WirelessClient.exe");

//        service = new WiniumDriverService.Builder()
 //               .usingDriverExecutable(new File("./drivers/Winium.Desktop.Driver.exe"))
 //               .usingAnyFreePort()
 //               .withVerbose(true)
 //               .withSilent(false)
 //               .buildDesktopService();
    }


    @Test
    public void winiumTest() throws Exception {
        driver = new WiniumDriver(new URL("http://localhost:9999"), options);
        Thread.sleep(6000);
        Set<String> handles = driver.getWindowHandles();
        for(String handle : handles) {
            System.out.println( "Window: " + handle);
        }


//        Thread.sleep(10000);
//        driver.switchTo().window(driver.getWindowHandle());

//        driver.findElement(By.id("username")).sendKeys("IBM");

    }


    @AfterTest
    public void tearDown() {
        driver.quit();
    }



}
