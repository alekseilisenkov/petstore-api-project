package com.alexlis.client.store;

import com.alexlis.dto.store.request.AddAnOrderForRequest;
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
public class StoreClient {

    private final RequestSpecification requestSpecification;

    @Step("Place an order for a pet")
    public ValidatableResponse addOrder(final AddAnOrderForRequest bodyForNewOrder) {
        return given()
                .spec(Specs.requestSpecification)
                .spec(requestSpecification)
                .body(bodyForNewOrder)
                .when()
                .post(EndPoints.CONTEXT_PATH_STORE)
                .then()
                .spec(customResponseSpecification);
    }

    @Step("Find purchase order by Id")
    public ValidatableResponse findPurchaseOrder(final int orderId) {
        return given()
                .spec(Specs.requestSpecification)
                .spec(requestSpecification)
                .when()
                .get(EndPoints.CONTEXT_PATH_STORE + "/" + orderId)
                .then()
                .spec(customResponseSpecification);
    }

    @Step("Find purchase order by Id")
    public ValidatableResponse findDeletedPurchaseOrder(final int orderId) {
        return given()
                .spec(Specs.requestSpecification)
                .spec(requestSpecification)
                .when()
                .get(EndPoints.CONTEXT_PATH_STORE + "/" + orderId)
                .then()
                .spec(customDeleteResponseSpecification);
    }

    @Step("Delete purchase order by Id")
    public ValidatableResponse deleteOrder(final int orderId) {
        return given()
                .spec(Specs.requestSpecification)
                .spec(requestSpecification)
                .when()
                .delete(EndPoints.CONTEXT_PATH_STORE + "/" + orderId)
                .then()
                .spec(customResponseSpecification);
    }
}
