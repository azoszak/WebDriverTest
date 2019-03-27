package RegressionTest;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import utilities.*;

import static org.testng.Assert.fail;


public class DataProviderWithExcel_all {
    public String baseUrl = "http://localhost/wordpress";
    public WebElement webtable;
    private WebDriver driver;
    public setBrowser browserWeb = new setBrowser(driver);
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @DataProvider(name = "userLogin")
    public static Object[][] userLogin() {
        Object[][] testObjArray = ExcelUtils.getTableArray("AddUserTCs.xlsx", "UserLogin");
        return (testObjArray);
    }

    @DataProvider(name = "addUser")
    public static Object[][] addUser() {
        Object[][] testObjArray_addUser = ExcelUtils.getTableArray("AddUserTCs.xlsx", "User");
        return (testObjArray_addUser);
    }

    @DataProvider(name = "deleteUser")
    public static Object[][] deleteUser() {
        Object[][] testObjArray_deleteUser = ExcelUtils.getTableArray("AddUserTCs.xlsx", "deleteUser");
        return (testObjArray_deleteUser);
    }

    @DataProvider(name = "deleteUserMultiSelection")
    public static Object[][] deleteUserMultiSelection() {
        Object[][] testObjArray_deleteUserMultiSelection = ExcelUtils.getTableArray("AddUserTCs.xlsx", "deleteUserMultiSelection");
        return (testObjArray_deleteUserMultiSelection);
    }

    @DataProvider(name = "UpdateUser")
    public static Object[][] UpdateUser() {
        Object[][] testObjArray_UpdateUser = ExcelUtils.getTableArray("AddUserTCs.xlsx", "modfyUser_TC");
        return (testObjArray_UpdateUser);
    }

    // Set up Browser
    @Parameters({"browser", "HttpHomePage", "sUsername", "sPassword"})
    @BeforeClass(alwaysRun = true)
    public void setUp(String browser, String HttpHomePage, String sUsername, String sPassword) throws Exception {
        driver = browserWeb.setUp(browser);
        driver.get(HttpHomePage);
        Assert.assertTrue(driver.getTitle().contains("NCS-Testing"));
        driver.findElement(By.linkText("Anmelden")).click();
        //String sUsername = "admin";
        //String sPassword = "!NCS2019";
        loginAdmin001.A001_loginAdmin(driver, sUsername, sPassword);

    }

    @Test(dataProvider = "addUser")
    public void A002_adduser(String sTC, String suserLogin, String sEmail,
                             String sfirstName, String slastName, String sPassword,
                             String ssendUserNotifiaction, String sRole) {

        System.out.printf("\n Testcase: %s Login user as : %s  Passwd: %s \n", sTC, suserLogin, sPassword);
        // go to add user page
        mouseOver_Test.A001_mouseover(driver, "//*[@class = 'wp-menu-image dashicons-before dashicons-admin-users']", "Neu hinzufügen");

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
        if (driver.findElement(By.className("pw-checkbox")).isDisplayed()) {
            driver.findElement(By.className("pw-checkbox")).click();
        }

        WebElement ckb_BenutzerBenachrichtigen = driver.findElement(By.id("send_user_notification"));
        System.out.printf("Benutzer Benachrichtigungen: Häckchen gesetzt?  %s \n", ckb_BenutzerBenachrichtigen.isEnabled());
        if (ckb_BenutzerBenachrichtigen.isEnabled()) {

            ckb_BenutzerBenachrichtigen.click();
        }
        new Select(driver.findElement(By.id("role"))).selectByVisibleText(sRole);
        //driver.findElement(By.xpath((//*[normalize-space(text()) and normalize-space(.)='Rolle'])[1]/following::option[2])).click();
        driver.findElement(By.id("createusersub")).click();
        WebDriverWait w1 = new WebDriverWait(driver, 20);
        System.out.println("Add user as admin");
        w1.until(ExpectedConditions.presenceOfElementLocated(By.id("message")));
        Assert.assertEquals(driver.findElement(By.id("message")).getText().contains("Neuer Benutzer erstellt"), true);
    }

