package example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class newTest
{
    private WebDriver driver;
    @Test
    public void testEasy()
    {
        driver.get("http://demo.guru99.com/test/guru99home/");
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Demo Guru99 Page"));
    }
    @BeforeTest
    public void beforeTest()
    {
        WebDriverManager.firefoxdriver().setup();
         driver = new FirefoxDriver();
    }
    @AfterTest
    public void afterTest()
    {
        driver.quit();
    }
}
