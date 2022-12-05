package com.alexlis.test.store;

import com.alexlis.allureannotations.JiraIssue;
import com.alexlis.allureannotations.Microservice;
import com.alexlis.dto.store.request.AddAnOrderForRequest;
import com.alexlis.dto.store.response.CreateOrdersResponse;
import com.alexlis.dto.store.response.DeleteOrderResponse;
import com.alexlis.helpers.BodyGenerator;
import com.alexlis.helpers.FakerData;
import io.qameta.allure.*;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Store tests")
@Microservice("Swagger")
@Severity(SeverityLevel.MINOR)
@Owner(value = "Lisenkov Alexey")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteOrderTest extends TestBase {

    @Test
    @JiraIssue("AUTO-1451")
    @Story("Story: Deleting Orders")
    @Severity(SeverityLevel.BLOCKER)
    @Tags({@Tag("api"), @Tag("blocker"), @Tag("store")})
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
                    () -> assertThat(createOrdersResponse.isComplete()).isTrue(),
                    () -> assertThat(createOrdersResponse.getId()).isEqualTo(addAnOrderForRequest.getId()),
                    () -> assertThat(createOrdersResponse.getId()).isEqualTo(addAnOrderForRequest.getId())
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
                    () -> assertThat(deleteOrderResponse.getType()).isEqualTo("unknown"),
                    () -> assertThat(deleteOrderResponse.getMessage()).isNotNull()
            );
        });

        Allure.step("Step 3: Get deleted order", () -> {
            deleteOrderResponse = storeClient.findDeletedPurchaseOrder(id)
                    .assertThat()
                    .statusCode(HttpStatus.SC_NOT_FOUND)
                    .extract().as(DeleteOrderResponse.class);

            assertAll(
                    () -> assertThat(deleteOrderResponse.getType()).isEqualTo("error")
            );
        });
    }
}
