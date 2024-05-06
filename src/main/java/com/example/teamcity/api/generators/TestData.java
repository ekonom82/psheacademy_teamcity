package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.unchecked.UncheckedProject;
import com.example.teamcity.api.requests.unchecked.UncheckedUser;
import com.example.teamcity.api.spec.Specifications;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestData {
    private User user;
    private NewProjectDescription project;
    private BuildType buildType;

    public void delete() {
        /*
        * refactoring according changes in UncheckedProject and in UncheckedUser
        * */
//        new UncheckedProject(user).delete(project.getId());
        var spec = Specifications.getSpec().authSpec(user);
        // when we delete Project we delete its BuildType automaticaly
        new UncheckedProject(spec).delete(project.getId());
        new UncheckedUser(spec).delete(user.getUsername());
    }
}
