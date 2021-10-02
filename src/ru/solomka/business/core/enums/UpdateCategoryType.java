package ru.solomka.business.core.enums;

import lombok.Getter;

public enum UpdateCategoryType {

    DOUBLE_MONEY("DOUBLE_MONEY"),
    REDUCTION_TAX("REDUCTION_TAX"),
    SELL_STARTING_PRICE("START_PRICE_SELL"),
    HAVE_TWO_BUSINESS("TWO_BUSINESS"),
    HAVE_FOUR_BUSINESS("FOUR_BUSINESS"),
    HAVE_SIX_BUSINESS("SIX_BUSINESS");

    @Getter private final String str;

    UpdateCategoryType(String str) {
        this.str = str;
    }
}
