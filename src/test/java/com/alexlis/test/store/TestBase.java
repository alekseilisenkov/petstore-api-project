package com.alexlis.test.store;

import com.alexlis.client.store.StoreClient;
import com.alexlis.config.BaseConfig;
import com.alexlis.dto.store.response.CreateOrdersResponse;
import com.alexlis.dto.store.response.DeleteOrderResponse;
import com.alexlis.helpers.FakerData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    protected StoreClient storeClient;
    protected CreateOrdersResponse createOrdersResponse;
    protected DeleteOrderResponse deleteOrderResponse;
    protected int id = Integer.parseInt(FakerData.getRandomId());
    protected int idQuantity = Integer.parseInt(FakerData.getRandomId());

    @BeforeAll
    void setUp() {
        BaseConfig config = ConfigFactory.create(BaseConfig.class);

        RequestSpecification createOrder = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(config.hostname())
                .build();

        storeClient = new StoreClient(createOrder);
    }
}
