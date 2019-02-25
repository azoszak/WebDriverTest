package RegressionTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class startBrowser {

    private WebDriver driver;
    WebDriverWait wait;
    public String baseUrl = "http://localhost/wordpress/";


    @BeforeTest()
    public void beforeTest()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(baseUrl);
        //String title = driver.getTitle();
        //Assert.assertTrue(title.contains("NCS-Testing"));
    }

    @Test()
    public void A001_verifyHomepageTitle() {

        String expectedTitle = "NCS-Testing";
        String actualTitle = driver.getTitle();
        // System.out.printf("exspectedTitle: %s \n", expectedTitle);
        System.out.printf("actualTitle: %s \n", actualTitle);
        Assert.assertEquals(driver.getTitle().contains(expectedTitle), true);
    }

    @AfterTest
    public void afterTest()
    {
        driver.quit();
    }
}
