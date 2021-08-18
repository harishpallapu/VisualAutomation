package com.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;

public class VisualTestingDemo {
	 private EyesRunner runner;
	  private Eyes eyes;
	  private static BatchInfo batch;
	  private WebDriver driver;

	  @BeforeClass
	  public static void setBatch() {
	    // Must be before ALL tests (at Class-level)
	    batch = new BatchInfo("Demo batch");
	  }
	  @BeforeMethod
	  public void beforeEach() {
	    // Initialize the Runner for your test.
	    runner = new ClassicRunner();

	    // Initialize the eyes SDK
	    eyes = new Eyes(runner);

	    // Set your personal Applitols API Key from your environment variables.
	    eyes.setApiKey("cPW8zWHAtJY9SsZE9991DUMuuEV109Ntzj0kSD337xMoY110");

	    // set batch name
	    eyes.setBatch(batch);
	    
	    System.setProperty("webdriver.chrome.driver","drivers/chromedriver.exe");
	    // Use Chrome browser
	    driver = new ChromeDriver();

	  }

	  @Test
	  public void basicTest() throws Exception {
	    // Set AUT's name, test name and viewport size (width X height)
	    // We have set it to 800 x 600 to accommodate various screens. Feel free to
	    // change it.
	    eyes.open(driver, "Shpping Cart web", "Base line execution");

	    // Navigate the browser to the "ACME" demo app.
	   // driver.get("https://demo.applitools.com");
	    
	    driver.manage().window().maximize();
	    driver.get("http://14.99.175.107:17670/SpringMVCAnnotationShoppingCart/");
		eyes.checkWindow("Home Page");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[text()='Login']")).click();
		driver.findElement(By.xpath("//*[@name='userName']")).sendKeys("admin");
		driver.findElement(By.xpath("//*[@name='password']")).sendKeys("123");
		driver.findElement(By.xpath("//*[@value='Login']")).click();
		Thread.sleep(2000);
		eyes.checkWindow("Logged In Page");
		driver.findElement(By.xpath("//a[normalize-space() = 'Product List']")).click();
		Thread.sleep(2000);
		eyes.checkWindow("Product List Page");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[normalize-space() = 'Order List']")).click();
		eyes.checkWindow("Order List");
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
		eyes.checkWindow("Check Logout Page");
	   // End the test.
	    eyes.closeAsync();
	  }

	  @AfterMethod
	  public void afterEach() {
	    // Close the browser.
	    driver.quit();

	    // If the test was aborted before eyes.close was called, ends the test as
	    // aborted.
	    eyes.abortIfNotClosed();

	    // Wait and collect all test results
	    TestResultsSummary allTestResults = runner.getAllTestResults();

	    // Print results
	    System.out.println(allTestResults);
	  }
	}