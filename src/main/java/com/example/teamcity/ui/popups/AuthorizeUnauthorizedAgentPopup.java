package com.example.teamcity.ui.popups;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.element;

public class AuthorizeUnauthorizedAgentPopup {
    private final SelenideElement authorizeButton = element(By.xpath("//*[@data-test='ring-popup' and @data-test-shown='true']"));

    public void clickAuthorizeButton() {
        authorizeButton.click();
    }
}
