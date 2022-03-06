package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;


public class AutomatePost {
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
    public  void validate_post_request_bdd_style() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"aa0a447b-f2b8-4fb2-8608-e0cc59da7b95\",\n" +
                "        \"name\": \"myFirstWorkspace\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is your personal, private workspace to play around in. Only you can see the collections and APIs you create here - unless you share them with your team.\"\n" +
                "    }\n" +
                "}";
        given().
                body(payload).
        when().
                post("/workspaces").
         then().
                log().all().
                assertThat().
                body("workspace.name" , equalTo("myFirstWorkspace") ,
                        "workspace.id" , matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public  void validate_post_request_non_bdd_style() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"aa0a447b-f2b8-4fb2-8608-e0cc59da7b95\",\n" +
                "        \"name\": \"myFirstWorkspace2\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is your personal, private workspace to play around in. Only you can see the collections and APIs you create here - unless you share them with your team.\"\n" +
                "    }\n" +
                "}";
        Response response = with().
                body(payload).
                post("/workspaces");
        assertThat(response.<String>path("workspace.name"), equalTo("myFirstWorkspace2"));
        assertThat(response.<String>path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public  void validate_post_request_payload_from_file() {
        File file = new File("src/main/resources/CreateWorkspacePayload.json");
        given().
                body(file).
        when().
                post("/workspaces").
        then().
                log().all().
                assertThat().
                body("workspace.name" , equalTo("mySecondWorkspace") ,
                        "workspace.id" , matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public  void validate_post_request_payload_as_map() {
        HashMap<String, Object> mainObject = new HashMap<String, Object>();

        HashMap<String, String> nestedObject = new HashMap<String, String>();
        nestedObject.put("name", "myThirdWorkspace");
        nestedObject.put("type", "personal");
        nestedObject.put("description", "Rest Assured created this");

        mainObject.put("workspace" , nestedObject);
        given().
                body(mainObject).
        when().
                post("/workspaces").
        then().
                log().all().
                assertThat().
                body("workspace.name" , equalTo("myThirdWorkspace") ,
                        "workspace.id" , matchesPattern("^[a-z0-9-]{36}$"));
    }
}

