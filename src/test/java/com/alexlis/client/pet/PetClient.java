package com.alexlis.client.pet;

import com.alexlis.dto.pet.request.AddNewPetToStoreRequest;
import com.alexlis.dto.pet.request.UpdateAnExistingPetRequest;
import com.alexlis.helpers.CustomLogFilter;
import com.alexlis.helpers.EndPoints;
import com.alexlis.specs.Specs;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;

import static com.alexlis.specs.Specs.customDeleteResponseSpecification;
import static com.alexlis.specs.Specs.customResponseSpecification;
import static io.restassured.RestAssured.given;

@RequiredArgsConstructor
public class PetClient {

    private final RequestSpecification requestSpecification;

    @Step("Add a new pet to store")
    public ValidatableResponse addPet(final AddNewPetToStoreRequest bodyForNewPet) {
        return given()
                .spec(Specs.requestSpecification)
                .spec(requestSpecification)
                .body(bodyForNewPet)
                .when()
                .post(EndPoints.CONTEXT_PATH_PET)
                .then()
                .spec(customResponseSpecification);
    }

    @Step("Update pet")
    public ValidatableResponse updatePet(final UpdateAnExistingPetRequest bodyForUpdatingPet) {
        return given()
                .spec(Specs.requestSpecification)
                .spec(requestSpecification)
                .body(bodyForUpdatingPet)
                .when()
                .put(EndPoints.CONTEXT_PATH_PET)
                .then()
                .spec(customResponseSpecification);
    }

    @Step("Get pet")
    public ValidatableResponse getPet(final int petId) {
        return given()
                .spec(Specs.requestSpecification)
                .spec(requestSpecification)
                .when()
                .get(EndPoints.CONTEXT_PATH_PET + "/" + petId)
                .then()
                .spec(customResponseSpecification);
    }

    @Step("Get pet")
    public ValidatableResponse getDeletedPet(final int petId) {
        return given()
                .spec(Specs.requestSpecification)
                .spec(requestSpecification)
                .when()
                .get(EndPoints.CONTEXT_PATH_PET + "/" + petId)
                .then()
                .spec(customDeleteResponseSpecification);
    }

    @Step("Delete pet")
    public ValidatableResponse deletePet(final int petId) {
        return given()
                .spec(Specs.requestSpecification)
                .spec(requestSpecification)
                .when()
                .delete(EndPoints.CONTEXT_PATH_PET + "/" + petId)
                .then()
                .spec(customResponseSpecification);
    }
}
