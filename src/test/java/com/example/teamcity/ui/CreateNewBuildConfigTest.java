package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.ui.pages.admin.BuildConfigStepsPage;
import com.example.teamcity.ui.pages.admin.CreateNewBuildConfigPage;
import com.example.teamcity.ui.pages.admin.CreateNewProject;
import com.example.teamcity.ui.pages.admin.NewBuildStepCommandLinePage;
import com.example.teamcity.ui.pages.admin.SelectBuildConfigRunnerTypePage;
import org.testng.annotations.Test;

public class CreateNewBuildConfigTest extends BaseUiTest {
    @Test
    public void authorizedUserShouldBeAbleCreateNewBuildConfig() {
        var testData = testDataStorage.addTestData();
        var url = Config.getProperty("urlRepo");

        loginAsUser(testData.getUser());


        new CreateNewProject()
                .createProject(testData.getProject().getParentProject().getLocator(),
                        url, testData.getProject().getName(), testData.getBuildType().getName());
        new CreateNewBuildConfigPage().selectCommandLineBuildStep().clickUseSelected();
        new BuildConfigStepsPage().clickAddBuildStepsButton();

        new SelectBuildConfigRunnerTypePage().fillRunnerTypeInput(testData.getBuildConfig().getTypeBuildConfigRunner()).selectBuildConfigRunnerType();

        new NewBuildStepCommandLinePage()
                .fillStepNameInputInput(testData.getBuildConfig().getNameBuildStep())
                .fillCustomScriptTextField(testData.getBuildConfig().getCustomScript())
                .clickSaveButton();

        // после создания Build Config он окажется самым нижним в списке на странице
        // чтобы его получить, можно весь список с проектами схлопнуть до двух с помощью stream и его конструкции
        // .stream().reduce((first, second) -> second).get()
        // и далее работать с ним: получаем его название и проверяем или он соответствует сгенерированным данным Build Config, используемые при его создании
        new BuildConfigStepsPage()
                .getBuildConfigs()
                .stream().reduce((first, second) -> second).get()
                .getStepName().shouldHave(Condition.text(testData.getBuildConfig().getNameBuildStep()));
    }
}
