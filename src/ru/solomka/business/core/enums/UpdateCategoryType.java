package ru.solomka.business.core.enums;

import lombok.Getter;

public enum UpdateCategoryType {

    DOUBLE_MONEY("DOUBLE_MONEY"),
    STAFF("STAFF"),
    TAX("HALF_TAX"),
    HAVE_TWO_BUSINESS("TWO_BUSINESS"),
    HAVE_FOUR_BUSINESS("FOUR_BUSINESS"),
    HAVE_SIX_BUSINESS("SIX_BUSINESS");

    @Getter private final String update;

    UpdateCategoryType(String update) {
        this.update = update;
    }
}
