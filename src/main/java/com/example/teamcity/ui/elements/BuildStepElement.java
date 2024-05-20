package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;

@Getter
public class BuildStepElement extends PageElement {
    private final SelenideElement stepName;

    public BuildStepElement(SelenideElement element) {
        super(element);

        stepName = findElement(Selectors.byClass("highlight stepName"));
    }
}
