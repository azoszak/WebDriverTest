package example;

import RegressionTest.ExcelUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;


public class modifyUser {
    private  WebDriver driver;
    public setBrowser browserWeb = new setBrowser(driver);
    public String baseUrl = "http://localhost/wordpress";
    public WebElement webtable;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @DataProvider(name = "userLogin")
    public static Object[][] userLogin() {
        Object[][] testObjArray = ExcelUtils.getTableArray("C:\\Users\\ZTE_testing\\IdeaProjects\\WebDriverTest\\AddUserTCs.xlsx","UserLogin");
        return (testObjArray);
    }

    @DataProvider(name = "changeRoleforUser")
    public static Object[][] changeRoleUser() {
        Object[][] testObjArray_modUser = ExcelUtils.getTableArray("C:\\Users\\ZTE_testing\\IdeaProjects\\WebDriverTest\\TCListeModifyUser.xlsx","modfyUser_TC");
        return (testObjArray_modUser);
    }

    @Parameters({"browser", "HttpHomePage", "sUsername","sPassword"})
    @BeforeClass( alwaysRun = true)
    public void setUp(String browser, String HttpHomePage, String sUsername, String sPassword ) throws Exception {
        driver = browserWeb.setUp(browser);
        driver.get(HttpHomePage);
        Assert.assertTrue(driver.getTitle().contains("NCS-Testing"));
        driver.findElement(By.linkText("Anmelden")).click();
        //String sUsername = "admin";
        //String sPassword = "!NCS2019";
        loginAdmin001.A001_loginAdmin(driver,sUsername,sPassword);

    }

        @Test(dataProvider = "changeRoleforUser")
        public void A002_ChangeRoleforUser(String  sTC, String sUserName,
                                           String  sVisuellerEditor,
                                           String  sFarbschemaverwalten,String  sTastaturkürzel,
                                           String  sSprache,
                                           String  sWerkzeugleiste,String  sBenutzername,
                                           String  sRolle,String  sVorname,
                                           String  sNachname,String  sSpitzname,
                                           String  sEMail,String  sWebseite,
                                           String  sBiografischeAngaben,String  sNeuesPasswort
                                           ) {

            Boolean  breakFor = true;
            String sUserID = "";
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(By.xpath("//*[@class = 'wp-menu-image dashicons-before dashicons-admin-users']"))).perform();
            WebDriverWait w = new WebDriverWait(driver, 20);
            w.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Alle Benutzer")));
            driver.findElement(By.linkText("Alle Benutzer")).click();
            // Benutzer auswählen
            sUserID = getUserIDfromName.getUserIDfromName(driver, sUserName);
            if (!sUserID.isEmpty()) {
                //System.out.printf("sUderID: %s \n", sUserID);
                Actions action2 = new Actions(driver);
                action2.moveToElement(driver.findElement(By.xpath("//*[@id='user-" + sUserID + "']/td[1]/strong/a"))).perform();
                WebDriverWait w_wait = new WebDriverWait(driver, 20);
                w_wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Bearbeiten")));
                driver.findElement(By.linkText("Bearbeiten")).click();
                // //tr[@id='user-129']/td/div/span[2]/a ->  span[1] Benutzer bearbeiten, span[2]: Benutzer löschen,  span[3]: Benutzer anschauen
                // change role
                driver.findElement(By.id("role")).click();
                new Select(driver.findElement(By.id("role"))).selectByVisibleText(sRolle);
                driver.findElement(By.id("role")).click();

                // change Visueller Editor //*[@id="rich_editing"]
                if( sVisuellerEditor.equals("ja")) {
                    if (!driver.findElement(By.xpath("//*[@id=\"rich_editing\"]")).isSelected()) {
                        driver.findElement(By.xpath("//*[@id=\"rich_editing\"]")).click();
                    }
                }
                else {
                    if (driver.findElement(By.xpath("//*[@id=\"rich_editing\"]")).isSelected()) {
                        driver.findElement(By.xpath("//*[@id=\"rich_editing\"]")).click();
                    }
                }
                // Farbschema verwalten
                ////*[@id="color-picker"]/div[1]
                int j_rowCount = driver.findElements(By.xpath("//*[@id=\"color-picker\"]/div")).size();
                for (int j = 1; j <= j_rowCount && breakFor; j++) {
                    System.out.printf("Aufzählen der Farbwerte: %s", driver.findElement(By.xpath("//*[@id=\"color-picker\"]/div[" + j + "]/label")).getText());
                    if(driver.findElement(By.xpath("//*[@id=\"color-picker\"]/div[" + j + "]/label")).getText().contains(sFarbschemaverwalten)) {
                        driver.findElement(By.xpath("//*[@id=\"color-picker\"]/div[" + j + "]/input")).click();
                        breakFor = false;
                    }
                }

 ////*[@id="wp-submit"]

                // change speech
                breakFor = true;
                int i_rowCount = driver.findElements(By.xpath("//*[@id=\"locale\"]/option")).size();
                for (int i = 1; i <= i_rowCount && breakFor; i++) {
                   if(driver.findElement(By.xpath("//*[@id=\"locale\"]/option[" + i + "]")).getText().contains(sSprache)) {
                       driver.findElement(By.xpath("//*[@id=\"locale\"]/option[" + i + "]")).click();
                       breakFor = false;
                   }
                }


                driver.findElement(By.id("submit")).click();
                // Verification role changes: seach for message: 'Benutzer aktualisiert'
                String s = driver.findElement(By.xpath("//*[@id=\"message\"]/p[1]/strong")).getText();
                Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"message\"]/p[1]/strong")).getText().contains("Benutzer aktualisiert"), true);
            }
            else {
                Assert.assertTrue(false);
            }

        }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        loginAdmin001.Z1000_logout(driver);
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
