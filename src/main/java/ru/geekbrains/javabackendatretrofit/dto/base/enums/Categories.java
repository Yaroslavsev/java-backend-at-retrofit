package ru.geekbrains.javabackendatretrofit.dto.base.enums;

import lombok.Getter;

public enum Categories {
    FOOD(1, "Food"),
    ELECTRONIC(2, "Electronic");

    @Getter
    private final Integer id;
    @Getter
    private final String title;

    Categories(Integer id, String title) {
        this.id = id;
        this.title = title;
    }
}
