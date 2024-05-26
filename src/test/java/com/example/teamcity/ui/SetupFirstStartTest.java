package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.pages.LoginAsSuperUserPage;
import com.example.teamcity.ui.pages.SetUpAdminPage;
import com.example.teamcity.ui.pages.StartUpPage;
import org.testng.annotations.Test;

public class SetupFirstStartTest extends BaseUiTest {

    @Test
    public void setupTeamCityServerTest() {
        new StartUpPage()
                .open()
                .setUpTeamCityServer();

        new SetUpAdminPage()
                .open()
                .getHeaderTitle().shouldHave(Condition.text("Create Administrator Account"));
    }

    @Test
    public void setupTeamCityAgentTest() {
        new SetUpAdminPage()
                .open()
                .clickLoginAsSuperUserLink();
        new LoginAsSuperUserPage()
                .open()
                .login();
    }
}
