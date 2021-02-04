package com.udacity.jwdnd.course1.cloudstorage;


import com.udacity.jwdnd.course1.cloudstorage.views.LoginView;
import com.udacity.jwdnd.course1.cloudstorage.views.NoteView;
import com.udacity.jwdnd.course1.cloudstorage.views.ResultView;
import com.udacity.jwdnd.course1.cloudstorage.views.SignupView;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class NoteTests {
    @LocalServerPort
    private Integer port; // this port is the RANDOM_PORT

    // initialize fields:
    private static WebDriver driver;
    String baseURL;
    private NoteView noteView;
    private ResultView resultView;

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
        baseURL = "http://localhost:" + port ;

        // navigate to /signup:
        driver.get(baseURL + "/signup");
        // initialize object for SignupPage:
        SignupView signupPage = new SignupView(driver);
        // simulate user to register new user:
        signupPage.signup("Ivan", "Kabardin", "kebard1985", "12345");

        // navigate to login:
        driver.get(baseURL + "/login");

        // initialize object for LoginPage:
        LoginView loginPage = new LoginView(driver);
        loginPage.login("kebard1985", "12345");

        // currently logged in at this stage
        // initialize homepage page:
        noteView = new NoteView(driver);


        // simulate user to click on Notes tab:
        noteView.clickNoteTab();
    }

    /**
     *  TEST 2.1:
     * */
    @Test
    public void addNewNote() {
        // simulate user to click "Add/Edit a Note" button to add new note:
        noteView.clickAddNoteButton();
        // fill in data to add a new note:
        noteView.fillNoteData("Hello World!", "Many many words of wisdom");

        // after successfully added new note, navigate to Result page:
        // initialize new Result page object:
        resultView = new ResultView(driver);
        // navigate back to /home by click on "Here" link:
        resultView.clickHereButton();

        // simulate user to click on Notes tab:
        noteView.clickNoteTab();

        // test if new note's title and description match:
        assertEquals("Hello World!", noteView.getNoteTitleText());
        assertEquals("Many many words of wisdom", noteView.getNoteDescriptionText());
    }

    /**
     * TEST 2.2:
     * */
    @Test
    public void editNote() {

        // simulate user to click on "Edit" button:
        noteView.clickEditButton();
        // simulate user to editing note with new data:
        noteView.fillNoteData("New Title", "New Description");

        // after successfully added new note, navigate to Result page:
        // initialize new Result page object:
        resultView = new ResultView(driver);
        // navigate back to /home by click on "Here" link:
        resultView.clickHereButton();

        // simulate user to click on Notes tab:
        noteView.clickNoteTab();

        // test if new note's title and description match:
        assertEquals("New Title", noteView.getNoteTitleText());
        assertEquals("New Description", noteView.getNoteDescriptionText());
    }

    /**
     * TEST 2.3:
     * */
    @Test
    public void deleteNote() {

        // simulate user to click on "Delete button"
        noteView.clickDeleteButton();

        // after successfully added new note, navigate to Result page:
        // initialize new Result page object:
        resultView = new ResultView(driver);
        // navigate back to /home by click on "Here" link:
        resultView.clickHereButton();

        // simulate user to click on Notes tab:
        noteView.clickNoteTab();

        // test there should be no note data on homepage:
        // use assertThrows() with NoSuchElementException.class to test data does not exist:
        assertThrows(NoSuchElementException.class, () -> {
            noteView.getNoteTitleText();
        });
    }

}
