package com.alexlis.test.store;

import com.alexlis.dto.store.request.AddAnOrderForRequest;
import com.alexlis.dto.store.response.CreateOrdersResponse;
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

@Epic("Purchase orders")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddNewOrder extends TestBase{

    @Test
    @Owner(value = "Lisenkov Alexey")
    @DisplayName("Add a new order to the store and get it")
    public void testAddNewOrder() {
        Allure.step("Step 1: Delete an existing order", () -> {
            AddAnOrderForRequest addAnOrderForRequest = BodyGenerator.addNewOrder()
                    .withId(id)
                    .withQuantity(idQuantity)
                    .withStatus("placed")
                    .withComplete(true)
                    .please();

            createOrdersResponse = storeClient.addOrder(addAnOrderForRequest)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(CreateOrdersResponse.class);

            assertAll(
                    () -> assertThat(createOrdersResponse.getStatus()).isEqualTo("placed"),
                    () -> assertThat(createOrdersResponse.getId()).isEqualTo(addAnOrderForRequest.getId()),
                    () -> assertThat(createOrdersResponse.getShipDate()).isEqualTo("2022-09-21T04:58:13.345+0000"),
                    () -> assertThat(createOrdersResponse.isComplete()).isTrue(),
                    () -> assertThat(createOrdersResponse.isComplete()).isTrue()
            );
            id = createOrdersResponse.getId();
        });

        Allure.step("Step 2: Get an existing order", () -> {
            createOrdersResponse = storeClient.findPurchaseOrder(id)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(CreateOrdersResponse.class);

            assertAll(
                    () -> assertThat(createOrdersResponse.getStatus()).isEqualTo("placed")
            );
        });
    }
}
