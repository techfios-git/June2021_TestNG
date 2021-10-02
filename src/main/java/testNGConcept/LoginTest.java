package testNGConcept;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {

	WebDriver driver;
	String browser;
	String url;

	// Login Data
	String USER_NAME = "demo@techfios.com";
	String PASSWORD = "abc123";

	// TestData
	String FULL_NAME = "TestNG";
	String COMPANY_NAME = "Techfios";
	String EMAIL = "abc234@techfios.com";
	String PHONE_NUMBER = "35252523";
	String COUNTRY = "Angola";

	// storing element with By class
	By userNameField = By.xpath("//input[@id='username']");
	By passWordField = By.xpath("//*[@id=\"password\"]");
	By signInButtonField = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By dashboardHeaderField = By.xpath("//h2[contains(text(), 'Dashboard')]");
	By customerButtonField = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By addCustomerButtonField = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By fullNameField = By.xpath("//*[@id=\"account\"]");
	By companyNameField = By.xpath("//*[@id=\"cid\"]");
	By emailField = By.xpath("//*[@id=\"email\"]");
	By phoneField = By.xpath("//*[@id=\"phone\"]");
	By countryField = By.xpath("//*[@id=\"country\"]");
	By saveButtonField = By.xpath("//*[@id=\"submit\"]");

	@BeforeClass
	public void readConfig() {
		// FileReader //InputStream //Buffered //Scanner

		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);
			url = prop.getProperty("url");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(url);
	}

	// @Test(priority=1)
	public void loginTest() {

		driver.findElement(userNameField).sendKeys(USER_NAME);
		driver.findElement(passWordField).sendKeys(PASSWORD);
		driver.findElement(signInButtonField).click();

		Assert.assertEquals(driver.findElement(dashboardHeaderField).getText(), "Dashboard", "Wronng Page!!");

	}

	@Test(priority = 2)
	public void addCustomerTest() {

		driver.findElement(userNameField).sendKeys(USER_NAME);
		driver.findElement(passWordField).sendKeys(PASSWORD);
		driver.findElement(signInButtonField).click();

		Assert.assertEquals(driver.findElement(dashboardHeaderField).getText(), "Dashboard", "Wronng Page!!");

		driver.findElement(customerButtonField).click();
		driver.findElement(addCustomerButtonField).click();

		
		;

		driver.findElement(fullNameField).sendKeys(FULL_NAME + generateRandomNO(999));
	

		selectFromDropdown(companyNameField, COMPANY_NAME);

		driver.findElement(emailField).sendKeys(generateRandomNO(9999) + EMAIL);
		driver.findElement(phoneField).sendKeys(PHONE_NUMBER + generateRandomNO(999));
		
		selectFromDropdown(countryField, COUNTRY);
		

//		driver.findElement(saveButtonField).click();

	}

	public int generateRandomNO(int boundaryNumber) {
		Random rnd = new Random();
		int generatedNo = rnd.nextInt(boundaryNumber);
		return generatedNo;
		
	}

	public void selectFromDropdown(By element, String visibleText) {
		Select sel = new Select(driver.findElement(element));
		sel.selectByVisibleText(visibleText);
	}

	// @AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
