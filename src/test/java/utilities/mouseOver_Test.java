package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class mouseOver_Test {

    public static void A001_mouseover(WebDriver driver, String s_xpath, String s_PresenceofElement) {

        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath(s_xpath))).perform();
        WebDriverWait w = new WebDriverWait(driver, 20);
        w.until(ExpectedConditions.presenceOfElementLocated(By.linkText(s_PresenceofElement)));
        driver.findElement(By.linkText(s_PresenceofElement)).click();
    }

}
