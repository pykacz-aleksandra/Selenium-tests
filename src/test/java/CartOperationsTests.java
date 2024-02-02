import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;

public class CartOperationsTests {

    WebDriver driver;

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
    public void addingItemsToCart() {
        List<WebElement> addToCartButtons = driver.findElements(By.className("btn_inventory"));
        addToCartButtons.forEach(WebElement::click);
        int size = addToCartButtons.size();

        driver.findElement(By.className("shopping_cart_link")).click();
        List<WebElement> cartQuantity = driver.findElements(By.className("cart_quantity"));
        int suma = cartQuantity.stream()
                .mapToInt(quantity -> Integer.parseInt(quantity.getText()))
                .sum();

        int numberOfItemsBadge = Integer.parseInt(driver.findElement(By.className("shopping_cart_badge")).getText());
        Assertions.assertEquals(size, numberOfItemsBadge);
        Assertions.assertEquals(size, suma);
    }

    @Test
    public void deletingItemsFromCart() {
        List<WebElement> addToCartButtons = driver.findElements(By.className("btn_inventory"));
        addToCartButtons.forEach(WebElement::click);

        driver.findElement(By.className("shopping_cart_link")).click();

        List<WebElement> cartItems = driver.findElements(By.cssSelector("[id*='remove-sauce-labs'], [id*='remove-test']"));
        cartItems.forEach(WebElement::click);
        cartItems = driver.findElements(By.cssSelector("[id*='remove-sauce-labs'], [id*='remove-test']"));

        int expectedNumberOfItemsInCart = 0;
        int actualSize = cartItems.size();
        List<WebElement> badge = driver.findElements(By.className("shopping_cart_badge"));

        Assertions.assertEquals(expectedNumberOfItemsInCart, actualSize);
        Assertions.assertTrue(badge.isEmpty());
    }
}
