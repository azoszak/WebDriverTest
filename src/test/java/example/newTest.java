package example;
import java.util.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class newTest
{
    private WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void beforeTest()
    {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get("http://www.wiedamann.net/ncs-testing/");
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("NCS-Testing – Testautomatisierung vom Feinsten ;-)"));

    }

    @Test
    public void A_verifyHomepageTitle() {

        String expectedTitle = "NCS-Testing";
        String actualTitle = driver.getTitle();
        // System.out.printf("exspectedTitle: %s \n", expectedTitle);
        System.out.printf("actualTitle: %s \n", actualTitle);
        Assert.assertEquals(driver.getTitle().contains(expectedTitle), true);
    }

    @Test
    public void B_loginAdmin() {

        //System.out.println("Login user as admin");

        driver.findElement(By.linkText("Anmelden")).click();
        driver.findElement(By.id("user_pass")).clear();
        driver.findElement(By.id("user_pass")).sendKeys("!NCS2019");
        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys("admin");
        driver.findElement(By.id("wp-submit")).click();

    }

    @Test
    public void C_addUser() {

        // System.out.println("Add user as admin");

        /* Click on button Benutzer */
        /* driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Editor'])[2]/following::div[4]")).click(); */
        driver.findElement(By.xpath("//li[@id='menu-users']/a/div[3]")).click();
        /* ClassName could be not find */
        /* driver.findElement(By.className("wp-menu-image dashicons-before dashicons-admin-users")).click(); */

        // driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Benutzer'])[4]/following::a[1]")).click();


        driver.findElement(By.className("page-title-action")).click();


        /* fill formular with some basic  entries */
        driver.findElement(By.id("user_login")).click();
        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys("mohamedNagi");
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("eng.m.nagi@gmail.com");
        driver.findElement(By.id("first_name")).click();
        driver.findElement(By.id("first_name")).clear();
        driver.findElement(By.id("first_name")).sendKeys("mohamed");
        driver.findElement(By.id("last_name")).click();
        driver.findElement(By.id("last_name")).clear();
        driver.findElement(By.id("last_name")).sendKeys("Nagi");
        WebElement ckb_BenutzerBenachrichtigen =  driver.findElement(By.id("send_user_notification"));
        System.out.printf("Benutzer Benachrichtigungen: Häckchen gesetzt?  %s \n", ckb_BenutzerBenachrichtigen.isEnabled() );
        if (ckb_BenutzerBenachrichtigen.isEnabled()) {

            ckb_BenutzerBenachrichtigen.click();
        }

        new Select(driver.findElement(By.id("role"))).selectByVisibleText("Mitarbeiter");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Rolle'])[1]/following::option[2]")).click();
        driver.findElement(By.id("createusersub")).click();

        WebDriverWait w = new WebDriverWait(driver,20);
        System.out.println("Add user as admin");
        w.until(ExpectedConditions.presenceOfElementLocated(By.id("message")));
        //System.out.printf("Message: %s", driver.findElement(By.id("message")).getText());

        /* Check if the user has been successfully created */
        Assert.assertEquals(driver.findElement(By.id("message")).getText().contains("Neuer Benutzer erstellt"), true);

        //Assert.assertEquals(driver.findElement(By.className("error")).getText().contains("Neuer Benutzer"),true);

    }

    @Test
    public void D_logoutAdmin() {
        Actions action = new Actions(driver);
        WebElement mainMenue = driver.findElement(By.id("wp-admin-bar-my-account"));
        action.moveToElement(mainMenue).perform();
        WebDriverWait w = new WebDriverWait(driver,20);
        w.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Abmelden")));
        driver.findElement(By.linkText("Abmelden")).click();

        w.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("login")));

        Assert.assertEquals(driver.findElement(By.id("login")).getText().contains("Du hast dich erfolgreich abgemeldet"), true);
        //System.out.printf("Abmeldung: %n  %s", driver.findElement(By.id("login")).getText());

    }

    @AfterTest
    public void afterTest()
    {
        driver.quit();
    }
}
