package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;
import lombok.Getter;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

@Getter
public class StartUpPage extends Page {
    private final SelenideElement proceedButton = element(Selectors.byId("proceedButton"));
    private final SelenideElement continueButton = element(By.xpath("//*[@name='Continue']"));
    private final SelenideElement headerTitle = element(Selectors.byId("header"));
    private final SelenideElement acceptLicenseCheckBox = element(Selectors.byId("accept"));

    public StartUpPage open() {
        Selenide.open("/");
        return this;
    }

    public StartUpPage setUpTeamCityServer() {
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        acceptLicenseCheckBox.shouldBe(Condition.enabled, Duration.ofSeconds(5));
        acceptLicenseCheckBox.scrollTo();
        acceptLicenseCheckBox.click();
        continueButton.click();
        return this;
    }
}
