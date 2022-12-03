package com.alexlis.test.store;


import com.alexlis.client.store.StoreClient;
import com.alexlis.config.BaseConfig;
import com.alexlis.dto.store.request.AddAnOrderForRequest;
import com.alexlis.dto.store.response.CreateOrdersResponse;
import com.alexlis.helpers.BodyGenerator;
import com.alexlis.helpers.FakerData;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Purchase orders")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddNewOrder {

    private StoreClient storeClient;
    private CreateOrdersResponse createOrdersResponse;
    private BaseConfig config;
    private int id = Integer.parseInt(FakerData.getRandomId());
    private int idQuantity = Integer.parseInt(FakerData.getRandomId());

    @BeforeAll
    void setUp() {
        config = ConfigFactory.create(BaseConfig.class);

        RequestSpecification createOrder = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .setBaseUri(config.hostname())
                .build();

        storeClient = new StoreClient(createOrder);
    }

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
