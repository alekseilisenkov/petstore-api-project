package com.alexlis.test.pet;

import com.alexlis.test.TestBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PetTests extends TestBase {

    @Test
    void getPetById() {
        given()
                .baseUri("https://petstore.swagger.io/v2")
                .when()
                .get("/pet/101")
                .then()
                .log().body()
                .log().status()
                .statusCode(200);
    }

    @Test
    void createPet() {


        given()
                .baseUri("https://petstore.swagger.io/v2")
                .spec()
                .when()
                .body("{\n" +
                        "  \"id\": 151,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"string\"\n" +
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .log().body()
                .post("pet")

                .then()
                .log().body();
    }

    @Test
    void updatePet() {
        given()
                .baseUri("https://petstore.swagger.io/v2")
                .log().body()
                .when()
                .body("{\n" +
                        "  \"id\": 0,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"string\"\n" +
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .put("pet")
                .then()
                .log().body();
    }

    @Test
    void deletePet() {
        given()
                .baseUri("https://petstore.swagger.io/v2")
                .when()
                .delete("pet/102")
                .then()
                .log().body();
    }

}
