package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.github.viclovsky.swagger.coverage.SwaggerCoverageRestAssured;
import io.restassured.RestAssured;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected SoftAssertions softy;
    public TestDataStorage testDataStorage;
    /*
     * Due to we always have access to Supper User and always can work with it
     * We create two next objects
     * */
    public CheckedRequests checkedWithSuperUser
            = new CheckedRequests(Specifications.getSpec().superUserSpec());

    public UncheckedRequests uncheckedWithSuperUser
            = new UncheckedRequests(Specifications.getSpec().superUserSpec());

    @AfterMethod
    public void cleanTest() {
        testDataStorage.delete();
    }

    @BeforeMethod
    public void beforeTest() {
        testDataStorage = TestDataStorage.getStorage();
        softy = new SoftAssertions();
        /*
        * Фильтр создаст папку по умолчанию swagger-coverage-output с метаинформацией для построения отчета о покрытии.
        * */
        RestAssured.given().filter(new SwaggerCoverageRestAssured());
    }

    @AfterMethod
    public void afterTest() {
        softy.assertAll();
        testDataStorage.delete();
    }
}
