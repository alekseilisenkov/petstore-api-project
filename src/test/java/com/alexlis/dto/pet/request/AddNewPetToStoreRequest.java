package com.alexlis.dto.pet.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Collections;
import java.util.List;


@Data
@Builder(buildMethodName = "please", setterPrefix = "with")
public class AddNewPetToStoreRequest {

    @Builder.Default
    private final String name = "Simba";

    @Builder.Default
    private final int id = 100500;

    @Builder.Default
    private final String status = "available";

    @Builder.Default
    private final Category category = Category.builder().build();

    @Builder
    @Getter
    public static final class Category {

        @Builder.Default
        private final String name = "LION";

        @Builder.Default
        private final int id = 10;
    }

    @Builder.Default
    private final List<TagsItem> tags = Collections.singletonList(TagsItem.builder().build());

    @Builder
    @Getter
    public static final class TagsItem {

        @Builder.Default
        private final String name = "LION";

        @Builder.Default
        private final int id = 10;
    }

    @Builder.Default
    private final List<String> photoUrls = List.of("http:\\\\dogs");
}
