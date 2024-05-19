package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.element;

public class CreateNewProject extends Page {
    private final SelenideElement urlInput = element(Selectors.byId("url"));
    private final SelenideElement projectNameInput = element(Selectors.byId("projectName"));
    private final SelenideElement buildTypeNameInput = element(Selectors.byId("buildTypeName"));

    public CreateNewProject open(String parentProjectId) {
        // that link we see on Create New Project page when we click on "+" to create new project
        // if our new feature project does not have custom parent project so his projectId=_Root
        Selenide.open("/admin/createObjectMenu.html?projectId=" +  parentProjectId + "&showMode=createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewProject createProjectByUrl(String url) {
        urlInput.sendKeys(url);
        submit();
        return this;
    }

    public void setupProject(String projectName, String buildTypeName) {
        // очищаем, так уже имеют предустановленные значения
        projectNameInput.shouldBe(visible, Duration.ofSeconds(10)).clear();
        projectNameInput.sendKeys(projectName);
        buildTypeNameInput.clear();
        buildTypeNameInput.sendKeys(buildTypeName);
        submit();
    }
}
