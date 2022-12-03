package com.alexlis.test.pet;

import com.alexlis.client.pet.PetClient;
import com.alexlis.config.BaseConfig;
import com.alexlis.dto.pet.request.AddNewPetToStoreRequest;
import com.alexlis.dto.pet.response.DeletePetResponse;
import com.alexlis.dto.pet.response.PetModelResponse;
import com.alexlis.helpers.BodyGenerator;
import com.alexlis.helpers.FakerData;
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
public class DeletePet {

    private String name;
    private int id;
    private PetClient petClient;
    private PetModelResponse petModelResponse;
    private BaseConfig config;
    private DeletePetResponse deletePetResponse;

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
    @DisplayName("Delete a new service.pet to the store")
    public void testDeleteNewPet() {
        Allure.step("Step 1: Add a new service.pet to the store", () -> {
            AddNewPetToStoreRequest addNewPetToStore = BodyGenerator.getAddingNewPet()
                    .withId(Integer.parseInt(FakerData.getRandomId()))
                    .withName(FakerData.getRandomName())
                    .withCategory(AddNewPetToStoreRequest.Category.builder()
                            .id(Integer.parseInt(FakerData.getRandomId()))
                            .name("Animal")
                            .build())
                    .please();

            petModelResponse = petClient.addPet(addNewPetToStore)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(PetModelResponse.class);

            assertAll(
                    () -> assertThat(petModelResponse.getCategory().getName()).isEqualTo("Animal")
            );

            name = petModelResponse.getName();
            id = petModelResponse.getId();
        });

        Allure.step("Step 2: Delete an existing pet", () -> {
            deletePetResponse = petClient.deletePet(id)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(DeletePetResponse.class);

            assertAll(
                    () -> assertThat(deletePetResponse.getCode()).isEqualTo(200),
                    () -> assertThat(deletePetResponse.getMessage()).isEqualTo("" + id),
                    () -> assertThat(deletePetResponse.getType()).isEqualTo("unknown")
            );
        });

        Allure.step("Step 3: Check deleted pet for existing", () -> {
            deletePetResponse = petClient.getPet(id)
                    .assertThat()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract().as(DeletePetResponse.class);

            assertAll(
                    () -> assertThat(deletePetResponse.getType()).isEqualTo("error"),
                    () -> assertThat(deletePetResponse.getMessage()).isEqualTo("Pet not found")
            );
        });
    }
}
