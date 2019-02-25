package example;

//import com.beust.jcommander.Parameters;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.Parameters;
import org.testng.annotations.DataProvider;

public class addUser_DataProviderMethods {

    private WebDriver driver;
    WebDriverWait wait;

    public String baseUrl = "http://www.wiedamann.net/ncs-testing/";
    public WebElement webtable;

    String username;
    String password;

    @DataProvider(name = "Authentication")

    public static Object[][] credentials() {

        // The number of times data is repeated, test will be executed the same no. of times

        // Here it will execute two times

        return new Object[][]{{"testuser_1", "Test@123"}, {"testuser_2", "Test@1234"}, {"ZZ", "ZZ"}};
    }

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
    @Parameters({"sUsername", "sPassword"})
    public void A002_loginUser(String sUsername, String sPassword) {

        System.out.printf("Login user as : %s \n", sUsername);
        driver.findElement(By.linkText("Anmelden")).click();

        driver.findElement(By.id("user_pass")).clear();
        driver.findElement(By.id("user_pass")).sendKeys(sPassword);

        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys(sUsername);
        //driver.findElement(By.id("user_pass")).clear();
        //driver.findElement(By.id("user_pass")).sendKeys(password);

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.id("wp-submit")).click();

        WebDriverWait wait = new WebDriverWait(driver,2);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("wp-admin-bar-my-account")));

        Assert.assertEquals(driver.findElement(By.id("")).getText().contains("Willkommen"), true);
        System.out.printf("Anmeldung: %n  %s", driver.findElement(By.id("wp-admin-bar-my-account")).getText());
        System.out.println();
        System.out.println();

    }
}
