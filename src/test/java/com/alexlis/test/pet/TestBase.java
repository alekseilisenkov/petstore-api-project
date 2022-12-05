package com.alexlis.test.pet;

import com.alexlis.client.pet.PetClient;
import com.alexlis.config.BaseConfig;
import com.alexlis.dto.pet.response.DeletePetResponse;
import com.alexlis.dto.pet.response.PetModelResponse;
import io.restassured.builder.RequestSpecBuilder;
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

    @BeforeAll
    void setUpPetConfig() {
        BaseConfig config = ConfigFactory.create(BaseConfig.class);

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(config.hostname())
                .build();

        petClient = new PetClient(requestSpecification);
    }
}
