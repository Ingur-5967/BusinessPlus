package ru.solomka.business.commands.utils.methods.enums;

import lombok.Getter;

public enum UpdateBusinessType {

    DOUBLE_MONEY("DOUBLE_MONEY"),
    REDUCTION_TAX("REDUCTION_TAX"),
    SELL_STARTING_PRICE("START_PRICE_SELL");

    @Getter private final String str;

    UpdateBusinessType(String str) {
        this.str = str;
    }
}
