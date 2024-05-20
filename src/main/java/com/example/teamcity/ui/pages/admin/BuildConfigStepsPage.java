package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.BuildStepElement;
import com.example.teamcity.ui.pages.Page;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.elements;

public class BuildConfigStepsPage extends Page {
    private final SelenideElement addBuildStepsButton = element(By.xpath("//*[contains(text(), 'Add build step')]"));
    // список наших build steps, что находятся на нашей странице (BuildConfigStepsPage), и имеют общий атрибут: class=editBuildStepRow
    // будем данный список вэбэлементов трансформировать в список объектов в методе generatePageElements класса Page
    private final ElementsCollection buildConfigs = elements(Selectors.byClass("editBuildStepRow"));

    public void clickAddBuildStepsButton() {
        addBuildStepsButton.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
        waitUntilDataIsSaved();
    }

    public List<BuildStepElement> getBuildConfigs() {
        return generatePageElements(buildConfigs, BuildStepElement::new);
    }
}
