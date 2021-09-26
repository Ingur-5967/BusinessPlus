package ru.solomka.business.commands.utils.methods.enums;

import lombok.Getter;

public enum BusinessClassType {

    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH");

    @Getter private final String type;

    BusinessClassType(String type) {
        this.type = type;
    }
}