    @Test(dataProvider = "UpdateUser")
    public void A010_UpdateUser(String sTC, String sUserName,
                                String sVisuellerEditor,
                                String sFarbschemaverwalten, String sTastaturkürzel,
                                String sSprache,
                                String sWerkzeugleiste, String sBenutzername,
                                String sRolle, String sVorname,
                                String sNachname, String sSpitzname,
                                String sEMail, String sWebseite,
                                String sBiografischeAngaben, String sNeuesPasswort
    ) {

        Boolean breakFor = true;
        int i_rowCount;
        String s_xpath;
        String sUserID = "";
        String s_PresenceofElement;

        // Benutzer auswählen
        s_xpath = "//*[@class = 'wp-menu-image dashicons-before dashicons-admin-users']";
        s_PresenceofElement = "Alle Benutzer";
        mouseOver_Test.A001_mouseover(driver, s_xpath, s_PresenceofElement);

        sUserID = getUserIDfromName.getUserIDfromName(driver, sUserName);
        if (!sUserID.isEmpty()) {
            s_xpath = "//*[@id='user-" + sUserID + "']/td[1]/strong/a";
            s_PresenceofElement = "Bearbeiten";
            mouseOver_Test.A001_mouseover(driver, s_xpath, s_PresenceofElement);

            if (sVisuellerEditor.equals("ja")) {
                if (!driver.findElement(By.xpath("//*[@id=\"rich_editing\"]")).isSelected()) {
                    driver.findElement(By.xpath("//*[@id=\"rich_editing\"]")).click();
                }
            } else {
                if (driver.findElement(By.xpath("//*[@id=\"rich_editing\"]")).isSelected()) {
                    driver.findElement(By.xpath("//*[@id=\"rich_editing\"]")).click();
                }
            }
            // Farbschema verwalten
            breakFor = true;
            i_rowCount = driver.findElements(By.xpath("//*[@id=\"color-picker\"]/div")).size();
            for (int i = 1; i <= i_rowCount && breakFor; i++) {
                System.out.printf("Aufzählen der Farbwerte: %s", driver.findElement(By.xpath("//*[@id=\"color-picker\"]/div[" + i + "]/label")).getText());
                if (driver.findElement(By.xpath("//*[@id=\"color-picker\"]/div[" + i + "]/label")).getText().contains(sFarbschemaverwalten)) {
                    driver.findElement(By.xpath("//*[@id=\"color-picker\"]/div[" + i + "]/input")).click();
                    breakFor = false;
                }
            }

            // Tastaturkürzel String  sTastaturkürzel: ja/nein
            s_xpath = "//*[@id=\"your-profile\"]/table[1]/tbody/tr[3]/td/label/input";
            if (sTastaturkürzel.equals("ja")) {
                if (!driver.findElement(By.xpath(s_xpath)).isSelected()) {
                    driver.findElement(By.xpath(s_xpath)).click();
                }
            } else {
                if (driver.findElement(By.xpath(s_xpath)).isSelected()) {
                    driver.findElement(By.xpath(s_xpath)).click();
                }
            }

            // change speech
            breakFor = true;
            i_rowCount = driver.findElements(By.xpath("//*[@id=\"locale\"]/option")).size();
            for (int i = 1; i <= i_rowCount && breakFor; i++) {
                if (driver.findElement(By.xpath("//*[@id=\"locale\"]/option[" + i + "]")).getText().contains(sSprache)) {
                    driver.findElement(By.xpath("//*[@id=\"locale\"]/option[" + i + "]")).click();
                    breakFor = false;
                }
            }

            // //tr[@id='user-129']/td/div/span[2]/a ->  span[1] Benutzer bearbeiten, span[2]: Benutzer löschen,  span[3]: Benutzer anschauen
            // change role
            driver.findElement(By.id("role")).click();
            new Select(driver.findElement(By.id("role"))).selectByVisibleText(sRolle);
            driver.findElement(By.id("role")).click();

            // change Namen
            driver.findElement(By.id("first_name")).sendKeys(sVorname);
            driver.findElement(By.id("last_name")).sendKeys(sNachname);
            driver.findElement(By.id("nickname")).sendKeys(sSpitzname);
            driver.findElement(By.id("url")).sendKeys(sWebseite);
            driver.findElement(By.id("description")).sendKeys(sBiografischeAngaben);

            // change passwd
            s_xpath = "//*[@id=\"password\"]/td/button";
            driver.findElement(By.xpath(s_xpath)).click();
            driver.findElement(By.id("pass1-text")).clear();
            driver.findElement(By.id("pass1-text")).sendKeys(sNeuesPasswort.replaceAll("^(.)(.*)", "$1"));
            driver.findElement(By.id("pass1-text")).sendKeys(sNachname.replaceAll("^(.)(.*)", "$2"));
            if (driver.findElement(By.className("pw-checkbox")).isDisplayed()) {
                driver.findElement(By.className("pw-checkbox")).click();
            }

            driver.findElement(By.id("submit")).click();
            // Verification role changes: seach for message: 'Benutzer aktualisiert'
            String s = driver.findElement(By.xpath("//*[@id=\"message\"]/p[1]/strong")).getText();
            Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"message\"]/p[1]/strong")).getText().contains("Benutzer aktualisiert"), true);
        } else {
            Assert.assertTrue(false);
        }

    }


    @Test(dataProvider = "deleteUser")
    public void B010_deleteUser(String sTC, String suserLogin) {

        String sUserID = "";
        mouseOver_Test.A001_mouseover(driver,
                "//*[@class = 'wp-menu-image dashicons-before dashicons-admin-users']",
                "Alle Benutzer");
        // Benutzer zum Löschen auswählen
        sUserID = getUserIDfromName.getUserIDfromName(driver, suserLogin);
        if (!sUserID.isEmpty()) {
            mouseOver_Test.A001_mouseover(driver,
                    "//*[@id='user-" + sUserID + "']/td[1]/strong/a",
                    "Löschen");
            if (!driver.findElements(By.id("delete_option0")).isEmpty()) {
                driver.findElement(By.id("delete_option0")).click();
            }
            driver.findElement(By.id("submit")).click();
            Assert.assertEquals(driver.findElement(By.id("message")).getText().contains("Benutzer gelöscht"), true);
        } else {
            //Assert.assertEquals(driver.findElement(By.id("message")).getText().contains("Benutzer gelöscht"), true);
            Assert.fail("user not found");
        }


    }

    @Test(dataProvider = "deleteUserMultiSelection")
    public void B020_deleteUserMultiSelection(String sTC, String suserLogin) {

        DeleteUserMultiSelection.B_0001_SearchUser(driver, sTC, suserLogin);
        Assert.assertEquals(driver.findElement(By.id("message")).getText().contains("Benutzer gelöscht"), true);

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
