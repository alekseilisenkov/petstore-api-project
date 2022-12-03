package com.alexlis.test.pet;

import com.alexlis.client.pet.PetClient;
import com.alexlis.config.BaseConfig;
import com.alexlis.dto.pet.request.AddNewPetToStoreRequest;
import com.alexlis.dto.pet.response.PetModelResponse;
import com.alexlis.helpers.BodyGenerator;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Add a new service.pet to the store")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddNewPetToStore {

    private PetClient petClient;
    private PetModelResponse petModelResponse;
    private BaseConfig config;

    @BeforeAll
    void setUp() {
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

    @Test
    @Owner(value = "Lisenkov Alexey")
    @DisplayName("Add a new service.pet to the store")
    public void testAddNewPetToStore() {
        Allure.step("Step 1: Create new pet", () -> {
            AddNewPetToStoreRequest addNewPetToStore = BodyGenerator.getAddingNewPet()
                    .withId(500)
                    .withName("John")
                    .withStatus("placed")
                    .withCategory(AddNewPetToStoreRequest.Category.builder()
                            .id(1515)
                            .name("Dima")
                            .build())
                    .please();

            petModelResponse = petClient.addPet(addNewPetToStore)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(PetModelResponse.class);

            assertAll(
                    () -> assertThat(petModelResponse.getCategory().getName()).withFailMessage("Name doesn't match").isEqualTo("Dima"),
                    () -> assertThat(petModelResponse.getCategory().getId()).isEqualTo(1515)
            );
        });
    }
}
