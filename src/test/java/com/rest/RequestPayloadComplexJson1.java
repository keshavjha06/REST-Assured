package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RequestPayloadComplexJson1 {

    ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void beforeClass() {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://b4de06c3-2d59-44d3-94f2-3ac78156fd20.mock.pstmn.io").
                //setConfig(config.encoderConfig(EncoderConfig.encoderConfig().
                //   appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                        addHeader("x-mock-match-request-body", "true").
                setContentType("application/json;charset=utf-8").
                log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        customResponseSpecification = responseSpecBuilder.build();
    }
    @Test
    public  void validate_post_request_payload_json_array_as_list() {

        List<Integer> rgbaList = new ArrayList<Integer>();
        rgbaList.add(255);
        rgbaList.add(255);
        rgbaList.add(255);
        rgbaList.add(1);

        HashMap<String, Object> rgbaObject = new HashMap<String, Object>();
        rgbaObject.put("rgba", rgbaList);
        rgbaObject.put("hex", "#000");

        HashMap <String, Object> colorMap = new HashMap<String, Object>();
        colorMap.put("color", "black");
        colorMap.put("category", "hue");
        colorMap.put("type", "primary");
        colorMap.put("code", rgbaObject);

        List<Integer> rgbaList2 = new ArrayList<Integer>();
        rgbaList2.add(0);
        rgbaList2.add(0);
        rgbaList2.add(0);
        rgbaList2.add(1);

        HashMap<String, Object> rgbaObject2 = new HashMap<String, Object>();
        rgbaObject2.put("rgba", rgbaList2);
        rgbaObject2.put("hex", "#FFF");

        HashMap <String, Object> colorMap2 = new HashMap<String, Object>();
        colorMap2.put("color", "white");
        colorMap2.put("category", "value");
        colorMap2.put("code", rgbaObject2);

        List<HashMap<String, Object>> colorsArrayList = new ArrayList<HashMap<String, Object>>();
        colorsArrayList.add(colorMap);
        colorsArrayList.add(colorMap2);

        HashMap <String, Object> colorObject = new HashMap<String, Object>();
        colorObject.put("colors", colorsArrayList);

        given().
                body(colorObject).
        when().
                post("/postComplexJsonAssignment").
        then().
                spec(customResponseSpecification).
                assertThat().
                body("msg", equalTo("Success"));
    }

}
