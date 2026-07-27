package tests;

import base.BaseTest;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DataDriven;


public class InventoryTest extends BaseTest {
    private InventoryPage inventoryPage;
    private LoginPage loginPage;

    @Test
    public void VerifyElements() {

        JSONObject data = DataDriven.jsonReader();

        JSONObject validLogin = (JSONObject) data.get("validLogin");

        String username = validLogin.get("username").toString();
        String password = validLogin.get("password").toString();

        loginPage = new LoginPage(driver);

        loginPage.EnterUserName(username)
                .EnterPassword(password)
                .clickLogin();

        inventoryPage = new InventoryPage(driver);

        Assert.assertEquals(inventoryPage.getPageTitle(), "Swag Labs");
        inventoryPage.assertOnTitle();
        Assert.assertTrue(inventoryPage.isCartDisplayed());
        inventoryPage.assertOnCart();
        Assert.assertEquals(inventoryPage.getProductsCount(), 6);
        inventoryPage.assertOnProduct();
    }
}