package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.ui.Selectors;

import static com.codeborne.selenide.Selenide.element;

public class LoginAsSuperUserPage extends Page{
    private static final String SUPERUSER_LOGIN_PAGE_URL = "/login.html?super=1";

    private final SelenideElement passwordInput = element(Selectors.byId("password"));
    private final SelenideElement loginButton = element(Selectors.byType("password"));

    public LoginAsSuperUserPage open() {
        Selenide.open(SUPERUSER_LOGIN_PAGE_URL);
        return this;
    }

    public void login() {
        passwordInput.sendKeys(Config.getProperty("superUserToken"));
        loginButton.click();
    }
}
