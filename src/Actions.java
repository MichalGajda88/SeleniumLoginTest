import org.openqa.selenium.*;
import org.testng.Assert;

public class Actions {

    public void clearField(WebElement webElement){
        webElement.sendKeys(Keys.CONTROL + "a");
        webElement.sendKeys(Keys.BACK_SPACE);
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
