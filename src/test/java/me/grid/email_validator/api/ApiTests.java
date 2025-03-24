package me.grid.email_validator.api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    static String baseURI = "http://localhost:8080/validate/";

    @BeforeAll
    public static void setUp(){
        requestSpecification = given().baseUri(baseURI)
                .contentType(ContentType.JSON);
    }

    @Test
    public void testWithValidBody_InvalidEmails(){
        Map<String, ArrayList<String>> map = new HashMap<>();
        map.put("emails", new ArrayList<>(List.of(
            "invalidEmailOne@@",
            "invalid####2"
        )));

        given()
                .spec(requestSpecification)
                .body(map)
        .when().post("")
        .then()
        .assertThat()
                .statusCode(200)
                .body("invalidEmails", equalTo(map.get("emails")))
                .body("validEmails", equalTo(new ArrayList()));
    }

    @Test
    public void testWithValidBody_ValidEmails(){
        Map<String, ArrayList<String>> map = new HashMap<>();
        map.put("emails", new ArrayList<>(List.of(
                "valid1@gma.ua",
                "valid2+_+_++_+_....@wow.co"
        )));

        given()
                .spec(requestSpecification)
                .body(map)
                .when().post("")
                .then()
                .assertThat()
                    .statusCode(200)
                    .body("validEmails", equalTo(map.get("emails")))
                    .body("invalidEmails", equalTo(new ArrayList()));
    }

    @Test
    public void testWithInvalidBody(){
        Map<String, ArrayList<String>> map = new HashMap<>();
        map.put("email", new ArrayList<>());

        given()
                .spec(requestSpecification)
                .body(map)
                .when().post("")
                .then()
                .assertThat()
                .body(containsString("Not a valid request body"));
    }

    @Test
    public void testWithoutABody(){
        given()
                .spec(requestSpecification)
                .when().post("")
                .then()
                .assertThat()
                .body(containsString("Request body is missing"));
    }


}
