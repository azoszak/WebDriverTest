package example;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

public class newTest2
{
    private WebDriver driver;
    WebDriverWait wait;

    public String baseUrl = "http://www.wiedamann.net/ncs-testing/";
    public WebElement webtable;

    String username;
    String password;
    String role;

    String email;
    String first_name;
    String last_name;


    @BeforeTest
    public void beforeTest()
    {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(baseUrl);
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("NCS-Testing – Testautomatisierung vom Feinsten ;-)"));

    }

    @Test
    public void A001_verifyHomepageTitle() {

        String expectedTitle = "NCS-Testing";
        String actualTitle = driver.getTitle();
        // System.out.printf("exspectedTitle: %s \n", expectedTitle);
        System.out.printf("actualTitle: %s \n", actualTitle);
        Assert.assertEquals(driver.getTitle().contains(expectedTitle), true);
    }

    @Test
    public void A002_loginAdmin() {

        username = "admin";
        password = "!NCS2019";


        System.out.println("Login user as admin");
        driver.findElement(By.linkText("Anmelden")).click();

        driver.findElement(By.id("user_pass")).clear();
        driver.findElement(By.id("user_pass")).sendKeys(password);

        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys(username);
        //driver.findElement(By.id("user_pass")).clear();
        //driver.findElement(By.id("user_pass")).sendKeys(password);

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.id("wp-submit")).click();



        WebDriverWait wait = new WebDriverWait(driver,2);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("wp-admin-bar-my-account")));

        Assert.assertEquals(driver.findElement(By.id("wp-admin-bar-my-account")).getText().contains("Willkommen"), true);
        System.out.printf("Anmeldung: %n  %s", driver.findElement(By.id("wp-admin-bar-my-account")).getText());
        System.out.println();



    }

    @Test
    public void A003_SelectAllUsers() {
        driver.findElement(By.xpath("//li[@id='menu-users']/a/div[3]")).click();
        System.out.println("Get Table Infos");

        int i_rowCount  = driver.findElements(By.xpath("//table/tbody/tr")).size();
        int i_ColCount  = driver.findElements(By.xpath("//table/tbody/tr[1]/td")).size();

        System.out.println();
        System.out.println("Tabelle aus geben:");

        for(int i=1; i<=i_rowCount;i++){
            System.out.printf("Rowcount: %d ", i);
            //System.out.printf("\n\n Checkbox: %s \n \n ", driver.findElement(By.xpath("//table/tbody/tr["+ i +"]/th/input")).getAttribute("id"));
            System.out.printf(" UserID: %s \t ", driver.findElement(By.xpath("//table/tbody/tr["+ i +"]/th/label")).getAttribute("for") ) ;
            System.out.printf(" Username. %s \n ", driver.findElement(By.xpath("//table/tbody/tr["+ i +"]/th/label")).getText() ) ;
            driver.findElement(By.xpath("//table/tbody/tr["+ i +"]/th/input")).click();
            //for(int j=1; j<=i_ColCount;j++){
            //   System.out.print(driver.findElement(By.xpath("//table/tbody/tr[" + i +"]/td[" + j + "]")).getText() + "\t");
            //}
            //System.out.println();
        }

       // List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
       // System.out.println("No of rows  : " +rows.size());
       // List<WebElement>  cols = driver.findElements(By.xpath("//table/tbody/tr[1]/td"));
       // System.out.println("No of cols are : " + cols.size());

    }


    @Test
    public void A004_ChangeRoleforSelectedUser() {

        new Select(driver.findElement(By.id("new_role2"))).selectByVisibleText("Redakteur");
        driver.findElement(By.id("new_role2")).click();
        driver.findElement(By.id("changeit2")).click();

    }

/*
    @Test
    public void A005_logoutAdmin() {
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

*/
}

