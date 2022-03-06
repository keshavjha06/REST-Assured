package com.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class RequestSpecificationExample {
    RequestSpecification requestSpecification;

    @BeforeClass
    public void beforeClass() {
        requestSpecification = with(). /*with and given both are same*/
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
                log().all();
    }

    @Test
    public void validate_status_code() {
        Response response = requestSpecification.get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
    }

    @Test
    public void validate_response_body() {
        Response response = requestSpecification.get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(response.path("workspaces[0].name") , equalTo("My Workspace2"));
        /*Other ways to do*/
        //assertThat(response.path("workspaces[0].name") , is(equalTo("My Workspace2")));
        //assertThat(response.path("workspaces[0].name").toString() , equalTo("My Workspace2"));
    }

}
