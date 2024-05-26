package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.pages.admin.StartUpPage;
import org.testng.annotations.Test;

public class SetupTest extends BaseUiTest {

    @Test
    public void startUpTest() {
        new StartUpPage()
                .open()
                .setUpTeamCityServer()
                .getHeaderTitle().shouldHave(Condition.text("Create Administrator Account"));
    }
}
