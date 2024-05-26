package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.element;

@Getter
public class AgentsPage extends Page {
    private static final String AGENTS_PAGE_URL = "/agents/overview";

    private final SelenideElement unauthorizedAgentsButton = element(By.xpath("//*[@href='/agents/unauthorized']"));
    private final SelenideElement unauthorizedAgentsText = element(By.xpath("//*[@href='/agents/unauthorized']//*[@data-test-agents-sidebar-title]"));
    private final SelenideElement AgentsNumberText = element(By.xpath("//*[@data-hint-container-id='header-agents-active']"));

    public AgentsPage open() {
        Selenide.open(AGENTS_PAGE_URL);
        return this;
    }

    public void clickUnauthorizedAgentsButton() {
        unauthorizedAgentsButton.click();
    }
}
