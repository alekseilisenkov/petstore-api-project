package com.alexlis.dto.pet.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePetRequest {
    private List<String> photoUrls;
    private String name;
    private int id;
    private CategoryAndTagsItem categoryAndTagsItem;
    private List<CategoryAndTagsItem> tags;
    private String status;

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public CategoryAndTagsItem getCategory() {
        return categoryAndTagsItem;
    }

    public List<CategoryAndTagsItem> getTags() {
        return tags;
    }

    public String getStatus() {
        return status;
    }

    public static class CategoryAndTagsItem {
        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }
}