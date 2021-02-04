package com.udacity.jwdnd.course1.cloudstorage.views;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NoteView {
    /** define fields */
    // logout:
    @FindBy(xpath = "//*[@id='logoutDiv']//button")
    private WebElement logoutButton;

    // define fields for NOTES:
    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    // title field:
    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(xpath = "//*[@id='noteTitleText']")
    private WebElement noteTitleText;

    // description:
    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(xpath = "//*[@id='noteDescriptionText']")
    private WebElement noteDescriptionText;

    // buttons:
    @FindBy(id = "add-note-btn")
    private WebElement addNoteButton;

    @FindBy(id = "note-savechanges-btn")
    private WebElement submitButton;

    @FindBy(id = "note-edit-btn")
    private WebElement editNoteButton;

    @FindBy(id = "note-delete-btn")
    private WebElement deleteNoteButton;

    // driver:
    private final WebDriver driver;

    // constructor:
    public NoteView(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /** define methods */

    // method to simulate user to click on Notes tab:
    public void clickNoteTab() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.notesTab);
    }

    // method to click on Add button:
    public void clickAddNoteButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.addNoteButton);
    }

    // method to click Edit button:
    public void clickEditButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.editNoteButton);
    }

    // method to click Delete button:
    public void clickDeleteButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.deleteNoteButton);
    }

    // method to fill data for note. Use both for Add and Edit Test:
    public void fillNoteData(String title, String description) {
        // fill in data:
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", this.noteTitle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", this.noteDescription);

        // click on "Save Changes" to submit:
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.submitButton);
    }

    // verify that new note title is created:
    public String getNoteTitleText() {
        return noteTitleText.getAttribute("innerHTML");
    }

    // verify that new note description is created:
    public String getNoteDescriptionText() {
        return noteDescriptionText.getAttribute("innerHTML");
    }

    // simulate user to click logout button:
    public void logout() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.logoutButton);
    }
}
