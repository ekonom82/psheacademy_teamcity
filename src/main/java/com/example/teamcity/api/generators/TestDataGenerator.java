package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Role;
import com.example.teamcity.api.models.Roles;
import com.example.teamcity.api.models.User;

import java.util.Arrays;

public class TestDataGenerator {
    /*
    * Update method according to adding new two parameters of User as email and roles and adding two new classes as Roles and Role
    * old variant - we generated User with username/password as admin/admin
    * new variant - we generate User with random username, password and email. But set roleId as "SYSTEM_ADMIN"
    * */
//    public TestData generate() {
//        var user = User.builder()
//                .username("admin")
//                .password("admin")
//                .build();
//        var project = NewProjectDescription
//                .builder()
//                .parentProject(Project.builder()
//                        .locator("_Root")
//                        .build())
//                .name(RandomData.getString())
//                .id(RandomData.getString())
//                .copyAllAssociatedSettings(true)
//                .build();
//        return TestData.builder()
//                .user(user)
//                .project(project)
//                .build();
//    }


    /*
    * By default we set roleId of User as "SYSTEM_ADMIN" which we can over set to another roleId where it is needed
    * And make all methods as static
    * */
    public static TestData generate() {
        var user = User.builder()
                .username(RandomData.getString())
                .password(RandomData.getString())
                .email(RandomData.getString() + "@gmail.com")
                .roles(Roles.builder()
                        .role(Arrays.asList(Role.builder()
                                .roleId("SYSTEM_ADMIN")
                                .scope("g")
                                .build()))
                        .build())
                .build();

        var project = NewProjectDescription
                .builder()
                .parentProject(Project.builder()
                        .locator("_Root")
                        .build())
                .name(RandomData.getString())
                .id(RandomData.getString())
                .copyAllAssociatedSettings(true)
                .build();

        var buildType = BuildType.builder()
                .id(RandomData.getString())
                .name(RandomData.getString())
                .project(project)
                .build();

        return TestData.builder()
                .user(user)
                .project(project)
                .buildType(buildType)
                .build();
    }

    // !!! params "scope" needs to make relation between User and specific Project
    public static Roles generateRoles(com.example.teamcity.api.enums.Role role, String scope) {
        return Roles.builder().role
                (Arrays.asList(Role.builder().roleId(role.getText())
//                        .scope("g").build())).build();
                        .scope(scope).build())).build();
    }
}
