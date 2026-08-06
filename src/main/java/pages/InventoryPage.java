package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InventoryPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By cartIcon = By.className("shopping_cart_link");
    private By products = By.className("inventory_item");
    private By linkedin = By.linkText("LinkedIn");
    private By facebook = By.linkText("Facebook");
    private By twitter = By.linkText("Twitter");
    private By menuButton = By.id("react-burger-menu-btn");
    private By logoutLink = By.id("logout_sidebar_link");

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isCartDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartIcon)).isDisplayed();
    }

    public int getProductsCount() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(products)).size();
    }

    public CartPage openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
        return new CartPage(driver);
    }

    public InventoryPage addProductToCart(String productName) {
        String xpath = "//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
        return this;
    }

    public String getProductButtonText(String productName) {
        String xpath = "//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button";
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).getText();
    }

    public double getProductPrice(String productName) {
        String xpath = "//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//div[@class='inventory_item_price']";
        String priceText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).getText();
        return Double.parseDouble(priceText.replace("$", ""));
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    private String getNewUrlAfterClick(By socialLocator) {
        String mainTab = driver.getWindowHandle();
        wait.until(ExpectedConditions.elementToBeClickable(socialLocator)).click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        String newUrl = driver.getCurrentUrl();
        driver.close();
        driver.switchTo().window(mainTab);

        return newUrl;
    }

    public String clickLinkedin() {
        return getNewUrlAfterClick(linkedin);
    }

    public String clickFacebook() {
        return getNewUrlAfterClick(facebook);
    }

    public String clickTwitter() {
        return getNewUrlAfterClick(twitter);
    }
}