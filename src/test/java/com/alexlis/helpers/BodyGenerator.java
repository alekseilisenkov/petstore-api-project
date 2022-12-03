package com.alexlis.helpers;

import com.alexlis.dto.pet.request.AddNewPetToStoreRequest;
import com.alexlis.dto.pet.request.UpdateAnExistingPetRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BodyGenerator {

    public static AddNewPetToStoreRequest.AddNewPetToStoreRequestBuilder getAddingNewPet() {
        return AddNewPetToStoreRequest.builder();
    }

    public static UpdateAnExistingPetRequest.UpdateAnExistingPetRequestBuilder updateAnExistingPetRequest() {
        return UpdateAnExistingPetRequest.builder();
    }

    //TODO
//    public static AddAnOrderForRequest.AddAnOrderForRequestBuilder addNewOrder() {
//        return AddAnOrderForRequest.builder();
//    }
}
