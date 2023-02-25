package com.listenerss.qa.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriver_Event_Listeners {

	@Test 
	public void test_WebDriver_Event_Listeners() throws IOException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
		
		My_Event_Handler eventsListeners = new My_Event_Handler();
		
		eventDriver.register(eventsListeners);
		
		eventDriver.get("https://www.facebook.com/login");
		eventDriver.get("https://www.amazon.in/");
		WebElement searchBox = eventDriver.findElement(By.id("twotabsearchtextbox"));
		searchBox.sendKeys("mobile");
		eventDriver.findElement(By.id("nav-search-submit-button")).click();
		eventDriver.navigate().refresh();
		String text = eventDriver.findElement(By.id("glow-ingress-line2")).getText();
		System.out.println(text);
		File screenshotFile = ((TakesScreenshot) eventDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFile, new File("screenshots\\screenshot1.jpg"));
		eventDriver.findElement(By.linkText("Amazon miniTV")).click();
		eventDriver.navigate().back();
		eventDriver.navigate().forward();
		
		try {
		eventDriver.findElement(By.id("kfr"));
		}catch(Exception e) {
			System.out.println("Exception occured");
		}
		eventDriver.unregister(eventsListeners);
		eventDriver.quit();		
	}
}
