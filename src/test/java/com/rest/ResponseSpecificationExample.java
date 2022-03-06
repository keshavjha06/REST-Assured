package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResponseSpecificationExample {

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        /*responseSpecification = RestAssured.expect().
                statusCode(200).
                contentType(ContentType.JSON).
                log().all();*/
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @org.testng.annotations.Test
    public void validate_status_code() {
         get("/workspaces");
    }

    @Test
    public void validate_response_body() {
        Response response = get("/workspaces").
                then().
                extract().
                response();
        assertThat(response.path("workspaces[0].name") , equalTo("My Workspace2"));

    }
}
