package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

public class setBrowser {

    private WebDriver driver;

    public setBrowser(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver setUp(String browser) {

        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.chromedriver().setup();
            return driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("ie")) {
            WebDriverManager.iedriver().setup();
            return driver = new InternetExplorerDriver();
        } else if (browser.equalsIgnoreCase("opera")) {
            WebDriverManager.operadriver().setup();
            return driver = new OperaDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            return driver = new ChromeDriver();
        }
    }
}