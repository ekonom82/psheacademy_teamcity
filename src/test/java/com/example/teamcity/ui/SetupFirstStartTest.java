package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.pages.AgentsPage;
import com.example.teamcity.ui.pages.LoginAsSuperUserPage;
import com.example.teamcity.ui.pages.SetUpAdminPage;
import com.example.teamcity.ui.pages.StartUpPage;
import com.example.teamcity.ui.pages.UnauthorizedAgentsPage;
import com.example.teamcity.ui.pages.favorites.ProjectsPage;
import com.example.teamcity.ui.popups.AuthorizeUnauthorizedAgentPopup;
import org.testng.annotations.Test;

public class SetupFirstStartTest extends BaseUiTest {

    @Test(groups ={"ui", "setup_steps"})
    public void setupTeamCityServerTest() {
        new StartUpPage()
                .open()
                .setUpTeamCityServer();

        new SetUpAdminPage()
                .open()
                .getHeaderTitle().shouldHave(Condition.text("Create Administrator Account"));
    }

    @Test(groups ={"ui", "setup_steps"})
    public void setupTeamCityAgentTest() {
        new SetUpAdminPage()
                .open()
                .clickLoginAsSuperUserLink();
        new LoginAsSuperUserPage()
                .open()
                .login();
        new ProjectsPage()
                .open()
                .clickAgentsMenuButton();
        new AgentsPage()
                .open()
                .clickUnauthorizedAgentsButton();
        new UnauthorizedAgentsPage()
                .open()
                .clickAuthorizeButton();
        new AuthorizeUnauthorizedAgentPopup().clickAuthorizeButton();
        new AgentsPage()
                .open()
                .getAgentsNumberText().shouldHave(Condition.text("1"));
    }
}
