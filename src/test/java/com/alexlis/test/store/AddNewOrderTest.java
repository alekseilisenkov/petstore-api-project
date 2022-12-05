package com.alexlis.test.store;

import com.alexlis.dto.store.request.AddAnOrderForRequest;
import com.alexlis.dto.store.response.CreateOrdersResponse;
import com.alexlis.helpers.BodyGenerator;
import com.alexlis.helpers.FakerData;
import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Store tests")
@Owner(value = "Lisenkov Alexey")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddNewOrderTest extends TestBase {

    @Test
    @Severity(SeverityLevel.MINOR)
    @Story("Story: Getting Orders")
    @Tags({@Tag("api"), @Tag("minor"), @Tag("store")})
    @DisplayName("Add a new order to the store, check creation ability")
    public void testAddNewOrder() {
        Allure.step("Step 1: Add an order", () -> {
            AddAnOrderForRequest addAnOrderForRequest = BodyGenerator.addNewOrder()
                    .withId(Integer.parseInt(FakerData.getRandomId()))
                    .please();

            createOrdersResponse = storeClient.addOrder(addAnOrderForRequest)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(CreateOrdersResponse.class);

            assertAll(
                    () -> assertThat(createOrdersResponse.getStatus()).isNotNull(),
                    () -> assertThat(createOrdersResponse.getId()).isNotNull(),
                    () -> assertThat(createOrdersResponse.isComplete()).isTrue()
            );
        });
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Story("Story: Getting Orders")
    @Tags({@Tag("api"), @Tag("minor"), @Tag("store")})
    @DisplayName("Add a new order to the store")
    public void addNewOrderCheckCreationTest() {
        Allure.step("Step 1: Add an order", () -> {
            AddAnOrderForRequest addAnOrderForRequest = BodyGenerator.addNewOrder()
                    .withId(Integer.parseInt(FakerData.getRandomId()))
                    .withQuantity(Integer.parseInt(FakerData.getRandomId()))
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
                    () -> assertThat(createOrdersResponse.isComplete()).isTrue()
            );
            id = createOrdersResponse.getId();
            idQuantity = createOrdersResponse.getQuantity();
        });

        Allure.step("Step 2: Get created order", () -> {
            createOrdersResponse = storeClient.findPurchaseOrder(id)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(CreateOrdersResponse.class);

            assertAll(
                    () -> assertThat(createOrdersResponse.getStatus()).isEqualTo("placed"),
                    () -> assertThat(createOrdersResponse.getQuantity()).isEqualTo(idQuantity),
                    () -> assertThat(createOrdersResponse.isComplete()).isEqualTo(true)
            );
        });
    }
}
