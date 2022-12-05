package com.alexlis.test.pet;

import com.alexlis.allureannotations.JiraIssue;
import com.alexlis.allureannotations.Microservice;
import com.alexlis.dto.pet.request.AddNewPetToStoreRequest;
import com.alexlis.dto.pet.response.DeletePetResponse;
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
@Owner(value = "Lisenkov Alexey")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeletePetTest extends TestBase {

    @Test
    @JiraIssue("AUTO-1451")
    @Story("Story: Deleting Pets")
    @Severity(SeverityLevel.CRITICAL)
    @Tags({@Tag("api"), @Tag("critical"), @Tag("pet")})
    @DisplayName("Delete a new pet in the store and try to get it")
    public void testDeleteNewPet() {
        Allure.step("Step 1: Add a new pet to the store", () -> {
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
                    () -> assertThat(petModelResponse.getCategory().getName()).isEqualTo("Animal"),
                    () -> assertThat(petModelResponse.getId()).isEqualTo(addNewPetToStore.getId()),
                    () -> assertThat(petModelResponse.getCategory()).isNotNull()
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
            deletePetResponse = petClient.getDeletedPet(id)
                    .assertThat()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract().as(DeletePetResponse.class);

            assertAll(
                    () -> assertThat(deletePetResponse.getType()).isEqualTo("error"),
                    () -> assertThat(deletePetResponse.getMessage()).isEqualTo("Pet not found"),
                    () -> assertThat(deletePetResponse.getCode()).isEqualTo(1)
            );
        });
    }
}
