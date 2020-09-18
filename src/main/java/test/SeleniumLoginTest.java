package test;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

/**
 * Automatisierter LoginTest mithilfe von Selenium
 */

public class SeleniumLoginTest {

    @Test
    public void testSeleniumJava() throws Exception {


        /**
         * Hier muss der Pfad zum ChromeDriver angegeben werden.
         */
        String driverpath = "C:\\\\Users\\\\Julia\\\\OneDrive\\\\Desktop\\\\chromedriver_win32\\\\chromedriver.exe";

        System.setProperty("webdriver.chrome.driver", driverpath);


        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(driver, 4);
        driver.get("http://localhost:8080/SEII_Projekt_Julian_und_Kim_war_exploded");


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("welcome1")));

        driver.findElement(By.className("loginbutton")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("EMAIL")));


        WebElement email = driver.findElement(By.id("EMAIL"));
        email.sendKeys("test@example.com");
        WebElement password = driver.findElement(By.id("PASSWORD"));
        password.sendKeys("password");
        driver.findElement(By.className("registerbutton")).click();



        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("tableart")));

        driver.close();





    }
}