package com.rest;

import io.restassured.config.LogConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomateGet {

    @Test
    public void validate_get_status_code() {
        given().
                baseUri("https://api.postman.com").header("X-Api-Key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }

    @Test
    public void validate_response_body() {
        given().
                baseUri("https://api.postman.com").header("X-Api-Key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name", hasItems("My Workspace2", "My Workspace"),
                        "workspaces.type", hasItems("personal", "personal"),
                        "workspaces[0].name", equalTo("My Workspace2"),
                        "workspaces[0].name", is(equalTo("My Workspace2")),
                        "workspaces.size()", equalTo(2));
    }

    @Test
    public void extract_response() {
        Response res = given().
                baseUri("https://api.postman.com").header("X-Api-Key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().response();
        System.out.println("response = " + res.asString());

    }

    @Test
    public void extract_single_value_response() {
        String name = given().
                baseUri("https://api.postman.com").header("X-Api-Key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().response().path("workspaces[0].name");
        System.out.println("workspace name = " + name);
        //System.out.println("workspace name = " + JsonPath.from(res).getString("workspaces[0].name"));
        // System.out.println("workspace name = " + jsonPath.getString("workspaces[0].name"));
        // System.out.println("workspace name = " + res.path("workspaces[0].name"));

    }

    @Test
    public void hamcrest_assert_on_extracted_response() {
        String name = given().
                baseUri("https://api.postman.com").header("X-Api-Key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().response().path("workspaces[0].name");
        System.out.println("workspace name = " + name);
        //assertThat(name , equalTo("My Workspace1"));
        Assert.assertEquals(name, "My Workspace2");
        //System.out.println("workspace name = " + JsonPath.from(res).getString("workspaces[0].name"));
        // System.out.println("workspace name = " + jsonPath.getString("workspaces[0].name"));
        // System.out.println("workspace name = " + res.path("workspaces[0].name"));

    }

    @Test
    public void validate_response_body_hamcrest_learnings() {
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name", containsInAnyOrder("My Workspace", "My Workspace2"),
                        /*For contains method order is strict*/
                        "workspaces.name", is(not(emptyArray())),
                        "workspaces.name", hasSize(2),
                        "workspaces.name", everyItem(startsWith("My")),
                        "workspaces[0]", hasKey("id"),
                        "workspaces[1]", hasValue("My Workspace"),
                        "workspaces[0]", hasEntry("id", "aa0a447b-f2b8-4fb2-8608-e0cc59da7b95"),
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)),
                        "workspaces[0].name", allOf(startsWith("My"), containsString("Workspace"))
                );
    }
}

