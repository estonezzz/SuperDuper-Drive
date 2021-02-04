package com.udacity.jwdnd.course1.cloudstorage.views;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialsView {
    // logout:
    @FindBy(xpath = "//*[@id='logoutDiv']//button")
    private WebElement logoutButton;

    // define fields for Credential:
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    // url field:
    @FindBy(id="credential-id")
    private WebElement credentialID;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(xpath = "//*[@id='credUrlText']")
    private WebElement credentialUrlText;

    // username field:
    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(xpath = "//*[@id='credUsernameText']")
    private WebElement credentialUsernameText;

    // password field:
    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(xpath = "//*[@id='credPasswordText']")
    private WebElement credentialPasswordText;

    // buttons:
    @FindBy(id = "add-cred-btn")
    private WebElement addCredentialButton;

    @FindBy(id = "cred-save-btn")
    private WebElement submitButton;

    @FindBy(xpath = "//*[@id='cred-EditBtn']")
    private WebElement editButton;

    @FindBy(xpath = "//*[@id='cred-DeleteBtn']")
    private WebElement deleteButton;

    @FindBy(xpath = "//*[@id='cred-closeBtn']")
    private WebElement closeButton;

    // driver (Chrome):
    private final WebDriver driver;

    private CredentialService credentialService;

    // constructor:
    public CredentialsView(WebDriver driver, CredentialService credentialService) {
        this.driver = driver;
        this.credentialService = credentialService;

        PageFactory.initElements(driver, this);
    }


    // method to simulate user to click on Credentials tab:
    public void clickCredTab() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.credentialsTab);
    }

    // method to click on Add New Credential button:
    public void clickAddCredBtn() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.addCredentialButton);
    }

    // method to click on Edit button:
    public void clickEditBtn() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.editButton);
    }

    // method to click on Delete button:
    public void clickDeleteBtn() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.deleteButton);
    }
    public void clickCloseBtn() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.closeButton);
    }


    // method to fill data to add new credentials:
    public void fillCredentialData(String url, String username, String password) {
        // fill in data:
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + url + "';", this.credentialUrl);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", this.credentialUsername);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", this.credentialPassword);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.submitButton);

    }


    // verify that new credential's url is created:
    public String getUrlText() {
        return credentialUrlText.getAttribute("innerHTML");
    }

    // verify that new credential's username is created:
    public String getUsernameText() {
        return credentialUsernameText.getAttribute("innerHTML");
    }

    // verify that new credential's password is created:
    // this should be the value of ENCRYPTED password:
    public String getPasswordText() {
        return credentialPasswordText.getAttribute("innerHTML");
    }



    // get unencrypted password:
    public String getUnencryptedPassword() {
        return this.credentialPassword.getAttribute("value");
    }
}
