package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.views.CredentialsView;
import com.udacity.jwdnd.course1.cloudstorage.views.LoginView;
import com.udacity.jwdnd.course1.cloudstorage.views.ResultView;
import com.udacity.jwdnd.course1.cloudstorage.views.SignupView;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialsTests {

    // define Credential Service variable
    //  to use getCrdentialsByID and call Credentials object
    // from Credential object, use getKey() to get key for encryption:
    @Autowired
    private CredentialService credentialService;

    // initialize fields:
    private static WebDriver driver;
    String baseURL;
    private CredentialsView credentialsView;
    private ResultView resultView;
    private EncryptionService encryptionService;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    // after all tests, Selenium quit driver:
    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    // before each test, signup and login to get to homepage:
    @BeforeEach
    public void beforeEach() {
        baseURL = "http://localhost:8080"; /*+ port;*/

        // navigate to /signup:
        driver.get(baseURL + "/signup");
        // initialize object for SignupPage:
        SignupView signupView = new SignupView(driver);
        // simulate user to register new user:
        signupView.signup("Ivan", "Kabardin", "kebard1985", "12345");

        // navigate to login:
        driver.get(baseURL + "/login");

        // initialize object for LoginPage:
        LoginView loginView = new LoginView(driver);
        loginView.login("kebard1985", "12345");

        // currently logged in at this stage
        // initialize Encryption service to encrypt/decrypt password
        // inside add credential and edit credential:
        encryptionService = new EncryptionService();

        // initialize homepage page:
        credentialsView = new CredentialsView(driver,credentialService);

        // simulate user to click on Credentials tab on nav bar:
        credentialsView.clickCredTab();
    }

    /**
     *  TEST 3.1:
     *  Write a test that creates a set of credentials,
     *  verifies that they are displayed,
     *  and verifies that the displayed password is encrypted.
     * */
    @Test
    public void addNewCredential() {

        // simulate user to click on Add new credential button:
        credentialsView.clickAddCredBtn();

        // simulate user to add new data to create add credential:
        credentialsView.fillCredentialData("amazon.com", "kebard1985", "54321");

        // after successfully added new credential, navigate to Result page
        // initialize new Result page object:
        resultView = new ResultView(driver);
        // navigate back to /home by click on "here" button:
        resultView.clickHereButton();

        // at this stage, user is currently back to homepage:
        // test if page title is "Home":
        assertEquals("Home", driver.getTitle());

        // simulate user to click on Credentials tab again:
        credentialsView.clickCredTab();

        // click on edit button to
        // grab credentialId from modal form
        credentialsView.clickEditBtn();
        String scriptHtml = "return document.getElementById('credential-id').getAttribute('value');";
        int credentialId = Integer.parseInt( ((JavascriptExecutor) driver).executeScript(scriptHtml).toString());
        // click to close modal form
        credentialsView.clickCloseBtn();
        System.out.println("INPUT CREDENTIAL ID: " + credentialId);

        //credentials returns null no matter what, can't figure out how to fix it
        Credentials credentials = credentialService.getCredentialById(credentialId);

//        System.out.println("INPUT CREDENTIAL ID: " + credentials.getCredentialID());


        // test if new credential url, username, and password match:
        assertEquals("amazon.com", credentialsView.getUrlText());
        assertEquals("kebard1985", credentialsView.getUsernameText());
        assertEquals(this.encryptionService.encryptValue("54321", credentials.getKey()), credentialsView.getPasswordText());
    }

    /**
     * TEST 3.2:
     *  Write a test that views an existing set of credentials,
     *  verifies that the viewable password is unencrypted,
     *  edits the credentials, and verifies that the changes are displayed.
     * */
    @Test
    public void editCredential() {
        // simulate user to click on "Edit" button:
        credentialsView.clickEditBtn();



        // simulate user to modify existing data to edit credential:
        credentialsView.fillCredentialData("blabla.com", "keb", "12345");

        // after successfully added new credential, navigate to Result page
        // initialize new Result page object:
        resultView = new ResultView(driver);
        // navigate back to /home by click on "here" button:
        resultView.clickHereButton();

        // test if page title is "Home":
        assertEquals("Home", driver.getTitle());

        // simulate user to click on Credentials tab again:
        credentialsView.clickCredTab();

        // click on edit button to
        // grab credentialId from modal form
        credentialsView.clickEditBtn();
        String scriptHtml = "return document.getElementById('credential-id').getAttribute('value');";
        int credentialId = Integer.parseInt( ((JavascriptExecutor) driver).executeScript(scriptHtml).toString());
        // click to close modal form
        credentialsView.clickCloseBtn();
        System.out.println("INPUT CREDENTIAL ID: " + credentialId);

        //credentials returns null no matter what, can't figure out how to fix it
        Credentials credentials = credentialService.getCredentialById(credentialId);


        // test if newly edited credential url, username, and password match:
        assertEquals("blabla.com", credentialsView.getUrlText());
        assertEquals("keb", credentialsView.getUsernameText());
        // use EncryptionService to use decryptValue() to get unencrypted pass
        // then compare with currently encrypted password:
        assertEquals(this.encryptionService.encryptValue("12345", credentials.getKey()), credentialsView.getPasswordText());
    }

    /**
     * TEST 3.3:
     * Write a test that deletes a note
     * and verifies that the note is no longer displayed.
     * */
    @Test
    public void deleteCredential() {

        // simulate user to click on "Delete button"
        credentialsView.clickDeleteBtn();

        // after successfully added new credential, navigate to Result page:
        // initialize new Result page object:
        resultView = new ResultView(driver);
        // navigate back to /home by click on "Here" link:
        resultView.clickHereButton();

        // simulate user to click on Notes tab:
        credentialsView.clickCredTab();

        // test there should be no credential data on homepage:
        // use assertThrows() with NoSuchElementException.class to test data does not exist:
        assertThrows(NoSuchElementException.class, () -> {
            credentialsView.getUsernameText();
        });
    }
}
