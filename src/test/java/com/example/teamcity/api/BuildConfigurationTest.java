package com.example.teamcity.api;

import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.checked.CheckedUser;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseApiTest{

    @Test(groups ={"api", "smoke", "regression"})
    public void buildconfigurationTest(){
        /*
         * refactoring according changes related with replacement testData with TestDataStorage in BaseApiTest
         * */
        var testData = testDataStorage.addTestData();

        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(testData.getUser());
        var project = new CheckedProject(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .create(testData.getProject());
        softy.assertThat(project.getId())
                .isEqualTo(testData.getProject().getId());
    }
}
