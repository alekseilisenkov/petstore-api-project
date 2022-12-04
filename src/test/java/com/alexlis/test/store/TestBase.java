package com.alexlis.test.store;

import com.alexlis.client.store.StoreClient;
import com.alexlis.config.BaseConfig;
import com.alexlis.dto.store.response.CreateOrdersResponse;
import com.alexlis.dto.store.response.DeleteOrderResponse;
import com.alexlis.helpers.FakerData;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    protected StoreClient storeClient;
    protected CreateOrdersResponse createOrdersResponse;
    protected DeleteOrderResponse deleteOrderResponse;
    private BaseConfig config;
    protected int id = Integer.parseInt(FakerData.getRandomId());
    protected int idQuantity = Integer.parseInt(FakerData.getRandomId());

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
}
