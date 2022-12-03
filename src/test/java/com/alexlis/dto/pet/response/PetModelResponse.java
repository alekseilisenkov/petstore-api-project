package com.alexlis.dto.pet.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetModelResponse {

    private String name;
    private int id;
    private String status;
    private Category category;
    private List<TagsItem> tags;
    private List<String> photoUrls;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class TagsItem {

        private String name;
        private int id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Category {

        private String name;
        private int id;
    }
}
