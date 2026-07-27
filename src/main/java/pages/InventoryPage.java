package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InventoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public InventoryPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    private By cartIcon = By.className("shopping_cart_link");
    private By products = By.cssSelector("div .inventory_item");




    public String getPageTitle()
    {
        return driver.getTitle();
    }

    public boolean isCartDisplayed()
    {

        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartIcon)).isDisplayed();
    }

    public int getProductsCount()
    {

        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(products)).size();
    }

    public  InventoryPage assertOnTitle()
    {
        String actualText = driver.getTitle();
        String expectedText = getPageTitle();

        if(actualText.equals(expectedText))
            System.out.println("Title is correct");
        else
            System.out.println("Title is incorrect");

        return this;
    }

    public InventoryPage assertOnCart()
    {
        if(isCartDisplayed() == true)
            System.out.println("Cart icon displayed successfully");
        else
            System.out.println("Cart icon is not displayed");
        return this;
    }

    public InventoryPage assertOnProduct()
    {
        if(getProductsCount() == 6)
            System.out.println("Products count are 6");
        else
            System.out.println("Products count are " + getProductsCount());
        return this;
    }
}