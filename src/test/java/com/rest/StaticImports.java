package com.rest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class StaticImports {

    @Test
    public void simple_test_case(){
        given().
                baseUri("https://api.postman.com").
                header("x-api-key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
        when().
                get("/workspaces").
        then().
                statusCode(200).
                body("name", is(equalTo("Keshav")),
                        "email", is(equalTo("keshav@gmail.com")));
    }
}
