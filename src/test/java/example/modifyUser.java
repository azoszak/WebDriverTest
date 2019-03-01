package example;

import RegressionTest.ExcelUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;


public class modifyUser {
    private WebDriver driver;
    public String baseUrl = "http://localhost/wordpress";
    public WebElement webtable;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @DataProvider(name = "userLogin")
    public static Object[][] userLogin() {
        Object[][] testObjArray = ExcelUtils.getTableArray("C:\\Users\\ZTE_testing\\IdeaProjects\\WebDriverTest\\AddUserTCs.xlsx","UserLogin");
        return (testObjArray);
    }

    @Parameters("browser")
    @BeforeClass( alwaysRun = true)
    public void setUp(String browser ) throws Exception {

        if(browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.chromedriver().setup();
            driver = new FirefoxDriver();
        }
        else if(browser.equalsIgnoreCase("ie")){
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        }
        else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.get(baseUrl);
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("NCS-Testing"));
        driver.findElement(By.linkText("Anmelden")).click();

        String sUsername = "admin";
        String sPassword = "!NCS2019";
        loginAdmin001.A001_loginAdmin(driver,sUsername,sPassword);
       // loginAdmin001.Z1000_logout(driver);

    }
        @Test()
        public void A002_ChangeRole() throws Exception {

        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//*[@class = 'wp-menu-image dashicons-before dashicons-admin-users']"))).perform();
        WebDriverWait w = new WebDriverWait(driver,20);
        w.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Alle Benutzer")));
        driver.findElement(By.linkText("Alle Benutzer")).click();
        // Benutzer auswählen

        driver.findElement(By.id("role")).click();
        new Select(driver.findElement(By.id("role"))).selectByVisibleText("Autor");
        driver.findElement(By.id("role")).click();
        driver.findElement(By.id("submit")).click();
        loginAdmin001.Z1000_logout(driver);

    }


    //@Test(dataProvider = "userLogin")
    public void  A003_loginUser(String sTC, String sUsername, String sPassword, String firstName, String lastName) {

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
        System.out.printf("Anmeldung: %n  %s", driver.findElement(By.id("wp-admin-bar-my-account")).getText());
        Assert.assertEquals(driver.findElement(By.id("wp-admin-bar-my-account")).getText().contains(lastName),true);
        // do something

        Z1000_logout();
    }

    //@Test
    public void modifyUser() throws Exception {


        driver.findElement(By.xpath("//*[normalize-space(text()) and normalize-space(.)='Erstellen'])[2]/following::div[8]")).click();
        driver.findElement(By.id("description")).click();
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys("Dies ist ein Test");
        driver.findElement(By.xpath("//*[normalize-space(text()) and normalize-space(.)='Neues Passwort'])[1]/following::button[1]")).click();
        driver.findElement(By.id("pass1-text")).click();
        driver.findElement(By.id("pass1-text")).clear();
        driver.findElement(By.id("pass1-text")).sendKeys("$_1234zz");

        if ( driver.findElement(By.className("pw-checkbox")).isDisplayed() ){
            driver.findElement(By.className("pw-checkbox")).click();
        }
        driver.findElement(By.id("submit")).click();
        driver.findElement(By.linkText("Abmelden")).click();

    }

    public void Z1000_logout() {
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

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
