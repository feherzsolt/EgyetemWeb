package com.logmein.testing;

import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.Properties;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppTest 
{
	private String baseUrl ;
	
	@Before
	public void Before() {
		Properties p = new Properties();
    	InputStream inputStream = ClassLoader.getSystemResourceAsStream("setting.properties");
    	try {
    		p.load(inputStream);
    	} catch(Exception e) {
    		Assert.fail();
    	}
    	
    	baseUrl = p.getProperty("url");
	}

    @Test
	public void a(){
		Assert.assertEquals(1,1);
	}
    
    @Test
    public void b() throws Exception {
    	
    	runInBrowser( webDriver -> {
    		
	    	webDriver.navigate().to("https://google.com");
	    	
	    	webDriver.findElement(By.name("q")).sendKeys("Informatikai TanszékCsoport Szeged");
	    	webDriver.findElement(By.name("btnK")).submit();
	    	
	    	webDriver.findElements(By.cssSelector("div.srg div.g")).get(0).findElement(By.tagName("a")).click();
    	
	    	new WebDriverWait(webDriver, 10).until(ExpectedConditions.urlContains("inf."));
    	});
    }
    
    @Test
    public void amazonPrice() {
    	runInBrowser(webDriver -> {
    		webDriver.navigate().to("https://google.com");
    		webDriver.findElement(By.name("q")).sendKeys("AMZN");
    		webDriver.findElement(By.name("btnK")).submit();
    		
    		String amznPrice = webDriver.findElement(By.tagName("g-card-section")).findElements(By.cssSelector("div.gsrt span")).get(0).getText();
    		
    		System.out.println(amznPrice);
    	});
    }
    
    private void runInBrowser(Consumer<WebDriver> action) {
    	
    	String geckoDriverPath = FileSystems.getDefault().getPath("src/test/resources/geckodriver.exe").toString();
		System.setProperty("webdriver.gecko.driver", geckoDriverPath);
   	
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.setCapability("marionette", false);
		
    	WebDriver webDriver = new FirefoxDriver(firefoxOptions);
    	
    	try {
    		
    		action.accept(webDriver);
	    	
    	} finally {
    		webDriver.close();
    	}
    }
}
