package com.example.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
/*
 * Build Configuration has name BuildType in the documentation
 * */
public class BuildType {
    private String id;
    private NewProjectDescription project;
    private String name;
}
