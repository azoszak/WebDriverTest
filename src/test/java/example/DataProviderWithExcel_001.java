package example;

import RegressionTest.ExcelUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderWithExcel_001 {

    private static WebDriver driver;
    WebDriverWait wait;

    public String baseUrl = "http://localhost/wordpress/";
    public WebElement webtable;

    @DataProvider(name = "addUser")
    public static Object[][] addUser() {
        Object[][] testObjArray_addUser = ExcelUtils.getTableArray("C:\\Users\\ZTE_testing\\IdeaProjects\\WebDriverTest\\AddUserTCs.xlsx","User");
        return (testObjArray_addUser);
    }


    @BeforeTest
    public void beforeTest()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(baseUrl);
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("NCS-Testing"));
        driver.findElement(By.linkText("Anmelden")).click();
    }

    @Test()
    public void  A001_loginAdmin() {

        String sTC = "LoginasAdmin";
        String sUsername = "admin";
        String sPassword = "!NCS2019";

        System.out.printf("\n Testcase: %s Login user as : %s  Passwd: %s \n",sTC, sUsername, sPassword);
        //driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys(sUsername);
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException ie){
        }
        //driver.findElement(By.id("user_pass")).clear();
        driver.findElement(By.id("user_pass")).sendKeys(sPassword);
        driver.findElement(By.id("wp-submit")).click();
        WebDriverWait wait = new WebDriverWait(driver,2);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("wp-admin-bar-my-account")));

        Assert.assertEquals(driver.findElement(By.id("wp-admin-bar-my-account")).getText().contains("admin"),true);
        System.out.printf("Anmeldung: %n  %s", driver.findElement(By.id("wp-admin-bar-my-account")).getText());
        System.out.println();
    }

    @Test(dataProvider = "addUser")
    public void A002_adduser(String sTC, String suserLogin, String sEmail,
                        String sfirstName, String slastName, String sPassword,
                        String ssendUserNotifiaction, String sRole ) {

        System.out.printf("\n Testcase: %s Login user as : %s  Passwd: %s \n",sTC, suserLogin, sPassword);

        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//*[@class = 'wp-menu-image dashicons-before dashicons-admin-users']"))).perform();
        WebDriverWait w = new WebDriverWait(driver,20);
        w.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Neu hinzufügen")));
        driver.findElement(By.linkText("Neu hinzufügen")).click();

        /* fill formular with some basic  entries */
        driver.findElement(By.id("user_login")).click();
        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys(suserLogin);
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys(sEmail);
        driver.findElement(By.id("first_name")).click();
        driver.findElement(By.id("first_name")).clear();
        driver.findElement(By.id("first_name")).sendKeys(sfirstName);
        driver.findElement(By.id("last_name")).click();
        driver.findElement(By.id("last_name")).clear();
        driver.findElement(By.id("last_name")).sendKeys(slastName);
        driver.findElement(By.xpath("(//button[@type='button'])[3]")).click();
        driver.findElement(By.id("pass1-text")).click();
        driver.findElement(By.id("pass1-text")).clear();
        driver.findElement(By.id("pass1-text")).sendKeys(sPassword.replaceAll("^(.)(.*)", "$1"));
        driver.findElement(By.id("pass1-text")).sendKeys(sPassword.replaceAll("^(.)(.*)", "$2"));
        if ( driver.findElement(By.className("pw-checkbox")).isDisplayed() ){
            driver.findElement(By.className("pw-checkbox")).click();
        }

        WebElement ckb_BenutzerBenachrichtigen =  driver.findElement(By.id("send_user_notification"));
        System.out.printf("Benutzer Benachrichtigungen: Häckchen gesetzt?  %s \n", ckb_BenutzerBenachrichtigen.isEnabled() );
        if (ckb_BenutzerBenachrichtigen.isEnabled()) {

            ckb_BenutzerBenachrichtigen.click();
        }
        new Select(driver.findElement(By.id("role"))).selectByVisibleText(sRole);
        //driver.findElement(By.xpath((//*[normalize-space(text()) and normalize-space(.)='Rolle'])[1]/following::option[2])).click();
        driver.findElement(By.id("createusersub")).click();
        WebDriverWait w1 = new WebDriverWait(driver,20);
        System.out.println("Add user as admin");
        w1.until(ExpectedConditions.presenceOfElementLocated(By.id("message")));
        Assert.assertEquals(driver.findElement(By.id("message")).getText().contains("Neuer Benutzer erstellt"), true);
    }


    @Test
    public void Z1000_logoutAdmin() {
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


