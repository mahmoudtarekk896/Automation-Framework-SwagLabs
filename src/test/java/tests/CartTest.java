package tests;

import base.BaseTest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DataDriven;

import java.util.ArrayList;
import java.util.List;

public class CartTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private JSONObject testData;

    @BeforeMethod
    public void loginSetup() {
        testData = DataDriven.jsonReader();
        JSONObject validLogin = (JSONObject) testData.get("validLogin");

        loginPage = new LoginPage(driver);
        loginPage.EnterUserName(validLogin.get("username").toString())
                .EnterPassword(validLogin.get("password").toString())
                .clickLogin();

        inventoryPage = new InventoryPage(driver);
    }

    @Test(priority = 1)
    public void testVerifySocialLinks() {
        Assert.assertTrue(inventoryPage.clickLinkedin().contains("linkedin"));
        Assert.assertTrue(inventoryPage.clickFacebook().contains("facebook"));
        Assert.assertTrue(inventoryPage.clickTwitter().contains("x.com"));
    }

    @Test(priority = 2)
    public void testVerifyCartIsEmpty() {
        cartPage = inventoryPage.openCart();
        Assert.assertEquals(cartPage.getCartItemsCount(), 0, "Cart is not empty!");
    }

    @Test(priority = 3)
    public void testAdd3SpecificProductsDataDriven() {
        JSONArray cartProducts = (JSONArray) testData.get("cartProducts");
        List<String> expectedProducts = new ArrayList<>();

        for (Object prod : cartProducts) {
            String name = prod.toString();
            expectedProducts.add(name);
            inventoryPage.addProductToCart(name);
        }

        cartPage = inventoryPage.openCart();
        List<String> actualProducts = cartPage.getCartItemNames();

        Assert.assertEquals(actualProducts, expectedProducts, "Products order or names mismatch!");
    }

    @Test(priority = 4)
    public void testRemoveOneProduct() {
        testAdd3SpecificProductsDataDriven();

        cartPage.removeItem("Sauce Labs Bolt T-Shirt");
        driver.navigate().back();

        Assert.assertEquals(inventoryPage.getProductButtonText("Sauce Labs Bolt T-Shirt"), "Add to cart");
        Assert.assertEquals(inventoryPage.getProductButtonText("Sauce Labs Backpack"), "Remove");
        Assert.assertEquals(inventoryPage.getProductButtonText("Sauce Labs Onesie"), "Remove");
    }

    @Test(priority = 5)
    public void testVerifyCartTotalPrice() {
        JSONArray cartProducts = (JSONArray) testData.get("cartProducts");
        double expectedSum = 0;

        for (Object prod : cartProducts) {
            String name = prod.toString();
            expectedSum += inventoryPage.getProductPrice(name);
            inventoryPage.addProductToCart(name);
        }

        cartPage = inventoryPage.openCart();
        cartPage.clickCheckout();

        JSONObject info = (JSONObject) testData.get("checkoutInfo");
        cartPage.fillCheckoutInformation(
                info.get("firstName").toString(),
                info.get("lastName").toString(),
                info.get("postalCode").toString()
        );

        double actualItemTotal = cartPage.getItemTotal();
        Assert.assertEquals(actualItemTotal, expectedSum, "Calculated total mismatch!");
    }

    @Test(priority = 6)
    public void testCheckoutWithEmptyCart() {
        cartPage = inventoryPage.openCart();
        cartPage.clickCheckout();

        boolean isPreventedOrErrorShown = driver.getCurrentUrl().contains("cart.html") || cartPage.getErrorMessage().contains("Cart is empty");
        Assert.assertTrue(isPreventedOrErrorShown, "Site allowed checkout with an empty cart!");
    }

    @Test(priority = 7)
    public void testCartStateAfterLogoutLogin() {
        inventoryPage.addProductToCart("Sauce Labs Backpack");
        inventoryPage.addProductToCart("Sauce Labs Onesie");

        inventoryPage.logout();

        JSONObject validLogin = (JSONObject) testData.get("validLogin");
        loginPage.EnterUserName(validLogin.get("username").toString())
                .EnterPassword(validLogin.get("password").toString())
                .clickLogin();

        cartPage = inventoryPage.openCart();
        Assert.assertTrue(cartPage.getCartItemsCount() > 0, "Cart items were cleared after logout!");
    }
}