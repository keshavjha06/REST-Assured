package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomatePut {

    @BeforeClass
    public void beforeClass() {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public  void validate_put_request_bdd_style() {
        String workspaceId = "77a7a71b-798b-46ba-93c2-bc4b790ef70d";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"newWorkspace\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"this is created by Rest Assured.\"\n" +
                "    }\n" +
                "}";
        given().
                body(payload).
                pathParam("workspaceId", workspaceId).
                when().
                put("/workspaces/{workspaceId}").
                then().
                log().all().
                assertThat().
                body("workspace.name" , equalTo("newWorkspace") ,
                        "workspace.id" , matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id" , equalTo(workspaceId));
    }

    @Test
    public  void validate_put_request_non_bdd_style() {
        String workspaceId = "77a7a71b-798b-46ba-93c2-bc4b790ef70d";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"newWorkspace\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"this is created by Rest Assured.\"\n" +
                "    }\n" +
                "}";
        Response response = with().
                body(payload).
                put("/workspaces/" + workspaceId);
        assertThat(response.path("workspace.name"), equalTo("newWorkspace"));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
        assertThat(response.path("workspace.id"), equalTo(workspaceId));

    }

}
