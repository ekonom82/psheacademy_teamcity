package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.element;

public class UnauthorizedAgentsPage extends Page {
    private static final String UNAUTHORIZED_AGENTS_PAGE_URL = "/agents/unauthorized";

    private final SelenideElement authorizeButton = element(By.xpath("//*[@data-test-authorize-agent]"));

    public UnauthorizedAgentsPage open() {
        Selenide.open(UNAUTHORIZED_AGENTS_PAGE_URL);
        return this;
    }

    public void clickAuthorizeButton() {
        authorizeButton.click();
    }
}
