package com.alexlis.test.store;

import com.alexlis.dto.store.request.AddAnOrderForRequest;
import com.alexlis.dto.store.response.CreateOrdersResponse;
import com.alexlis.dto.store.response.DeleteOrderResponse;
import com.alexlis.helpers.BodyGenerator;
import com.alexlis.helpers.FakerData;
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
public class DeleteOrder extends TestBase {

    @Test
    @Owner(value = "Lisenkov Alexey")
    @DisplayName("Delete created order to the store and try to get it")
    public void testDeleteCreatedOrder() {
        Allure.step("Step 1: Add a new order to the store", () -> {
            AddAnOrderForRequest addAnOrderForRequest = BodyGenerator.addNewOrder()
                    .withId(Integer.parseInt(FakerData.getRandomId()))
                    .please();

            createOrdersResponse = storeClient.addOrder(addAnOrderForRequest)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(CreateOrdersResponse.class);

            assertAll(
                    () -> assertThat(createOrdersResponse.isComplete()).isTrue()
            );
            id = createOrdersResponse.getId();
        });

        Allure.step("Step 2: Delete an existing order", () -> {
            deleteOrderResponse = storeClient.deleteOrder(id)
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK)
                    .extract().as(DeleteOrderResponse.class);

            assertAll(
                    () -> assertThat(deleteOrderResponse.getCode()).isEqualTo(200),
                    () -> assertThat(deleteOrderResponse.getType()).isEqualTo("unknown")
            );
        });

        Allure.step("Step 3: Get deleted order", () -> {
            deleteOrderResponse = storeClient.findPurchaseOrder(id)
                    .assertThat()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract().as(DeleteOrderResponse.class);

            assertAll(
                    () -> assertThat(deleteOrderResponse.getType()).isEqualTo("error")
            );
        });
    }
}
