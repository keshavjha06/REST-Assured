package com.rest;

import static io.restassured.RestAssured.given;


public class Test {
    @org.testng.annotations.Test
    public void test() {
        given().
                baseUri("https://postman-echo.com").
                log().all().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);

    }
}
