package RegressionTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class DataProviderwithExcel_deleteUser {

    private static WebDriver driver;
    WebDriverWait wait;

    public String baseUrl = "http://localhost/wordpress/";
    public WebElement webtable;

    @DataProvider(name = "Admin")
    public static Object[][] credentials() {
        // The number of times data is repeated, test will be executed the same no. of times
        // Here it will execute two times
        return new Object[][]{{"LoginasAdmin", "admin", "!NCS2019"}
        };
    }
    @DataProvider(name = "delteUser")
    public static Object[][] deleteUser() {
        Object[][] testObjArray_deleteUser = ExcelUtils.getTableArray("C:\\Users\\ZTE_testing\\IdeaProjects\\WebDriverTest\\AddUserTCs.xlsx","deleteUser");
        return (testObjArray_deleteUser);
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


    @Test(dataProvider = "Admin")
    public void  A001_loginAdmin(String sTC, String sUsername, String sPassword) {

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

    @Test(dataProvider = "deleteUser")
    public void B_0001_SearchUser(String sTC, String suserName) throws Exception {
        Boolean b;
        //driver.findElement(By.xpath("//li[@id='menu-users']/a/div[3]")).click();
        //driver.findElement(By.className("page-title-action")).click();

        driver.get("http://localhost/wordpress/wp-admin/users.php?s=nase&action=-1&new_role&paged=1&action2=-1&new_role2");
        driver.findElement(By.id("user-search-input")).clear();
        driver.findElement(By.id("user-search-input")).sendKeys(suserName);
        //driver.findElement(By.id("user-search-input")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("search-submit")).click();

        // wait for the page for the result
        // possible result:
        // - no user has been found: Message pops up: "Es wurden keinen Benutzer gefunden"
        // - Benutzer wurden gefunden und angezeigt.


        b = B_0002_SelectUser();
        if(b == Boolean.TRUE) {
            if (suserName.equals("admin")) {
                B_0004_DeleteSelectedAdmin();
                WebDriverWait w = new WebDriverWait(driver, 20);
                driver.findElement(By.xpath("//*[@name='updateusers']"));
                w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@name='updateusers']")));
                System.out.printf("Admin kann nicht gelöscht werden %s ", driver.findElement(By.xpath("//*[@name='updateusers']")).getText());
                Assert.assertEquals(driver.findElement(By.xpath("//*[@name='updateusers']")).getText().contains("nicht gelöscht"), true);
            } else {
                B_0003_DeleteSelectedUser();
                System.out.println("Nase");
                WebDriverWait w = new WebDriverWait(driver, 20);
                w.until(ExpectedConditions.presenceOfElementLocated(By.id("message")));
                System.out.printf("War die Löschung erfolgreich: %s ", driver.findElement(By.id("message")).getText());
                Assert.assertEquals(driver.findElement(By.id("message")).getText().contains("Benutzer gelöscht"), true);

            }
        }
        else {
            Assert.assertTrue(b, "User" + suserName + " could not be found. ");
        }
    }

    public Boolean B_0002_SelectUser() throws Exception {

        //driver.findElement(By.id("user_313")).click();
        //driver.findElement(By.id("bulk-action-selector-bottom")).click();
        //new Select(driver.findElement(By.id("bulk-action-selector-bottom"))).selectByVisibleText("Löschen");
        //driver.findElement(By.id("bulk-action-selector-bottom")).click();
        //driver.findElement(By.id("doaction2")).click();
        //driver.findElement(By.id("submit")).click();

        String s1;
        Boolean b;
        //driver.findElement(By.xpath("//li[@id='menu-users']/a/div[3]")).click();
        System.out.println("Get Table Infos");

        int i_rowCount = driver.findElements(By.xpath("//table/tbody/tr")).size();
        int i_ColCount = driver.findElements(By.xpath("//table/tbody/tr[1]/td")).size();

        System.out.println();
        System.out.println("Tabelle aus geben:");

        WebElement w = driver.findElement(By.xpath("//table/tbody"));
        System.out.printf("Entry found %s \n",  w.getText());

        if(w.getText().contains("keinen Benutzer gefunden")) {
            return Boolean.FALSE;
        }

        else {
            for (int i = 1; i <= i_rowCount; i++) {
                System.out.printf("Rowcount: %d ", i);
                //System.out.printf("\n\n Checkbox: %s \n \n ", driver.findElement(By.xpath("//table/tbody/tr["+ i +"]/th/input")).getAttribute("id"));
                System.out.printf(" UserID: %s \t ", driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/th/label")).getAttribute("for"));
                System.out.printf(" Username. %s \n ", driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/th/label")).getText());
                s1 = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/th/label")).getAttribute("for");
                if (s1.contentEquals("user_1xx")) {
                } else {
                    driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/th/input")).click();
                }
            }
            return Boolean.TRUE;
        }
    }

    public void B_0003_DeleteSelectedUser() throws Exception {
        //driver.findElement(By.id("user_313")).click();
        driver.findElement(By.id("bulk-action-selector-bottom")).click();
        new Select(driver.findElement(By.id("bulk-action-selector-bottom"))).selectByVisibleText("Löschen");
        driver.findElement(By.id("bulk-action-selector-bottom")).click();
        driver.findElement(By.id("doaction2")).click();
        if ( !driver.findElements(By.id("delete_option0")).isEmpty() ) {
            driver.findElement(By.id("delete_option0")).click();
        }
        driver.findElement(By.id("submit")).click();
    }

    public void B_0004_DeleteSelectedAdmin() throws Exception {
        //driver.findElement(By.id("user_313")).click();
        driver.findElement(By.id("bulk-action-selector-bottom")).click();
        new Select(driver.findElement(By.id("bulk-action-selector-bottom"))).selectByVisibleText("Löschen");

       // <input type="radio" id="delete_option0" name="delete_option" value="delete">
        driver.findElement(By.id("bulk-action-selector-bottom")).click();
        driver.findElement(By.id("doaction2")).click();

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
