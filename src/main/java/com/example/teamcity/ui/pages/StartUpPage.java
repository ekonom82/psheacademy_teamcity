package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class StartUpPage extends Page {
    private static final String START_UP_PAGE_URL = "/";

    private final SelenideElement proceedButton = element(Selectors.byId("proceedButton"));
    private final SelenideElement continueButton = element(By.xpath("//*[@name='Continue']"));
    private final SelenideElement acceptLicenseCheckBox = element(Selectors.byId("accept"));

    public StartUpPage open() {
        Selenide.open(START_UP_PAGE_URL);
        return this;
    }

    public void setUpTeamCityServer() {
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        acceptLicenseCheckBox.shouldBe(Condition.enabled, Duration.ofSeconds(5));
        acceptLicenseCheckBox.scrollTo();
        acceptLicenseCheckBox.click();
        continueButton.click();
    }
}
