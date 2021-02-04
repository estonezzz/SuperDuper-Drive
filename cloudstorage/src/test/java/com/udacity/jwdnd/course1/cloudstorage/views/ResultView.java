package com.udacity.jwdnd.course1.cloudstorage.views;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultView {
    // fields:
    @FindBy(tagName = "a")
    private WebElement backHomeLink;

    private final WebDriver driver;

    public ResultView(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickHereButton() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.backHomeLink);
    }
}
