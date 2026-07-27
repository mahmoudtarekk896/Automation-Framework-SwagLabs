package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;


    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    private By UserName = By.id("user-name");
    private By Password = By.id("password");
    private By Login_Button = By.id("login-button");
    private By ErrorMessage = By.xpath("//div[@class=\"error-message-container error\"]");


    public LoginPage EnterUserName(String Username)
    {
        WebElement user = wait.until(ExpectedConditions.elementToBeClickable(UserName));
        user.sendKeys(Username);
        return this;
    }

    public LoginPage EnterPassword(String Pass)
    {
        WebElement pass = wait.until(ExpectedConditions.elementToBeClickable(Password));
        pass.sendKeys(Pass);
        return this;
    }

    public LoginPage clickLogin(){
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(Login_Button));
        button.click();
        return this;
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ErrorMessage)).getText();
    }



}