package customFunctions;

import java.util.concurrent.TimeUnit;

import static driver.DriverScript.OR;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import driver.DriverScript;

public class Keywords {

	public static WebDriver driver;

	public static void openBrowser(String object, String data) {
		try {
			System.setProperty("webdriver.chrome.driver", ".\\src\\main\\resources\\utils\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			driver = new ChromeDriver(options);
			Log.info("Browser is starting");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			DriverScript.result = false;
			Log.info("Exception observed in openBrowser method()"+ e.getMessage());
		}
	}

	public static void navigate(String object, String data) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(Constants.URL);
			driver.manage().window().maximize();
			Log.info("Browser is maximized");
			WebDriverWait myWaitVar = new WebDriverWait(driver, 15);
			myWaitVar.until(ExpectedConditions.elementToBeClickable(By.linkText(Constants.FX_Link)));
			Actions actions = new Actions(driver);
			WebElement mainMenu = driver.findElement(By.linkText(Constants.FX_Link));
			actions.moveToElement(mainMenu).build().perform();
			WebElement subMenu = driver.findElement(By.linkText(Constants.Currency_Link));
			actions.moveToElement(subMenu);
			actions.click().build().perform();
			Log.info("Currency Convert link is clicked");
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			Log.info("Browser navigation is complete");
			driver.switchTo().frame(Constants.Frame_Name);			
			Log.info("Focus is switched to Frame now");
		} catch (Exception e) {
			DriverScript.result = false;
			Log.info("Exception observed in navigate method()"+ e.getMessage());
		}
	}

	public static void selectConvert(String object, String data) {
		try {

			Select fromCurrency = new Select(driver.findElement(By.id(OR.getProperty(object))));
			fromCurrency.selectByVisibleText(data);
			Log.info("Currency is selected from drop down");
		} catch (Exception e) {
			DriverScript.result = false;
			Log.info("Exception observed in selectConvert method()"+ e.getMessage());
		}
	}

	public static void enterAmount(String object, String data) {
		try {
			driver.findElement(By.id(OR.getProperty(object))).sendKeys(data);
			Log.info("Amount is entered into the textbox");
		} catch (Exception e) {
			DriverScript.result = false;
			Log.info("Exception observed in enterAmount method()"+ e.getMessage());
		}
	}

	public static void clickButton(String object, String data) {
		try {
			driver.findElement(By.id(OR.getProperty(object))).click();
			Log.info("Convert button is clicked");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		} catch (Exception e) {
			DriverScript.result = false;
			Log.info("Exception observed in clickButton method()"+ e.getMessage());
		}
	}

	public static void verifyError(String object, String data) {
		try {
			String text = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			if (text.contentEquals("Please enter the amount you want to convert.")) {
				Log.info("Error message is displayed on the UI");
				DriverScript.result = true;
			} else {
				Log.info("Error message is NOT displayed on the UI");
				DriverScript.result = false;
			}
		} catch (Exception e) {
			DriverScript.result = false;
			Log.info("Exception observed in verifyError method()"+ e.getMessage());
		}
	}

	public static void verifyMessage(String object, String data) {
		try {
			if (driver.findElement(By.xpath(OR.getProperty(object))).isDisplayed()) {
				Log.info("Conversion rate is displayed on the UI for the entered currency");
				DriverScript.result = true;
			} else {
				Log.info("Conversion rate is NOT displayed on the UI for the entered currency");
				DriverScript.result = false;
			}
		} catch (Exception e) {
			DriverScript.result = false;
			Log.info("Exception observed in verifyMessage method()"+ e.getMessage());
		}
	}

	public static void closeBrowser(String object, String data) {
		try {
			driver.quit();
			Log.info("Browser is closed");
		} catch (Exception e) {
			DriverScript.result = false;
			Log.info("Exception observed in closeBrowser method()"+ e.getMessage());
		}
	}

}
