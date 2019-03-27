package utilities;

//import javafx.scene.web.WebEngineBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class loginAdmin001 {

    public static void A001_loginAdmin(WebDriver driver, String sUsername, String sPassword) {

        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys(sUsername);
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException ie){
        }
        System.out.printf("Username: %s Password: %s \n", sUsername, sPassword );
        //driver.findElement(By.id("user_pass")).clear();
        driver.findElement(By.id("user_pass")).sendKeys(sPassword);
        driver.findElement(By.id("wp-submit")).click();
        WebDriverWait wait = new WebDriverWait(driver,2);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("wp-admin-bar-my-account")));
        Assert.assertEquals(driver.findElement(By.id("wp-admin-bar-my-account")).getText().contains(sUsername),true);
        System.out.printf("Anmeldung: %n  %s", driver.findElement(By.id("wp-admin-bar-my-account")).getText());
        System.out.println();

    }


    public static void Z1000_logout(WebDriver driver) {

      mouseOver_Test.A001_mouseover( driver,"//*[@id=\"wp-admin-bar-my-account\"]",
                "Abmelden");
        WebDriverWait w = new WebDriverWait(driver,20);
        w.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("login")));
        Assert.assertEquals(driver.findElement(By.id("login")).getText().contains("Du hast dich erfolgreich abgemeldet"), true);
        //System.out.printf("Abmeldung: %n  %s", driver.findElement(By.id("login")).getText());

    }
    public static void test() {

        System.out.println("dies ist ein Test \n");
    }
}
