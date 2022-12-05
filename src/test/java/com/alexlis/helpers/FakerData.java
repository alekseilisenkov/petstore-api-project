package com.alexlis.helpers;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FakerData {

    private final static Faker FAKER = new Faker();

    public static String getRandomName() {
        return FAKER.regexify("[A-Z]{6}");
    }

    public static String getRandomId() {
        return FAKER.regexify("[0-9]{6}");
    }

    public static String getRandomWord() {
        return FAKER.regexify("TEST([A-Z]{1}[a-z]{6})");
    }
}
