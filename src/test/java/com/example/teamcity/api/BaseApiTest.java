package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class BaseApiTest extends BaseTest{

    /*
    * Replace using TestData with TestDataStorage cause
    * */
//    public TestData testData;
//
//    @BeforeTest
//    public void setupTest() {
//        testData = new TestDataGenerator().generate();
//    }
//
//    @AfterMethod
//    public void cleantest() {
//        testData.delete();
//    }

    public TestDataStorage testDataStorage;

    // Due to we always have access to Supper User and always can work with it
    // We create two next objects
    public CheckedRequests checkedWithSuperUser
            = new CheckedRequests(Specifications.getSpec().superUserSpec());

    public UncheckedRequests uncheckedWithSuperUser
            = new UncheckedRequests(Specifications.getSpec().superUserSpec());

    @BeforeMethod
    public void setupTest() {
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void cleanTest() {
        testDataStorage.delete();
    }

    @BeforeTest
    public void settingPermission() {
//        RestAssured.given()
//                .spec(Specifications.getSpec().superUserSpec())
//                .body(Map.of("perProjectPermissions", true))
//                .post("/app/rest/server/authSettings")
//                .then().assertThat().statusCode(HttpStatus.SC_OK)
//                .extract().asString();
    }
}
