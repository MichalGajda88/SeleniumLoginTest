import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class LoginTest {

    WebDriver driver;
    String testPage = "https://www.gmail.com", errorPage = "https://poczta.interia.pl/logowanie/?b=-1",
            correctName = "test9999@interia.pl", correctPassword = "Test1234", incorrectName = "mietek123",
            incorrectPassword = "ble1232";

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "E:\\AUTOMATYZACJA\\selenium\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "E:\\AUTOMATYZACJA\\selenium\\geckodriver.exe");
            driver = new FirefoxDriver();
        }


        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get(testPage);
        driver.findElement(By.id("details-button")).click();
        driver.findElement(By.id("proceed-link")).click();
        driver.findElement(By.xpath("//button[@class='rodo-popup-agree']")).click();

    }

    @BeforeMethod(alwaysRun = true)
    public void openPage() {
        if (!driver.getCurrentUrl().equals(testPage)) {
            driver.get(testPage);
        }

    }

    @AfterMethod(alwaysRun = true)
    public void logout() {
        if (!driver.getCurrentUrl().equals(errorPage)) {
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
        Assert.assertTrue(mailBox.isDisplayed(), "User is not logged in");
        System.out.println("User is logged in");
//        WebElement closeDialogButton = driver.findElement(By.xpath("//div[@class='dialog__close icon icon-close']"));
//        closeDialogButton.click();
    }

    @Test(priority = 2, groups = {"negative"})
    public void tc2IncorrectPassword() {
        WebElement inputName = driver.findElement(By.id("email"));
        inputName.sendKeys(Keys.CONTROL + "a");
        inputName.sendKeys(Keys.BACK_SPACE);
        inputName.sendKeys(correctName);
        WebElement inputPassword = driver.findElement(By.id("password"));
        inputPassword.sendKeys(incorrectPassword);
        WebElement button = driver.findElement(By.xpath("//button[@class='btn']"));
        button.click();
        WebElement errorMessage = driver.findElement(By.xpath("//span[@class='form__error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "User is logged in");
        System.out.println("User is not logged in");
//        WebElement closeDialogButton = driver.findElement(By.xpath("//div[@class='dialog__close icon icon-close']"));
//        closeDialogButton.click();
    }

    @Test(priority = 3, groups = {"negative"})
    public void tc3IncorrectName() {
        WebElement inputName = driver.findElement(By.id("email"));
        inputName.sendKeys(Keys.CONTROL + "a");
        inputName.sendKeys(Keys.BACK_SPACE);
        inputName.sendKeys(incorrectName);
        WebElement inputPassword = driver.findElement(By.id("password"));
        inputPassword.sendKeys(correctPassword);
        WebElement button = driver.findElement(By.xpath("//button[@class='btn']"));
        button.click();
        WebElement errorMessage = driver.findElement(By.xpath("//span[@class='form__error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "User is logged in");
        System.out.println("User is not logged in");
//        WebElement closeDialogButton = driver.findElement(By.xpath("//div[@class='dialog__close icon icon-close']"));
//        closeDialogButton.click();
    }

    @Test(priority = 4, groups = {"negative"})
    public void tc4IncorrectData() {
        WebElement inputName = driver.findElement(By.id("email"));
        inputName.sendKeys(Keys.CONTROL + "a");
        inputName.sendKeys(Keys.BACK_SPACE);
        inputName.sendKeys(incorrectName);
        WebElement inputPassword = driver.findElement(By.id("password"));
        inputPassword.sendKeys(incorrectPassword);
        WebElement button = driver.findElement(By.xpath("//button[@class='btn']"));
        button.click();
        WebElement errorMessage = driver.findElement(By.xpath("//span[@class='form__error']"));
        Assert.assertTrue(errorMessage.isDisplayed(), "User is logged in");
        System.out.println("User is not logged in");
//        WebElement closeDialogButton = driver.findElement(By.xpath("//div[@class='dialog__close icon icon-close']"));
//        closeDialogButton.click();
    }
}
