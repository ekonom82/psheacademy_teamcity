package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;

public class SelectBuildConfigRunnerTypePage extends Page {
    private final SelenideElement runnerTypeInput = element(Selectors.byDataTest("runner-item-filter"));
    private final SelenideElement nameBuildConfigRunnerType = element(Selectors.byClass("SelectBuildRunners__title--Vf"));

    public SelectBuildConfigRunnerTypePage fillRunnerTypeInput(String text) {
        runnerTypeInput.shouldBe(Condition.visible, Duration.ofSeconds(5)).sendKeys(text);
        return this;
    }

    public SelectBuildConfigRunnerTypePage selectBuildConfigRunnerType() {
        nameBuildConfigRunnerType.click();
        return this;
    }
}
