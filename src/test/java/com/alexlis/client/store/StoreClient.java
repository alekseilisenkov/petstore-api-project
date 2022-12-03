package com.alexlis.client.store;

import com.alexlis.dto.store.request.AddAnOrderForRequest;
import com.alexlis.helpers.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;

import static io.restassured.RestAssured.given;

@RequiredArgsConstructor
public class StoreClient {

    private final RequestSpecification requestSpecification;

    @Step("Place an order for a pet")
    public ValidatableResponse addOrder(final AddAnOrderForRequest bodyForNewOrder) {
        return given()
                .spec(requestSpecification)
                .body(bodyForNewOrder)
                .when()
                .post(EndPoints.CONTEXT_PATH_STORE)
                .then();
    }

    @Step("Find purchase order by Id")
    public ValidatableResponse findPurchaseOrder(final int orderId) {
        return given()
                .spec(requestSpecification)
                .when()
                .get(EndPoints.CONTEXT_PATH_STORE + "/" + orderId)
                .then();
    }

    @Step("Delete purchase order by Id")
    public ValidatableResponse deleteOrder(final int orderId) {
        return given()
                .spec(requestSpecification)
                .when()
                .delete(EndPoints.CONTEXT_PATH_STORE + "/" + orderId)
                .then();
    }
}
