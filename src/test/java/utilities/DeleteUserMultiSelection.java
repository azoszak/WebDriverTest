package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class DeleteUserMultiSelection {


    public static void B_0001_SearchUser(WebDriver driver, String sTC, String suserName)  {
        Boolean b;

        mouseOver_Test.A001_mouseover(driver,
                "//*[@class = 'wp-menu-image dashicons-before dashicons-admin-users']",
                "Alle Benutzer");
        driver.findElement(By.id("user-search-input")).clear();
        driver.findElement(By.id("user-search-input")).sendKeys(suserName);
        //driver.findElement(By.id("user-search-input")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("search-submit")).click();

        b = B_0002_SelectUser(driver);
        if(b == Boolean.TRUE) {
            if (suserName.equals("admin")) {
                B_0004_DeleteSelectedAdmin(driver);
                WebDriverWait w = new WebDriverWait(driver, 20);
                driver.findElement(By.xpath("//*[@name='updateusers']"));
                w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@name='updateusers']")));
                System.out.printf("Admin kann nicht gelöscht werden %s ", driver.findElement(By.xpath("//*[@name='updateusers']")).getText());
                Assert.assertEquals(driver.findElement(By.xpath("//*[@name='updateusers']")).getText().contains("nicht gelöscht"), true);
            } else {
                B_0003_DeleteSelectedUser(driver);
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

    public static Boolean B_0002_SelectUser(WebDriver driver)  {

        String s1;
        Boolean b;


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

    public static void B_0003_DeleteSelectedUser(WebDriver driver)  {
        //driver.findElement(By.id("user_313")).click();
        driver.findElement(By.id("bulk-action-selector-bottom")).click();
        new Select(driver.findElement(By.id("bulk-action-selector-bottom"))).selectByVisibleText("Löschen");
        driver.findElement(By.id("bulk-action-selector-bottom")).click();
        driver.findElement(By.id("doaction2")).click();
        if ( !driver.findElements(By.id("delete_option0")).isEmpty() ) {
            driver.findElement(By.id("delete_option0")).click();
        }
        driver.findElement(By.id("submit")).click();

        // //*[@id="locale"]/option[2]
    }

    public static void B_0004_DeleteSelectedAdmin(WebDriver driver)  {
        //driver.findElement(By.id("user_313")).click();
        driver.findElement(By.id("bulk-action-selector-bottom")).click();
        new Select(driver.findElement(By.id("bulk-action-selector-bottom"))).selectByVisibleText("Löschen");

        // <input type="radio" id="delete_option0" name="delete_option" value="delete">
        driver.findElement(By.id("bulk-action-selector-bottom")).click();
        driver.findElement(By.id("doaction2")).click();

    }


}
