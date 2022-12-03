package com.alexlis.client.pet;

import com.alexlis.dto.pet.request.AddNewPetToStoreRequest;
import com.alexlis.dto.pet.request.UpdateAnExistingPetRequest;
import com.alexlis.helpers.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;

import static io.restassured.RestAssured.given;

@RequiredArgsConstructor
public class PetClient {

private final RequestSpecification requestSpecification;

    @Step("Add a new pet to store")
    public ValidatableResponse addPet(final AddNewPetToStoreRequest bodyForNewPet) {
        return given()
                .spec(requestSpecification)
                .body(bodyForNewPet)
                .when()
                .post(EndPoints.CONTEXT_PATH_PET)
                .then();
    }

    @Step("Update an existing pet")
    public ValidatableResponse updatePet(final UpdateAnExistingPetRequest bodyForUpdatingPet) {
        return given()
                .spec(requestSpecification)
                .body(bodyForUpdatingPet)
                .when()
                .put(EndPoints.CONTEXT_PATH_PET)
                .then();
    }

    @Step("Получение животного")
    public ValidatableResponse getPet(final int petId) {
        return given()
                .spec(requestSpecification)
                .when()
                .get(EndPoints.CONTEXT_PATH_PET + "/" + petId)
                .then();
    }

    @Step("Удаление животного")
    public ValidatableResponse deletePet(final int petId) {
        return given()
                .spec(requestSpecification)
                .when()
                .delete(EndPoints.CONTEXT_PATH_PET + "/" + petId)
                .then();
    }
}
