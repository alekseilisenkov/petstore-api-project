package com.alexlis.dto.store.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(buildMethodName = "please", setterPrefix = "with")
public class AddAnOrderForRequest {

    @Builder.Default
    private final int id = 0;

    @Builder.Default
    private final int petId = 0;

    @Builder.Default
    private final int quantity = 0;

    @Builder.Default
    private final String shipDate = "2022-09-21T04:58:13.345Z";

    @Builder.Default
    private final String status = "placed";

    @Builder.Default
    private final boolean complete = true;
}
