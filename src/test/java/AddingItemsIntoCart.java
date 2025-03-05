import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddingItemsIntoCart {
    WebDriver driver;

    @BeforeTest
    void ApplicationLogIn() {
        //Set the path to the ChromeDriver executable.
        System.setProperty("webDriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        // Navigate to the SauceApp.
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        // ApplicationLogIn
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }

    @Test
    void AddingHighestPriceItemIntoCart() {


        // Find all price elements on the page
        List<WebElement> PriceElements = driver.findElements(By.xpath("//div[@class='inventory_item_price'][1]"));
        //Print out the number of links found
        System.out.println("Total PriceElements: " + PriceElements.size());

        double maxPrice = 0.0;
        int maxPriceIndex = -1;

        // Loop through all price elements to find the highest price
        for (int i = 0; i < PriceElements.size(); i++) {
            String priceText = PriceElements.get(i).getText().replace("$", "").replace(",", "");
            try {
                double price = Double.parseDouble(priceText);
                if (price > maxPrice) {
                    maxPrice = price;
                    maxPriceIndex = i + 1;
                    System.out.println("maxPriceIndex:" + i);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing price: " + priceText);
            }
        }
        // Output the maximum price
        System.out.println("Maximum Price: $" + maxPrice);

        //  highest-priced item - "Add to Cart"
        String xpathB = "//div[@class='inventory_item'][" + maxPriceIndex + "]//button";
        WebElement addToCartButtons = driver.findElement(By.xpath(xpathB));

        // Click on the "Add to Cart" button for the highest-priced item
        addToCartButtons.click();
    }
}