package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.ui.pages.favorites.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateNewProject;
import org.testng.annotations.Test;

public class CreateNewProjectTest extends BaseUiTest {
    @Test
    public void authorizedUserShouldBeAbleCreateNewProject() {
        var testData = testDataStorage.addTestData();
        // link to your repository necessary to fill textfield during creation new project
        var url = "https://github.com/ekonom82/psheacademy_teamcity";

        // данная функциональность будет использоваться постоянно, поэтому ее вынесли в метод loginAsUser() класса BaseUiTest
//        new LoginPage().open().login(testData.getUser());
        loginAsUser(testData.getUser());

        new CreateNewProject()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(url)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        // после создания проекта он окажется самым нижним в списке на странице
        // чтобы его получить, можно весь список с проектами схлопнуть до двух с помощью stream и его конструкции
        // .stream().reduce((first, second) -> second).get()
        // и далее работать с ним: получаем его хедер и проверяем или он соответствует сгенерированным данным проекта, используемые при его создании
        new ProjectsPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(Condition.text(testData.getProject().getName()));
    }
}
