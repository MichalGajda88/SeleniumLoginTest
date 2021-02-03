import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class LoginTest {

    Actions actions = new Actions();

    WebDriver driver;
    String testPage = "https://www.poczta.interia.pl",
            correctName = "test9999@interia.pl", correctPassword = "Test1234",
            incorrectName = "mietek123", incorrectPassword = "ble1232";

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser", "adblock"})
    public void setup(String browser, String adblock) {


        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "E:\\AUTOMATYZACJA\\selenium\\chromedriver.exe");
            driver = new ChromeDriver();

            if (adblock.equalsIgnoreCase("on")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("load-extension=C:\\Users\\Pajdzior\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\cfhdojbkjhnklbpkdaibdccddilifddb\\3.10.2_0");
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                ChromeDriver driver = new ChromeDriver(capabilities);
            }

        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "E:\\AUTOMATYZACJA\\selenium\\geckodriver.exe");
            driver = new FirefoxDriver();

            if (adblock.equalsIgnoreCase("on")) {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("load-extension=C:\\Users\\Pajdzior\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\szblmzky.default-release-1611000105252\\extensions");
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                FirefoxDriver driver = new FirefoxDriver(capabilities);
            }
        }

        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get(testPage);

        if (browser.equalsIgnoreCase("chrome")) {
            WebElement detailsButton = driver.findElement(By.id("details-button"));
            detailsButton.click();
            driver.findElement(By.id("proceed-link")).click();
            driver.findElement(By.xpath("//button[@class='rodo-popup-agree']")).click();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void openPage() {
        if (!driver.getCurrentUrl().equals(testPage)) {
            driver.get(testPage);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void logout() {
        if (driver.getCurrentUrl().contains("/folder/")) {
            WebElement userButton = driver.findElement(By.xpath("//div[@Title='Wyloguj']"));
            userButton.click();
            WebElement logoutButton = driver.findElement(By.xpath("//a[@class='account-info__logout button']"));
            logoutButton.click();
        }

    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    @Test(priority = 1, groups = {"positive"})
    public void tc1CorrectData() {
        WebElement inputName = driver.findElement(By.id("email"));
        inputName.sendKeys(correctName);
        WebElement inputPassword = driver.findElement(By.id("password"));
        inputPassword.sendKeys(correctPassword);
        WebElement button = driver.findElement(By.xpath("//button[@class='btn']"));
        button.click();
        WebElement mailBox = driver.findElement(By.xpath("//section[@class='msglist-container']"));
        actions.checkErrorPossitive(mailBox);
    }

    @Test(priority = 2, groups = {"negative"})
    public void tc2IncorrectPassword() {
        WebElement inputName = driver.findElement(By.id("email"));
        actions.clearField(driver, 10, inputName, correctName);
        WebElement inputPassword = driver.findElement(By.id("password"));
        actions.clearField(driver, 10, inputPassword, incorrectPassword);
        WebElement button = driver.findElement(By.xpath("//button[@class='btn']"));
        button.click();
        WebElement errorMessage = driver.findElement(By.xpath("//span[@class='form__error']"));
        actions.checkErrorNegative(errorMessage);
    }

    @Test(priority = 3, groups = {"negative"})
    public void tc3IncorrectName() {
        WebElement inputName = driver.findElement(By.id("email"));
        actions.clearField(driver, 10, inputName, incorrectName);
        WebElement inputPassword = driver.findElement(By.id("password"));
        actions.clearField(driver, 10, inputPassword, correctPassword);
        WebElement button = driver.findElement(By.xpath("//button[@class='btn']"));
        button.click();
        WebElement errorMessage = driver.findElement(By.xpath("//span[@class='form__error']"));
        actions.checkErrorNegative(errorMessage);
    }

    @Test(priority = 4, groups = {"negative"})
    public void tc4IncorrectData() {
        WebElement inputName = driver.findElement(By.id("email"));
        actions.clearField(driver, 10, inputName, incorrectName);
        WebElement inputPassword = driver.findElement(By.id("password"));
        actions.clearField(driver, 10, inputPassword, incorrectPassword);
        WebElement button = driver.findElement(By.xpath("//button[@class='btn']"));
        button.click();
        WebElement errorMessage = driver.findElement(By.xpath("//span[@class='form__error']"));
        actions.checkErrorNegative(errorMessage);
    }

    @Test(priority = 5, groups = {"negative"})
    public void tc5NoLogin() {
        WebElement inputName = driver.findElement(By.id("email"));
        actions.clearField(driver, 10, inputName, "");
        WebElement inputPassword = driver.findElement(By.id("password"));
        actions.clearField(driver, 10, inputPassword, incorrectPassword);
        WebElement button = driver.findElement(By.xpath("//button[@class='btn']"));
        button.click();
        WebElement errorMessage = driver.findElement(By.xpath("//ul[@class='account-input__error']"));
        actions.checkErrorNegative(errorMessage);
    }

    @Test(priority = 6, groups = {"negative"})
    public void tc6NoPassword() {
        WebElement inputName = driver.findElement(By.id("email"));
        actions.clearField(driver, 10, inputName, correctName);
        WebElement button = driver.findElement(By.xpath("//button[@class='btn']"));
        button.click();
        WebElement errorMessage = driver.findElement(By.xpath("//ul[@class='account-input__error']"));
        actions.checkErrorNegative(errorMessage);
    }

    @Test(priority = 7, groups = {"negative"})
    public void tc7NoData() {
        WebElement inputName = driver.findElement(By.id("email"));
        actions.clearField(driver, 10, inputName, "");
        WebElement button = driver.findElement(By.xpath("//button[@class='btn']"));
        button.click();
        WebElement errorMessage = driver.findElement(By.xpath("//ul[@class='account-input__error']"));
        actions.checkErrorNegative(errorMessage);
    }
}
