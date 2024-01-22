import org.example.User;
import org.example.UsersDataSet;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class testLogIn {
    WebDriver driver;
    UsersDataSet x;

    @BeforeEach
    public void initializeChromedriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        x = new UsersDataSet();
    }
    @AfterEach
    public void quitChromedirver(){
        driver.quit();
    }
    public void testCorrectLogIn(){
        x.addUsersToLists();
        for (User u : x.getListOfValidUsers()) {
            driver.navigate().to("https://www.saucedemo.com/");
            driver.findElement(By.id("user-name")).sendKeys(u.getUsername());
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
            String expectedPageTile = "Swag Labs";
            Assert.assertEquals(expectedPageTile, driver.getTitle());
        }
    }
    @Test
    public void testIncorrectLogIn() {
        x.addUsersToLists();
        for (User u : x.getListOfInValidUsers()) {
            driver.navigate().to("https://www.saucedemo.com/");
            driver.findElement(By.id("user-name")).sendKeys(u.getUsername());
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
            WebElement getErrorMessage = driver.findElement(By.cssSelector("h3[data-test='error']"));
            Assert.assertEquals("Epic sadface: Sorry, this user has been locked out.", getErrorMessage.getText());
        }
    }
}
// cel to zalogować się jako każdy użytkownik więc trzeba napisać pętlę iterującą po użytkownikach