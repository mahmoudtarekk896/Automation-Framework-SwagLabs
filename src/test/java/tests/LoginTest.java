package tests;

import base.BaseTest;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DataDriven;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    @Test
    public void VerifySuccessfulLogin() {

        JSONObject data = DataDriven.jsonReader();

        JSONObject validLogin = (JSONObject) data.get("validLogin");

        String username = validLogin.get("username").toString();
        String password = validLogin.get("password").toString();

        loginPage = new LoginPage(driver);

        loginPage.EnterUserName(username)
                .EnterPassword(password)
                .clickLogin();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test
    public void VerifyInvalidLogin() {

        JSONObject data = DataDriven.jsonReader();

        JSONObject invalidLogin = (JSONObject) data.get("invalidLogin");

        String username = invalidLogin.get("username").toString();
        String password = invalidLogin.get("password").toString();

        loginPage = new LoginPage(driver);

        loginPage.EnterUserName(username)
                .EnterPassword(password)
                .clickLogin();

        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"));
    }

    @Test
    public void LoginWithoutPassword() {

        JSONObject data = DataDriven.jsonReader();

        JSONObject emptyPassword = (JSONObject) data.get("emptyPassword");

        String username = emptyPassword.get("username").toString();
        String password = emptyPassword.get("password").toString();

        loginPage = new LoginPage(driver);

        loginPage.EnterUserName(username)
                .EnterPassword(password)
                .clickLogin();

        Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"));
    }
}