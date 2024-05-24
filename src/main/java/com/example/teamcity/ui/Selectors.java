package com.example.teamcity.ui;

import com.codeborne.selenide.selector.ByAttribute;

/*
* class helper to avoid an issue when specifying the type of attribute
* */
public class Selectors {
    public static ByAttribute byId(String value) {
        return new ByAttribute("id", value);
    }

    public static ByAttribute byType(String value) {
        return new ByAttribute("type", value);
    }

    public static ByAttribute byDataTest(String value) {
        return new ByAttribute("data-test", value);
    }

    public static ByAttribute byClass(String value) {
        return new ByAttribute("class", value);
    }
}
