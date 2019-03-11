import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class sample1 {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void beforeTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost/wordpress/");
        driver.findElement(By.linkText("Anmelden")).click();
    }

    @Test
    public void A001_loginAdmin() {
        //System.out.println("Login user as admin");
        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys("admin");
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException ie){
        }
        driver.findElement(By.id("user_pass")).clear();
        driver.findElement(By.id("user_pass")).sendKeys("!NCS2019");
        driver.findElement(By.id("wp-submit")).click();
        WebDriverWait wait = new WebDriverWait(driver,2);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("wp-admin-bar-my-account")));
        Assert.assertEquals(driver.findElement(By.id("wp-admin-bar-my-account")).getText().contains("admin"),true);
    }

    @Test
    public void  A002_addUser() {

        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//*[@class = 'wp-menu-image dashicons-before dashicons-admin-users']"))).perform();
        WebDriverWait w = new WebDriverWait(driver,20);
        w.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Neu hinzufügen")));
        driver.findElement(By.linkText("Neu hinzufügen")).click();
        /* fill formular with some basic  entries */
        driver.findElement(By.id("user_login")).sendKeys("aznase1234");
        driver.findElement(By.id("email")).sendKeys("aznase1234@gibtesnicht.de");
        driver.findElement(By.id("first_name")).sendKeys("az");
        driver.findElement(By.id("last_name")).sendKeys("nase1234");
        WebElement ckb_BenutzerBenachrichtigen =  driver.findElement(By.id("send_user_notification"));
        System.out.printf("Benutzer Benachrichtigungen: Häckchen gesetzt?  %s \n", ckb_BenutzerBenachrichtigen.isEnabled() );
        if (ckb_BenutzerBenachrichtigen.isEnabled()) {
            ckb_BenutzerBenachrichtigen.click();
        }
        new Select(driver.findElement(By.id("role"))).selectByVisibleText("Mitarbeiter");
        driver.findElement(By.id("createusersub")).click();
        WebDriverWait w2 = new WebDriverWait(driver,20);
        w2.until(ExpectedConditions.presenceOfElementLocated(By.id("message")));
        Assert.assertEquals(driver.findElement(By.id("message")).getText().contains("Neuer Benutzer erstellt"), true);
    }

    @Test
    public void A003_logoutAdmin() {
        Actions action = new Actions(driver);
        WebElement mainMenue = driver.findElement(By.id("wp-admin-bar-my-account"));
        action.moveToElement(mainMenue).perform();
        WebDriverWait w = new WebDriverWait(driver,20);
        w.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Abmelden")));
        driver.findElement(By.linkText("Abmelden")).click();
        w.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("login")));
        Assert.assertEquals(driver.findElement(By.id("login")).getText().contains("Du hast dich erfolgreich abgemeldet"), true);
    }

    @AfterTest
    public void afterTest()
    {
        driver.quit();
    }
}


