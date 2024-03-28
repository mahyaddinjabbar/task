package com.mahyaddin.task.domain.enums;

public enum idType {

    ID_CARD(0),
    PASSPORT(1);

    private final int value;

    idType(int value) {
        this.value = value;

    }
    public int getValue() {
        return value;
    }

}

