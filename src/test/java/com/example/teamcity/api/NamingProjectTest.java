package com.example.teamcity.api;

import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.spec.Specifications;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class NamingProjectTest extends BaseApiTest{
    @Test
    public void canNotBeCreatedProjectWithTheSameProjectId() {
        var testData = testDataStorage.addTestData();

        var projectFirst = new CheckedProject(Specifications.getSpec().superUserSpec())
                .create(testData.getProject());
        softy.assertThat(projectFirst.getId())
                .isEqualTo(testData.getProject().getId());

        new UncheckedProject(Specifications.getSpec().superUserSpec())
                .create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project with this name already exists: " + testData.getProject().getName()));
    }

    @Test
    public void canNotBeCreatedProjectWithNameLonger255Characters() {
        int maxAllowedLength = 255;
        String randomAllowedName = RandomStringUtils.randomAlphabetic(maxAllowedLength);
        String randomNotAllowedName = randomAllowedName + "T";

        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

        firstTestData.getProject().setName(randomAllowedName);
        secondTestData.getProject().setName(randomNotAllowedName);

        var projectFirst = new CheckedProject(Specifications.getSpec().superUserSpec())
                .create(firstTestData.getProject());
        softy.assertThat(projectFirst.getName())
                .isEqualTo(firstTestData.getProject().getName());

        new UncheckedProject(Specifications.getSpec().superUserSpec())
                .create(secondTestData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
