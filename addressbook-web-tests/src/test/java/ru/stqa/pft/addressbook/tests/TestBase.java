package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class TestBase {

    protected final ApplicationManager app = new ApplicationManager();
    protected WebDriver driver;
    private String baseUrl;
    protected boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
//old test
    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }


//    @BeforeClass(alwaysRun = true)
//    public void setUp() throws Exception {
//      System.setProperty("webdriver.gecko.driver", "D:\\\\Program files\\Rep\\geckodriver.exe");
//      driver = new FirefoxDriver();
//      baseUrl = "https://www.katalon.com/";
//      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//      driver.get("http://localhost/addressbook/");
//      login("admin", "secret");
//    }
//
//    private void login(String username, String password) {
//      driver.findElement(By.name("user")).click();
//      driver.findElement(By.name("user")).clear();
//      driver.findElement(By.name("user")).sendKeys(username);
//      driver.findElement(By.name("pass")).click();
//      driver.findElement(By.name("pass")).clear();
//      driver.findElement(By.name("pass")).sendKeys(password);
//      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Password:'])[1]/following::input[2]")).click();
//    }


//old test
    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

//    @AfterClass(alwaysRun = true)
//    public void tearDown() throws Exception {
//      driver.quit();
//      String verificationErrorString = verificationErrors.toString();
//      if (!"".equals(verificationErrorString)) {
//        fail(verificationErrorString);
//      }
//    }

    protected void confirmDeletion() {
      driver.switchTo().alert().accept();
    }

    protected void deleteSelectedContact() {
      driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Select all'])[1]/following::input[2]")).click();
    }

    protected void selectContact() {
      driver.findElement(By.name("selected[]")).click();
    }
}
