package com.rest;

import io.restassured.config.LogConfig;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class Logging {

    @org.testng.annotations.Test
    public void request_response_logging() {
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
                log().headers().
                when().
                get("/workspaces").
                then().
                log().body().
                assertThat().
                statusCode(200);

    }

    @org.testng.annotations.Test
    public void log_only_if_error() {
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
                log().all().
                when().
                get("/workspaces").
                then().
                log().ifError().
                assertThat().
                statusCode(200);

    }

    @org.testng.annotations.Test
    public void log_only_if_validation_fails() {
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
                /* we can use config method instead of using ifValidationsFails() twice for request and response*/
                        config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())).
                //            log().ifValidationFails().
                        when().
                get("/workspaces").
                then().
                //           log().ifValidationFails().
                        assertThat().
                statusCode(201);

    }

    @Test
    public void logs_blacklist_header() {
        Set<String> headers = new HashSet<String>();
        headers.add("X-Api-Key");
        headers.add("Accept");
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6219be88ab339d257edc34ca-6d70c8591fd210658c3286c3386927c9a6").
                config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))).
                //config(config.logConfig(LogConfig.logConfig().blacklistHeader("X-Api-Key"))). - for blacklisting single header
                        log().all().
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200);

    }
}
