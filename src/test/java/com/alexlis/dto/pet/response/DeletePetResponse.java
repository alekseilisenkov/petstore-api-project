package com.alexlis.dto.pet.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletePetResponse {

    private int code;
    private String type;
    private String message;
}
