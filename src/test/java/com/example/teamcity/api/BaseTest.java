package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
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

    @BeforeMethod
    public void setupTest() {
        testDataStorage = TestDataStorage.getStorage();
    }

    @AfterMethod
    public void cleanTest() {
        testDataStorage.delete();
    }

    @BeforeMethod
    public void beforeTest() {
        testDataStorage = TestDataStorage.getStorage();
        softy = new SoftAssertions();
    }

    @AfterMethod
    public void afterTest() {
        softy.assertAll();
        testDataStorage.delete();
    }
}
