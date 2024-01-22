import com.google.common.collect.Ordering;
import org.example.UsersDataSet;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class productSorting {
    WebDriver driver;
    List<WebElement> sortingOptions;

    @BeforeEach
    public void initializeChromedriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();
    }

    @AfterEach
    public void quitChromedirver() {
        driver.quit();
    }

    @Test
    public void testAreSOrtingOptionsCorrect() {
        WebElement parentElement = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select"));
        sortingOptions = parentElement.findElements(By.xpath(".//*"));
        List<String> expectedSortingOptions = List.of("Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)");

        String option;
        for (WebElement element : sortingOptions) {
            option = element.getText();
            Assert.assertTrue(expectedSortingOptions.contains(option));
        }

    }
    @Test
    public void correctSorting(){
        List<String> sortingOptions = List.of("option[value='az']", "option[value='za']", "option[value='lohi']", "option[value='hilo']");
        driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select")).click();

        for (String sortingOption : sortingOptions) {
            driver.findElement(By.cssSelector(sortingOption)).click();
            List<WebElement> listOfProducts = driver.findElements(By.cssSelector("div.inventory_item_name"));
            List<String> listOfProductsTitles = new ArrayList<String>();
            for (WebElement product : listOfProducts) {
                listOfProductsTitles.add(product.getText());
            }
            Assert.assertTrue(Ordering.<String>natural().isOrdered(listOfProductsTitles));

            Assert.assertTrue(Ordering.natural().reverse().isOrdered(listOfProductsTitles));
        }





        }
    }



