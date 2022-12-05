package com.alexlis.specs;

import com.alexlis.helpers.CustomLogFilter;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class Specs {

    public static RequestSpecification requestSpecification = with()
            .filter(CustomLogFilter.customLogFilter().withCustomTemplates())
            .log().uri()
            .log().body()
            .contentType(ContentType.JSON);

    public static ResponseSpecification customResponseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.BODY)
            .build();

    public static ResponseSpecification customDeleteResponseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .log(LogDetail.BODY)
            .build();
}
