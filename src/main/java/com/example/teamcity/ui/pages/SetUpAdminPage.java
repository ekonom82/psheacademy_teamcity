package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.element;

@Getter
public class SetUpAdminPage extends Page {
    private static final String SETUP_ADMIN_PAGE_URL = "/setupAdmin.html";

    private final SelenideElement headerTitle = element(Selectors.byId("header"));
    private final SelenideElement loginAsSuperUserLink = element(By.xpath("//*[@class='registerUser']//a"));

    public SetUpAdminPage open() {
        Selenide.open(SETUP_ADMIN_PAGE_URL);
        return this;
    }

    public void clickLoginAsSuperUserLink() {
        loginAsSuperUserLink.click();
    }
}
