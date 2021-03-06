package com.cybertek.Day3;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpartanTestsParameters {

    @BeforeAll
    public static void init() {
//save baseurl inside this variable so that we don't need to type each http url.
        RestAssured.baseURI = "http://44.203.152.162:8000";

    }

    /*
     * Given accept type is Json
     * And Id parameter value is 5
     * When user sends GET request to /api/spartans/{id}
     * Then response status code should be 200
     * And response content-type: application/json
     * And "Blythe" should be in response payload*/

    @DisplayName("GET  request to /api/spartans/{id} with  ID=5")
    @Test
    public void test1(String id) {
//        Response response = RestAssured.given().
//                when().accept(ContentType.JSON).
//                get("/api/spartan/:" + "5");
//
//

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 5)
                .when()
                .get("/api/spartans/{id}");

        assertEquals(200, response.contentType());

        assertEquals("application/json", response.contentType());

        assertTrue(response.body().asString().contains("Blythe"));

    }

    /*
     * TASK
     * Given accept type is JSON
     * and id parameter value is 500
     * When user sends GET request to /api/spartans/{id}
     * Then response status code should be 404
     * and response content-type: application/json
     * and "Not Found" message should be in response payload*/

    @DisplayName("GET request /api/spartans/{id} Negative Test")
    @Test
    public void test2() {
        Response response = given().accept(ContentType.JSON).

                and().pathParam("id", 500).


                when().get("/api/spartans/{id}");


        assertEquals(404, response.statusCode());

        assertEquals("application/json", response.contentType());

        assertTrue(response.body().asString().contains("Not Found"));



        /*
         * Given type is json
         * And quert parameter values are:
         * gender| Female
         * nameContains|e
         * When user sends GET request to /api/spartans/search
         * Then response status code should be 200
         * And response content-type: application/json
         * And "Female" should be in response payload
         * And "Janette" should be in response payload*/
    }

    @DisplayName("GET to /api/spartans/search")
    @Test
    public void test3() {

        Response response = given().log().all().
                accept(ContentType.JSON).and().
                queryParam("gender", "Female").and()

                .queryParam("nameContains", "e")

                .and().when().get("/api/spartans/search");

        assertEquals(200, response.statusCode());

        assertEquals("application/json", response.contentType());

        assertTrue(response.body().asString().contains("Female") && response.body().asString().contains("Janette"));


    }

    @DisplayName("GET request to /api/spartans/search/ with Query Params (MAP)")
    @Test
    public void test4() {
        //create a map and add query parameters
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("nameContains", "e");
        queryMap.put("gender", "Female");
        Response response = given().log().all().accept(ContentType.JSON)
                .and().queryParams(queryMap)
                .when().get("/api/spartans/search");


        assertEquals(200, response.statusCode());

        assertEquals("application/json", response.contentType());

        assertTrue(response.body().asString().contains("Female") && response.body().asString().contains("Janette"));

    }

}