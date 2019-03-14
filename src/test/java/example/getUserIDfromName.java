package example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class getUserIDfromName {

    public static String  getUserIDfromName(WebDriver driver , String sUserName) {
    String sUserId = "";
    Boolean b = Boolean.TRUE;
    Boolean breakFor = Boolean.TRUE;
    int i_rowCount = driver.findElements(By.xpath("//table/tbody/tr")).size();
    int i_ColCount = driver.findElements(By.xpath("//table/tbody/tr[1]/td")).size();
    //System.out.println("Tabelle aus geben:");
    WebElement w = driver.findElement(By.xpath("//table/tbody"));
    if(w.getText().contains("keinen Benutzer gefunden")) {
       sUserId = "";
    }
    else {
        for (int i = 1; i <= i_rowCount && breakFor; i++) {
            if (driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/th/label")).getText().contains(sUserName)) {
                sUserId =  driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/th/input")).getAttribute("value");
                breakFor = false;
            }
            else {
               sUserId = "";
            }
        }
    }
        return sUserId;
  }

}
