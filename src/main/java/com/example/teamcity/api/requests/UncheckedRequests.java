package com.example.teamcity.api.requests;

import com.example.teamcity.api.requests.unchecked.UncheckedBuildConfig;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.requests.unchecked.UncheckedUser;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

/*
* Our tests become too difficult for reading: too many requests inside them
* To deal with this case we create these two classes where we can combine all requests and initialize them there and then call these requests in tests
* */
@Getter
public class UncheckedRequests {
    private UncheckedUser userRequest;
    private UncheckedProject projectRequest;
    private UncheckedBuildConfig buildConfigRequest;

    public UncheckedRequests(RequestSpecification spec) {
        this.userRequest = new UncheckedUser(spec);
        this.buildConfigRequest = new UncheckedBuildConfig(spec);
        this.projectRequest  = new UncheckedProject(spec);
    }
}
