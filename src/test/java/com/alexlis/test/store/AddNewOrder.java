package com.alexlis.test.store;

import com.alexlis.dto.store.request.AddAnOrderForRequest;
import com.alexlis.dto.store.response.CreateOrdersResponse;
import com.alexlis.helpers.BodyGenerator;
import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Store tests")
@Owner(value = "Lisenkov Alexey")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddNewOrder extends TestBase{

    @Test
//    @AllureId("")
    @Story("Story: Getting Orders")
    @Severity(SeverityLevel.MINOR)
    @Tags({@Tag("api"), @Tag("minor"), @Tag("store")})
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
