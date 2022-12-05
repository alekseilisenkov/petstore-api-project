package com.alexlis.test.pet;

import com.alexlis.allureannotations.JiraIssue;
import com.alexlis.allureannotations.Microservice;
import com.alexlis.dto.pet.request.AddNewPetToStoreRequest;
import com.alexlis.dto.pet.response.PetModelResponse;
import com.alexlis.helpers.BodyGenerator;
import com.alexlis.helpers.FakerData;
import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Pet tests")
@Microservice("Swagger")
@Story("Story: Getting Pets")
@Owner(value = "Lisenkov Alexey")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddNewPetToStoreTests extends TestBase {

    @Test
    @JiraIssue("AUTO-1451")
    @Severity(SeverityLevel.MINOR)
    @Tags({@Tag("api"), @Tag("minor"), @Tag("pet")})
    @DisplayName("Add a new pet in the store and try to get it")
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
                    () -> assertThat(petModelResponse.getName()).isEqualTo("John"),
                    () -> assertThat(petModelResponse.getCategory().getId()).isEqualTo(1515)
            );
            id = petModelResponse.getId();
        });

        Allure.step("Step 2: Search created pet", () -> {
            petModelResponse = petClient.getPet(id)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(PetModelResponse.class);

            assertAll(
                    () -> assertThat(petModelResponse.getCategory().getName()).isEqualTo("Dima"),
                    () -> assertThat(petModelResponse.getCategory().getId()).isEqualTo(1515),
                    () -> assertThat(petModelResponse.getId()).isEqualTo(500)
            );
        });
    }

    @Test
    @JiraIssue("AUTO-1451")
    @Severity(SeverityLevel.MINOR)
    @Tags({@Tag("api"), @Tag("minor"), @Tag("pet")})
    @DisplayName("Add a new pet in the store and try to get it")
    public void testAddNewRandomPetToStore() {
        Allure.step("Step 1: Create new pet", () -> {
            AddNewPetToStoreRequest addNewPetToStore = BodyGenerator.getAddingNewPet()
                    .withId(Integer.parseInt(FakerData.getRandomId()))
                    .withName(FakerData.getRandomName())
                    .withStatus(FakerData.getRandomWord())
                    .withCategory(AddNewPetToStoreRequest.Category.builder()
                            .id(Integer.parseInt(FakerData.getRandomId()))
                            .name(FakerData.getRandomWord())
                            .build())
                    .please();

            petModelResponse = petClient.addPet(addNewPetToStore)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(PetModelResponse.class);

            assertAll(
                    () -> assertThat(petModelResponse.getCategory().getName()).isNotNull(),
                    () -> assertThat(petModelResponse.getName()).isEqualTo(addNewPetToStore.getName()),
                    () -> assertThat(petModelResponse.getCategory().getId()).isEqualTo(addNewPetToStore.getCategory().getId())
            );
        });

        Allure.step("Step 2: Search created pet", () -> {
            petModelResponse = petClient.getPet(petModelResponse.getId())
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(PetModelResponse.class);

            assertAll(
                    () -> assertThat(petModelResponse.getCategory().getName()).isNotNull(),
                    () -> assertThat(petModelResponse.getCategory().getId()).isNotNull(),
                    () -> assertThat(petModelResponse.getId()).isNotNull()
            );
        });
    }
}
