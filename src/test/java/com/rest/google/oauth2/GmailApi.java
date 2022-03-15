package com.rest.google.oauth2;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GmailApi {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token = "ya29.A0ARrdaM_Ap5BXAzDDt3GXscLtbR_NB9IEyMXsJT6w73xd3eo2N1KQFerPpXXP3Ois4IcGy1xHO_-r2YMPSqR3HzHQYXsQz1mcntMpIBM6fl75v0T891rrYBbyP72-ijIYhEv_Gm_kxpgWYT5JzfQfH7FVTfNdUQ";

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://gmail.googleapis.com").
                addHeader("Authorization", "Bearer " + access_token).
                //        setConfig(config.encoderConfig(EncoderConfig.encoderConfig()
                //                .appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                        setContentType(ContentType.JSON).
                log(LogDetail.ALL);

        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void getUserProfile(){
        given(requestSpecification).
                basePath("/gmail/v1").
                pathParam("userid", "christinarachelberg@gmail.com").
        when().
                get("/users/{userid}/profile").
        then().spec(responseSpecification);
    }

    @Test
    public void sendMessage(){
        String msg = "From: christinarachelberg@gmail.com\n" +
                "To: christinarachelberg@gmail.com\n" +
                "Subject: Rest Assured Test Email\n" +
                "\n" +
                "Sending from Rest Assured";

        String base64UrlEncodedMsg = Base64.getUrlEncoder().encodeToString(msg.getBytes());

        HashMap<String, String> payload = new HashMap<>();
        payload.put("raw", base64UrlEncodedMsg);

        given(requestSpecification).
                basePath("/gmail/v1").
                pathParam("userid", "christinarachelberg@gmail.com").
                body(payload).
        when().
                post("/users/{userid}/messages/send").
        then().spec(responseSpecification);
    }

    @Test
    public void list(){
        given(requestSpecification).
                basePath("/gmail/v1").
                pathParam("userid", "christinarachelberg@gmail.com").
        when().
                get("/users/{userid}/messages").
        then().spec(responseSpecification);
    }

    @Test
    public void get(){
        given(requestSpecification).
                basePath("/gmail/v1").
                pathParam("userid", "christinarachelberg@gmail.com").
                when().
                get("/users/{userid}/messages/17f78c0ce20d6022").
                then().spec(responseSpecification);
    }

    @Test
    public void delete(){
        given(requestSpecification).
                basePath("/gmail/v1").
                pathParam("userid", "christinarachelberg@gmail.com").
        when().
                delete("/users/{userid}/messages/17f78c0ce20d6022").
        then().spec(responseSpecification);
    }


}
