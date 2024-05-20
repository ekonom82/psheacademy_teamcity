package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.Page;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class CreateNewBuildConfigPage extends Page {
    private final SelenideElement commandLineCheckBox = element(By.xpath("//td[text()='Command Line']/preceding-sibling::td"));
    private final SelenideElement useSelectedButton = element(By.xpath("//*[@class='btn btn_primary']"));

    public CreateNewBuildConfigPage open(String buildType) {
        // full link has view  http://localhost:8111/admin/discoverRunners.html?init=1&id=buildType:PsheacademyTeamcity3_Build
        Selenide.open("/admin/discoverRunners.html?init=1&id=buildType:" +  buildType);
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewBuildConfigPage selectCommandLineBuildStep() {
        waitUntilPageIsLoaded();
        commandLineCheckBox.shouldBe(Condition.visible, Duration.ofSeconds(20)).click();
        return this;
    }

    public void clickUseSelected() {
        useSelectedButton.click();
        waitUntilDataIsSaved();
    }
}
