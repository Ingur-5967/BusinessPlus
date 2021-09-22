package ru.solomka.business.commands.utils.methods.enums;

import lombok.Getter;

public enum UpdatePlayerType {

    HAVE_TWO_BUSINESS("TWO_BUSINESS"),
    HAVE_FOUR_BUSINESS("FOUR_BUSINESS"),
    HAVE_SIX_BUSINESS("SIX_BUSINESS");

    @Getter private final String str;

    UpdatePlayerType(String str) {
        this.str = str;
    }
}
