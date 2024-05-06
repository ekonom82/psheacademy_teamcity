package com.example.teamcity.api;

import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.checked.CheckedUser;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseApiTest{

    /*
     * variant 1.0
     */
//    @Test
//    public void buildconfigurationTest(){
//        var token = RestAssured.get("http://admin:admin@192.168.0.150:8111/authenticationTest.html?csrf")
//                .then().assertThat().statusCode(HttpStatus.SC_OK)
//                .extract().asString();
//        System.out.println(token);
//    }

    /*
     * variant 2.0
     */
//    @Test
//    public void buildconfigurationTest(){
//        var user = User.builder()
//                    .username("admin")
//                    .password("admin")
//                    .build();
//
//        var token = RestAssured
//                .given()
//                .spec(Specifications.getSpec().authSpec(user))
//                .get("/authenticationTest.html?crsf")
//                .then().assertThat().statusCode(HttpStatus.SC_OK)
//                .extract().asString();
//        System.out.println(token);
//    }

    /*
     * variant 3.0
     */
//    @Test
//    public void buildconfigurationTest(){
//        var user = User.builder()
//                    .username("admin")
//                    .password("admin")
//                    .build();
//
//        var projectDescription = NewProjectDescription.builder()
//                .parentProject(Project.builder()
//                        .locator("_Root")
//                        .build())
//                .name("name1")
//                .id("id1")
//                .copyAllAssociatedSettings(true)
//                .build();
//
//        var project = new CheckedProject(user).create(projectDescription);

    /*
    *  Responding with error, status code: 400 (Bad Request).
    *  Details: jetbrains.buildServer.serverSide.DuplicateProjectNameException: Project with this name already exists: name1
    *  Error occurred while processing this request.

    *  java.lang.AssertionError: 1 expectation failed.
    *  Expected status code <200> but was <400>.
     */
//    }

    /*
     * variant 4.0
     */
//    @Test
//    public void buildconfigurationTest(){
//        var testData = new TestDataGenerator().generate();
//
//        var project = new CheckedProject(testData.getUser()).create(testData.getProject());
//    }

    /*
     * variant 5.0
     * generation of testData in BaseApiTest because it will be called before every test
     */
//    @Test
//    public void buildconfigurationTest(){
//        var project = new CheckedProject(testData.getUser()).create(testData.getProject());
//    }

    /*
     * variant 6.0
     * added Soft assert by adding it in BaseTest
     */
    @Test
    public void buildconfigurationTest(){
        /*
        * refactoring according changes in UncheckedProject
        * */
        // var. 1 we param with User
//        var project = new CheckedProject(testData.getUser()).create(testData.getProject());

        // var. 2 we param with Specification
//        var project = new CheckedProject(Specifications.getSpec().authSpec(testData.getUser())).create(testData.getProject());

        // var. 3 we generate User using SuperUser Specification
//        new CheckedUser(Specifications.getSpec().superUserSpec()).create(testData.getUser());
//        var project = new CheckedProject(Specifications.getSpec().authSpec(testData.getUser())).create(testData.getProject());
//        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());

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
