package com.example.teamcity.api;

import com.example.teamcity.api.enums.Role;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.checked.CheckedUser;
import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class RolesTest extends BaseApiTest{
    @Test
    public void unauthorizedUserShouldNotHaveRightToCreateProject() {
        /*
        * In first method we check or will be created project by unauth User
        * we do not get any messages like Pshe "Authenctication required". Only code 401
        * */
        /*
         * refactoring according changes related with replacement testData with TestDataStorage in BaseApiTest
         * */
        var testData = testDataStorage.addTestData();

        /*
        * refactoring according changes related with creating FACADE requests in classes UncheckedRequests and CheckedRequests
        * */
        new UncheckedProject(Specifications.getSpec().unauthSpec())
                .create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED);
        // we do not get any message in response
//                .body(Matchers.containsString("Authenctication required"));


        /*
        * In second method we check by auth User that project was NOT created by unauth User in first method
        * */
        /*
         * refactoring second method according changes related with creating FACADE requests in classes UncheckedRequests and CheckedRequests
         * */
//        new UncheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
//                .get(testData.getProject().getId())
//                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
//                .body(Matchers.containsString("No project found by locator 'count:1,id:" + testData.getProject().getId() + "'"));

        uncheckedWithSuperUser.getProjectRequest()
                .get(testData.getProject().getId())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by locator 'count:1,id:" + testData.getProject().getId() + "'"));
    }

    /*
    * Scenario 2
    * in this test we do the same as in @Test buildconfigurationTest() in BuildConfigurationTest. But in this test we set Role of User directly as "SYSTEM_ADMIN"
    * */
    @Test
    public void systemAdminShouldHaveRightsToCreateProject() {
        /*
         * make changes according to changes in TestDataGenerator with adding method generateRoles and creation enum Role
         * */
        // v.1
//        testData.getUser().setRoles(Roles.builder()
//                .role(Arrays.asList(Role.builder()
//                        .scope("SYSTEM_ADMIN")
//                        .build()))
//                .build());
        // v.2 using method generateRoles
        /*
         * refactoring according changes related with replacement testData with TestDataStorage in BaseApiTest
         * */
        var testData = testDataStorage.addTestData();

        testData.getUser().setRoles(TestDataGenerator.generateRoles(Role.SYSTEM_ADMIN, "g"));

        /*
         * refactoring according changes related with creating FACADE requests in classes UncheckedRequests and CheckedRequests
         * */
//        new CheckedUser(Specifications.getSpec().superUserSpec()).create(testData.getUser());
        checkedWithSuperUser.getUserRequest().create(testData.getUser());

        var project = new CheckedProject(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    /*
    * Scenario 3 (positive)
    * */
    @Test
    public void projectAdminShouldHaveRightToCreateBuildConfigToHisProject() {
        /*
         * make the same test like systemAdminTest but with role = PROJECT_ADMIN
         * And we should also check PROJECT_ADMIN can make build configuration (Not only a project)
         * */
        /*
         * refactoring according changes related with replacement testData with TestDataStorage in BaseApiTest
         * */
        var testData = testDataStorage.addTestData();

        // !!!!! This step is very important cause we need to create Project before creating User for specific (this) Project
        new CheckedProject(Specifications.getSpec().superUserSpec())
                .create(testData.getProject());

        testData.getUser().setRoles(TestDataGenerator.generateRoles(Role.PROJECT_ADMIN, "p:" + testData.getProject().getId()));

        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(testData.getUser());

        // we've already created Project by Super User.
//        new CheckedProject(Specifications.getSpec()
//                .authSpec(testData.getUser()))
//                .create(testData.getProject());

        var buildConfig = new CheckedBuildConfig(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .create(testData.getBuildType());

        // we check that created buildConfig is the same which we send (which we generated in testData)
        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    /*
     * Scenario 4 (negative)
     * We need two users
     * First user creates his project
     * Second user creates his project
     * Second user tries to create buildConfig to first user's project
     *
     * To implement this case we need to create Storage (TestDataStorage) where we will store collection of our test data and every time generate these test data again
     * */
    @Test
    public void projectAdminShouldNotHaveRightsToCreateBuildConfigToAnotherProjectOld() {
        /*
         * refactoring according changes related with replacement testData with TestDataStorage in BaseApiTest
         * */
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

        // !!!!! This step is very important cause we need to create Project before creating first User for first Project
        new CheckedProject(Specifications.getSpec().superUserSpec())
                .create(firstTestData.getProject());
        // !!!!! This step is very important cause we need to create Project before creating second User for second Project
        new CheckedProject(Specifications.getSpec().superUserSpec())
                .create(secondTestData.getProject());

        firstTestData.getUser().setRoles(TestDataGenerator.generateRoles(Role.PROJECT_ADMIN, "p:" + firstTestData.getProject().getId()));

        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(firstTestData.getUser());

        secondTestData.getUser().setRoles(TestDataGenerator.generateRoles(Role.PROJECT_ADMIN, "p:" + secondTestData.getProject().getId()));

        new CheckedUser(Specifications.getSpec().superUserSpec())
                .create(secondTestData.getUser());

        // we try to authorize as FIRST user and then to create build configuration for project created by SECOND user
        new UncheckedBuildConfig(Specifications.getSpec().authSpec(secondTestData.getUser()))
                .create(firstTestData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN);
    }

    /*
    * Test like previous test but with changes according to changes related with creating FACADE requests in classes UncheckedRequests and CheckedRequests
    * */
    @Test
    public void projectAdminShouldNotHaveRightsToCreateBuildConfigToAnotherProject() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());
        checkedWithSuperUser.getProjectRequest().create(secondTestData.getProject());

        firstTestData.getUser().setRoles(TestDataGenerator.
                generateRoles(Role.PROJECT_ADMIN, "p:" + firstTestData.getProject().getId()));

        checkedWithSuperUser.getUserRequest().create(firstTestData.getUser());

        secondTestData.getUser().setRoles(TestDataGenerator.
                generateRoles(Role.PROJECT_ADMIN, "p:" + secondTestData.getProject().getId()));

        checkedWithSuperUser.getUserRequest()
                .create(secondTestData.getUser());

        new UncheckedBuildConfig(Specifications.getSpec().authSpec(secondTestData.getUser()))
                .create(firstTestData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
