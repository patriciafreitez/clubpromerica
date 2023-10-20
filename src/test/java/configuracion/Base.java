package configuracion;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import report.ReportManager;

public class Base {

	 private ReportManager reportManager;
	 WebDriver driver;

	 public Base(WebDriver driver) {
		this.driver = driver;
		reportManager = new ReportManager();
	 }
	 
	 public WebDriver chromeDriverConnection() {
			System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			return driver;
	 }
	 
	 public WebElement fiElement(By locator) {
		 reportManager.registerEvent("find element " + locator.toString(), "ok");
		 return driver.findElement(locator);
	 }
	 public List<WebElement> findElement(By locator){
		 reportManager.registerEvent("find elements " + locator.toString(), "ok");
		 return driver.findElements(locator);
	 }
	 
	 public String getValue (By locator) {
		 String value = driver.findElement(locator).getAttribute("value");
		 reportManager.registerEvent("get value " + value + " from locator " + locator.toString(), "ok");
		 
		 return value;
	 }
	 
	 public String getText (By locator) {
		 String text = driver.findElement(locator).getText();
		 reportManager.registerEvent("get text " + text + " from locator " + locator.toString(), "ok");
		 return text;
	 }
	 public String getTtitle () {
		 String title = driver.getTitle();
		 reportManager.registerEvent("get title page " + title, "ok");
		 return title;
	 }

	 public void sleep (int segundos) {
		  try {
			reportManager.registerEvent("sleep in seconds  " + segundos, "ok");
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public void type(String inputText, By locator) {
		 reportManager.registerEvent("Write " + inputText + " to " + locator.toString(), "ok");
		 driver.findElement(locator).sendKeys(inputText);
	 }   
	 
	 public void typeEnter(String inputText, By locator) {
		 reportManager.registerEvent("Write " + inputText + " and press enter, to " + locator.toString(), "ok");
		 driver.findElement(locator).sendKeys(inputText + Keys.ENTER);
	 }
	 
	 public void enter(By locator) {
		 reportManager.registerEvent("Enter to " + locator.toString(), "ok");
		 driver.findElement(locator).sendKeys(Keys.ENTER);
	 }
	 public void donwAndEnter(By locator) {
		 reportManager.registerEvent("Down and Enter to " + locator.toString(), "ok");
		 driver.findElement(locator).sendKeys(Keys.DOWN);
		 driver.findElement(locator).sendKeys(Keys.ENTER);
	 }

	 public void click(By locator) {
		 reportManager.registerEvent("Click to " + locator.toString(), "ok");
		 driver.findElement(locator).click();
	 }
	 public Boolean isDisplayed(By locator) {
		 try {
			 Boolean result = driver.findElement(locator).isDisplayed();
			 reportManager.registerEvent(locator.toString() + " is visible", "ok");
			 return result;
		 } catch (org.openqa.selenium.NoSuchElementException e) {
			 reportManager.registerEvent(locator.toString() + " is visible", "error");
			 return false;
		 }
	 }
	 public void visit(String url) {
		 reportManager.registerEvent("visit " + url, "ok");
		 driver.get(url);
	 }
	 
	public String getCurrentWindow() {
		return driver.getWindowHandle();
	}
	
	public void changeTap(String originalWindow) {
		 reportManager.registerEvent("Switch to tap", "ok");
		//Wait for the new window or tab
		sleep(2);

		//Loop through until we find a new window handle
		for (String windowHandle : driver.getWindowHandles()) {
		    if(!originalWindow.contentEquals(windowHandle)) {
		        driver.switchTo().window(windowHandle);
		        break;
		    }
		}
	}
	
	public void setToDriver(String originalWindow) {
		driver.switchTo().window(originalWindow);
	}
	
	public void hover(By locator) {
		 reportManager.registerEvent("Hover to " + locator.toString(), "ok");

		WebElement hover = fiElement(locator);
		Actions action = new Actions(driver);
		action.moveToElement(hover).perform();
	}
	 
	public void fWait(final By locator) { 
		 reportManager.registerEvent("Wait element " + locator.toString(), "ok");

		Wait<WebDriver> fwait = new FluentWait<WebDriver>(driver)
				 .withTimeout(Duration.ofSeconds(10))
				 .pollingEvery(Duration.ofMillis(300))
				 .ignoring(NoSuchElementException.class);
		 
		fwait.until(new Function<WebDriver, WebElement>() {
			 public WebElement apply(WebDriver driver) {
				 return driver.findElement(locator);
			 }
		});
	}
	 
	public void close() {
		 reportManager.registerEvent("Close current windonw", "ok");
		driver.close();
	}
	
	public Boolean screenShot() {
		try {
			File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			// Now you can do whatever you need to do with it, for example copy somewhere

			String scrShotDir = ReportManager.TEST_PATH;
			new File(scrShotDir + "/screenshots").mkdirs();

			String path = scrShotDir + "/screenshots/" + source.getName();
			reportManager.registerEventWithScreenShot("Take the screenshot " + source.getName(), "ok","screenshots/" + source.getName());
			
			FileUtils.copyFile(source, new File(path));
	        
	        return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}

