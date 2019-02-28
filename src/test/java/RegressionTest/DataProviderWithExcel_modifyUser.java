package RegressionTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.fail;

public class DataProviderWithExcel_modifyUser {
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

    @DataProvider(name = "addUser")
    public static Object[][] addUser() {
        Object[][] testObjArray_addUser = ExcelUtils.getTableArray("C:\\Users\\ZTE_testing\\IdeaProjects\\WebDriverTest\\AddUserTCs.xlsx","User");
        return (testObjArray_addUser);
    }

    @DataProvider(name = "changeRoleforUser")
    public static Object[][] changeRoleUser() {
        Object[][] testObjArray_modUser = ExcelUtils.getTableArray("C:\\Users\\ZTE_testing\\IdeaProjects\\WebDriverTest\\AddUserTCs.xlsx","modifyUser");
        return (testObjArray_modUser);
    }


    @Parameters("browser")
    @BeforeClass( alwaysRun = true)
    public void setUp(String browser ) throws Exception {

        if(browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        else if(browser.equalsIgnoreCase("ie")){
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        }
        else if(browser.equalsIgnoreCase("opera")){
            WebDriverManager.operadriver().setup();
            driver = new OperaDriver();
        }
        else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.get(baseUrl);
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("NCS-Testing"));
        driver.findElement(By.linkText("Anmelden")).click();
    }


    @Test()
    public void A001_loginAdmin() {
        String sUsername = "admin";
        String sPassword = "!NCS2019";
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

    @Test(dataProvider = "changeRoleforUser")
    public void A002_ChangeRoleforUser(String sTC, String sUserLogin, String sRole ) {

       System.out.printf("Modification of the user Roles has to not implemented yet \n");

    }

    @Test()
        public void A100_adminlogout() {
            Z1000_logout();
    }

    @Test(dataProvider = "userLogin")
    public void  B001_loginUser(String sTC, String sUsername, String sPassword, String firstName, String lastName) {
        System.out.printf("\n Testcase: %s Login user as : %s  Passwd: %s \n",sTC, sUsername, sPassword);
        driver.findElement(By.id("user_login")).clear();
        driver.findElement(By.id("user_login")).sendKeys(sUsername);
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException ie){
        }
        driver.findElement(By.id("user_pass")).clear();
        driver.findElement(By.id("user_pass")).sendKeys(sPassword);
        driver.findElement(By.id("wp-submit")).click();


        if(sTC.contains("Wrong")) {
            System.out.printf("check if the user or passwd is wrog \n");
           // WebDriverWait wait = new WebDriverWait(driver,2);
           // wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("login-error")));
            System.out.printf("Check Login GetText:%s \n ", driver.findElement(By.id("login_error")).getText());
            Assert.assertEquals(driver.findElement(By.id("login_error")).getText().contains("FEHLER"), true);
        }
        else {
            WebDriverWait wait = new WebDriverWait(driver,2);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("wp-admin-bar-my-account")));
            System.out.printf("Anmeldung: %n  %s", driver.findElement(By.id("wp-admin-bar-my-account")).getText());
            Assert.assertEquals(driver.findElement(By.id("wp-admin-bar-my-account")).getText().contains(lastName), true);
            modifyUser();
            Z1000_logout();
        }

    }

    //@Test
    public void modifyUser()  {

        WebElement w = driver.findElement(By.xpath("//li[@class='wp-not-current-submenu menu-top menu-icon-users menu-top-first']"));
        System.out.printf("\n Change Profile: W ist gleich: Text; %s, Tagname: %s, Class: %s  \n " , w.getText(), w.getTagName(), w.getClass());

        driver.findElement(By.xpath("//li[@class='wp-not-current-submenu menu-top menu-icon-users menu-top-first']")).click();
        driver.findElement(By.id("admin_color_light")).click();
        driver.findElement(By.id("url")).click();
        driver.findElement(By.id("url")).clear();
        driver.findElement(By.id("url")).sendKeys("www.gibtesnicht.de");
        driver.findElement(By.id("description")).click();
        driver.findElement(By.id("description")).clear();
        driver.findElement(By.id("description")).sendKeys("Ich bin ein Testobjekt");
        driver.findElement(By.id("submit")).click();


    }

    public void Z1000_logout() {
        Actions action = new Actions(driver);
        //WebElement mainMenue = driver.findElement(By.id("wp-admin-bar-my-account"));
        //WebElement m = driver.findElement(By.id("wpadminbar"));
        //System.out.printf("\n Webadmin wpadmin Element :%s \n", m.getText());
        action.moveToElement(driver.findElement(By.xpath("//a[contains(@href,'wp-admin/profile.php')]"))).perform();
        WebDriverWait w = new WebDriverWait(driver,20);
        w.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Abmelden")));
        driver.findElement(By.linkText("Abmelden")).click();
        w.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("login")));
        Assert.assertEquals(driver.findElement(By.id("login")).getText().contains("Du hast dich erfolgreich abgemeldet"), true);
        System.out.printf("\n Abmeldung: %n  %s", driver.findElement(By.id("login")).getText());

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
