package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedProject extends Request implements CrudInterface {
    private static final String PROJECT_ENDPOINT = "/app/rest/projects";

    /*
    * Мы сделали ошибку, когда начали сощдавать проекты на основе передаваемого авторизированного User
    * Но, у нас есть кейсы, когда мы пытаемся создать проект НЕавторизированным User-ом
    * А сам User нам нужен для получения авторизированной спецификации, используя юзернейм и пароль, для его дальнейшей передачи в create
    * Но мы можем передавать и неавторизированную спецификацию
    *
    * Поэтому нужно параметризировать не User, а RequestSpecification -> у каждого запроса (реквеста) должна быть своя спецификация,
    * котороя содержит всю информацию, что у нас лежит в Хедерах запроса
    * */
//    private User user;

    public UncheckedProject(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object obj) {
        return RestAssured
                .given()
//                .spec(Specifications.getSpec().authSpec(user))
                .spec(spec)
                .body(obj)
                .post(PROJECT_ENDPOINT);
    }

    @Override
    public Response get(String id) {
        return given().spec(spec).get(PROJECT_ENDPOINT + "/id:" + id);
    }

    @Override
    public Object update(String id, Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return given()
//                .spec(Specifications.getSpec().authSpec(user))
                .spec(spec)
                .delete(PROJECT_ENDPOINT + "/id:" + id);
    }
}
