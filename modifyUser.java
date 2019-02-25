package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class modifyUser {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://www.katalon.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void modifyUser() throws Exception {
    driver.get("http://localhost/wordpress/wp-login.php?loggedout=true");
    driver.findElement(By.id("user_pass")).click();
    driver.findElement(By.id("user_pass")).clear();
    driver.findElement(By.id("user_pass")).sendKeys("$_1234zz");
    driver.findElement(By.id("wp-submit")).click();
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Erstellen'])[2]/following::div[8]")).click();
    driver.findElement(By.id("description")).click();
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("Dies ist ein Test");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Neues Passwort'])[1]/following::button[1]")).click();
    driver.findElement(By.id("pass1-text")).click();
    driver.findElement(By.id("pass1-text")).clear();
    driver.findElement(By.id("pass1-text")).sendKeys("$_1234zz");
    driver.findElement(By.name("pw_weak")).click();
    driver.findElement(By.id("submit")).click();
    driver.findElement(By.linkText("Abmelden")).click();
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
