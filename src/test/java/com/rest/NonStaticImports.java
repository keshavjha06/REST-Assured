package com.rest;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class NonStaticImports {

    @Test
    public void simple_test_case(){
        RestAssured.given().
                baseUri("https://api.postman.com").
                header("x-api-key", Secrets.postmanApiKey()).
        when().
                get("/workspaces").
        then().
                statusCode(200).
                body("name", Matchers.is(Matchers.equalTo("Keshav")),
                        "email", Matchers.is(Matchers.equalTo("keshav@gmail.com")));
    }
}
