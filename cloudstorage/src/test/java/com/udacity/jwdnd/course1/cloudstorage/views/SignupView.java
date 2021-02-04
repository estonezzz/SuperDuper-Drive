package com.udacity.jwdnd.course1.cloudstorage.views;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupView {

    @FindBy(id = "inputFirstName")
    private WebElement firstname;

    @FindBy(id = "inputLastName")
    private WebElement lastname;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(tagName = "button")
    private WebElement signupButton;

    @FindBy(xpath = "//label//a[@href='/login']")
    private WebElement backToLoginButton;

    private final WebDriver driver;


    public SignupView(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void signup(String filename, String lastname, String username, String password) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + filename + "';", this.firstname);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + lastname + "';", this.lastname);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", this.username);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", this.password);

        // hit Sign Up button:
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.signupButton);
    }
}
