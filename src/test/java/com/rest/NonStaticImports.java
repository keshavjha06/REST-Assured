package com.rest;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class NonStaticImports {

    @Test
    public void simple_test_case(){
        RestAssured.given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
        when().
                get("/workspaces").
        then().
                statusCode(200).
                body("name", Matchers.is(Matchers.equalTo("Keshav")),
                        "email", Matchers.is(Matchers.equalTo("keshav@gmail.com")));
    }
}
