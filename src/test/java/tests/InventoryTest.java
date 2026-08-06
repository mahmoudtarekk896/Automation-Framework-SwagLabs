package tests;

import base.BaseTest;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DataDriven;

public class InventoryTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @Test
    public void VerifyElements() {
        JSONObject data = DataDriven.jsonReader();
        JSONObject validLogin = (JSONObject) data.get("validLogin");

        loginPage = new LoginPage(driver);
        loginPage.EnterUserName(validLogin.get("username").toString())
                .EnterPassword(validLogin.get("password").toString())
                .clickLogin();

        inventoryPage = new InventoryPage(driver);
        Assert.assertEquals(inventoryPage.getPageTitle(), "Swag Labs");
        Assert.assertTrue(inventoryPage.isCartDisplayed());
        Assert.assertEquals(inventoryPage.getProductsCount(), 6);
    }
}