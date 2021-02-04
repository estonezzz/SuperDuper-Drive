package com.udacity.jwdnd.course1.cloudstorage.views;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// login.html
public class LoginView {
    // define fields:
    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(tagName = "button")
    private WebElement submitBtn;

    @FindBy(id = "error-msg")
    private WebElement unAuthorizedMsg;

    @FindBy(id = "logout-msg")
    private WebElement logoutMsg;

    private final WebDriver driver;

    public LoginView(WebDriver driver)
    {   this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {

        // fill in data:
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", this.username);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", this.password);

        // hit Login button:
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.submitBtn);
    }

    public boolean isInvalid() {
        return this.unAuthorizedMsg.isDisplayed();
    }

    public boolean isLoggedOut() {
        return this.logoutMsg.isDisplayed();
    }
}
