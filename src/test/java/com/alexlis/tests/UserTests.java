package com.alexlis.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UserTests {

//    @BeforeAll
//    void setUp() {
//        Configuration.baseUrl = "https://reqres.in/api/register";
//    }


    @Test
    void getListOfUsers() {
        given()
                .baseUri("https://reqres.in/api")
                .log().body()
                .when()
                .get("/users")
                .then()
                .log().body();
    }

    @Test
    void getUserById() {
        given()
                .baseUri("https://reqres.in/api")
                .log().body()
                .when()
                .get("/users/1")
                .then()
                .log().body();
    }

    @Test
    void getNotFoundUser() {
        given()
                .baseUri("https://reqres.in/api")
                .log().body()
                .when()
                .get("/users/721")
                .then()
                .log().body();
    }

    @Test
    void createUser() {
        given()
                .baseUri("https://reqres.in/api")
                .log().body()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .post("/users")
                .then()
                .log().body();
    }

    @Test
    void updateUser() {
        given()
                .baseUri("https://reqres.in/api")
                .log().body()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .post("/users")
                .then()
                .log().body();
    }

    @Test
    void successfulRegistration() {
        given()
                .baseUri("https://reqres.in/api")
                .log().body()
                .when()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .post("/register")
                .then()
                .log().body();
    }


}
