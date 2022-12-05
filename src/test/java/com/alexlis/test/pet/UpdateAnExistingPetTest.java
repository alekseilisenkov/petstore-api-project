package com.alexlis.test.pet;

import com.alexlis.dto.pet.request.AddNewPetToStoreRequest;
import com.alexlis.dto.pet.request.UpdateAnExistingPetRequest;
import com.alexlis.dto.pet.response.PetModelResponse;
import com.alexlis.helpers.BodyGenerator;
import com.alexlis.helpers.FakerData;
import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Pet tests")
@Owner(value = "Lisenkov Alexey")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdateAnExistingPetTest extends TestBase {

    @Test
    @Story("Story: Updating Pets")
    @Severity(SeverityLevel.BLOCKER)
    @Tags({@Tag("api"), @Tag("blocker"), @Tag("pet")})
    @DisplayName("Update a new pet in the store and try to get it")
    public void testAddNewPetToStore() {
        Allure.step("Step 1: Add a new service.pet to the store", () -> {
            AddNewPetToStoreRequest addNewPetToStore = BodyGenerator.getAddingNewPet()
                    .withId(Integer.parseInt(FakerData.getRandomId()))
                    .withName(FakerData.getRandomName())
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
                    () -> assertThat(petModelResponse.getCategory().getName()).isEqualTo(addNewPetToStore.getCategory().getName()),
                    () -> assertThat(petModelResponse.getCategory().getId()).isEqualTo(addNewPetToStore.getCategory().getId()),
                    () -> assertThat(petModelResponse.getId()).isEqualTo(addNewPetToStore.getId())
            );
            name = petModelResponse.getName();
            id = petModelResponse.getId();
        });

        Allure.step("Step 2: Update an existing pet", () -> {
            UpdateAnExistingPetRequest updateAnExistingPetRequest = BodyGenerator.updateAnExistingPetRequest()
                    .withId(id)
                    .withName(name)
                    .withCategory(UpdateAnExistingPetRequest.Category.builder()
                            .id(1515)
                            .name("Example")
                            .build())
                    .please();

            petModelResponse = petClient.updatePet(updateAnExistingPetRequest)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(PetModelResponse.class);

            assertAll(
                    () -> assertThat(petModelResponse.getCategory().getName()).isEqualTo("Example"),
                    () -> assertThat(petModelResponse.getCategory().getId()).isEqualTo(1515),
                    () -> assertThat(petModelResponse.getId()).isEqualTo(id)
            );
        });

        Allure.step("Step 3: Search updated pet", () -> {
            petModelResponse = petClient.getPet(id)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(PetModelResponse.class);

            assertAll(
                    () -> assertThat(petModelResponse.getCategory().getName()).isEqualTo("Example"),
                    () -> assertThat(petModelResponse.getCategory().getId()).isEqualTo(1515),
                    () -> assertThat(petModelResponse.getName()).isEqualTo(name)
            );
        });
    }
}
