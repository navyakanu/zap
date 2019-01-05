package SampleFunctionalTest;


import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import zap.ZapScan;

public class Sample {

@Test
public void testGoogleSearch(){
	ZapScan zs = new ZapScan();
	
	DesiredCapabilities capabilities = DesiredCapabilities.chrome();

	Proxy proxy = new Proxy();
	proxy.setHttpProxy("127.0.0.1" + ":" + "8080");
	capabilities.setCapability("proxy", proxy);
	
	ChromeOptions options = new ChromeOptions();
	System.setProperty("webdriver.chrome.driver", "path-to/chromedriver");

	ChromeDriver driver = new ChromeDriver(options);
    driver.get("http://www.google.com/xhtml");
    WebElement searchBox = driver.findElement(By.name("q"));
	searchBox.sendKeys("ChromeDriver");
	searchBox.submit();
	  try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  // Let the user actually see something!
	  zs.spider();
	  driver.quit();
	}
}
