import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class productSorting {
    WebDriver driver;

    List<WebElement> actualSortingOptionList = new ArrayList<>();

    WebElement inventoryList;
    List<String> initialInventoryProductsText = new ArrayList<>();
    List<String> initialInventoryProductsPriceText = new ArrayList<>();
    List<String> initialInventoryProductsTextList = new ArrayList<>();
    Select select;


    @BeforeEach
    public void initializeChromedriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

        WebElement sortingElement = driver.findElement(By.className("product_sort_container")); //finds webelement with classname "product_sort_container"
        select = new Select(sortingElement); //create objects of Select class
        List<WebElement> actualSortingOptionList = select.getOptions();
        inventoryList = driver.findElement(By.className("inventory_list")); //Find parent element of the products list
        List<WebElement> initialInventoryProducts = inventoryList.findElements(By.xpath("//div[contains(@class, 'inventory_item_label')]")); //moja poczatkowa lista produktów w defaultowej kolejności
        initialInventoryProductsText = new ArrayList<>(); //Initialize list of product names that will be sorted
        for (WebElement product : initialInventoryProducts) { //add product names to the list
            String productText = product.getText();
            initialInventoryProductsText.add(productText);
        }
        List<WebElement> initialInventoryProductsPriceObjectList = inventoryList.findElements(By.xpath("//div[contains(@class, 'inventory_item_price')]")); //moja poczatkowa lista produktów w defaultowej kolejności
        initialInventoryProductsPriceText = new ArrayList<>(); //Initialize list of product names that will be sorted
        for (WebElement product : initialInventoryProductsPriceObjectList) { //add product names to the list
            String productText = product.getText();
            initialInventoryProductsPriceText.add(productText);
        }

    }
    @AfterEach
    public void quitChromedirver() {
        driver.quit();
    }

    @Test
    public void testAreSortingOptionsCorrect() {
        List<String> expectedSortingOptionList = List.of("Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)");

        for (WebElement element : actualSortingOptionList) {
            Assert.assertTrue(expectedSortingOptionList.contains(element.getText()));
        }
    }
    @Test
    public void ascendingNameSorting() {
        Collections.sort(initialInventoryProductsText);
        select.selectByVisibleText("Name (A to Z)");

        List<WebElement> sortedProducts = inventoryList.findElements(By.xpath("//div[contains(@class, 'inventory_item_label')]"));
        List<String> sortedProductsTextList = new ArrayList<>();
        for (WebElement sortedProduct : sortedProducts) {
            String sortedProductText = sortedProduct.getText();
            sortedProductsTextList.add(sortedProductText);
        }
        Assertions.assertEquals(initialInventoryProductsText, sortedProductsTextList);
    }
    @Test
    public void descendingNameSorting(){
        Collections.sort(initialInventoryProductsText, Collections.reverseOrder());
        select.selectByVisibleText("Name (Z to A)");
        List<WebElement> sortedProducts = inventoryList.findElements(By.xpath("//div[contains(@class, 'inventory_item_label')]"));
        List<String> sortedProductsTextList = new ArrayList<>();
        for (WebElement sortedProduct : sortedProducts) {
            String sortedProductText = sortedProduct.getText();
            sortedProductsTextList.add(sortedProductText);
        }
        Assertions.assertEquals(initialInventoryProductsText, sortedProductsTextList);
    }


}

