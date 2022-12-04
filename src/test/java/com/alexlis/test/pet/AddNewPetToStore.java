package com.alexlis.test.pet;

import com.alexlis.dto.pet.request.AddNewPetToStoreRequest;
import com.alexlis.dto.pet.response.PetModelResponse;
import com.alexlis.helpers.BodyGenerator;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Add a new service.pet to the store")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddNewPetToStore extends TestBase {

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
                    () -> assertThat(petModelResponse.getName()).isEqualTo("John"),
                    () -> assertThat(petModelResponse.getCategory().getId()).isEqualTo(1515)
            );
        });
    }
}
