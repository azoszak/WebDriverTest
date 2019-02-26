package RegressionTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class loginAdmin {

    private static WebDriver driver;

    @DataProvider(name = "Authentication")
    public static Object[][] credentials() {
        Object[][] testObjArray = ExcelUtils.getTableArray("C:\\Users\\ZTE_testing\\IdeaProjects\\WebDriverTest\\AddUserTCs.xlsx","Admin");
        return (testObjArray);
    }

    @Test(dataProvider = "Authentication")
    public void  B001_loginAdmin(String sTC, String sUsername, String sPassword) {

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

}
