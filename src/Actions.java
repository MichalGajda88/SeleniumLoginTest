import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Actions {

    public void clearField(WebDriver driver, int timeout, WebElement field, String value){
        new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(field));
        field.sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
        field.sendKeys(value);
    }

    public void checkErrorPossitive (WebElement webElement){
        Assert.assertTrue(webElement.isDisplayed(), "User is not logged in");
        System.out.println("User is logged in");
    }

    public void checkErrorNegative (WebElement webElement){
        Assert.assertTrue(webElement.isDisplayed(), "User is logged in");
        System.out.println("User is not logged in");
    }
}
