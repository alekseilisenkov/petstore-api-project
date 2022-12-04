package com.alexlis.test.pet;

import com.alexlis.client.pet.PetClient;
import com.alexlis.config.BaseConfig;
import com.alexlis.dto.pet.response.DeletePetResponse;
import com.alexlis.dto.pet.response.PetModelResponse;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    protected String name;
    protected int id;
    protected DeletePetResponse deletePetResponse;

    protected PetModelResponse petModelResponse;
    protected PetClient petClient;
    private BaseConfig config;

    @BeforeAll
    void setUpPetConfig() {
        config = ConfigFactory.create(BaseConfig.class);

        RequestSpecification createPet = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .setBaseUri(config.hostname())
                .build();

        petClient = new PetClient(createPet);
    }
}
