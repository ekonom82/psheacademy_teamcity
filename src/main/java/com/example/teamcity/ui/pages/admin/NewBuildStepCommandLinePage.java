package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.element;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class NewBuildStepCommandLinePage extends Page {
    protected final SelenideElement saveButton = element(By.xpath("//input[@name='submitButton']"));
    private final SelenideElement stepNameInput = element(Selectors.byId("buildStepName"));
    private final SelenideElement customScriptTextField = element(By.xpath("//*[@id='script.content']"));

    public void clickSaveButton() {
        saveButton.scrollTo();
        saveButton.shouldBe(Condition.appear, Duration.ofSeconds(5)).click();
        waitUntilDataIsSaved();
    }

    public NewBuildStepCommandLinePage fillStepNameInputInput(String text) {
        stepNameInput.shouldBe(Condition.visible, Duration.ofSeconds(5)).sendKeys(text);
        return this;
    }

    public NewBuildStepCommandLinePage fillCustomScriptTextField(String text) {
        executeJavaScript(
                String.format("document.getElementById('script.content').value = \"%s\"", text)
        );

        return this;
    }
}
