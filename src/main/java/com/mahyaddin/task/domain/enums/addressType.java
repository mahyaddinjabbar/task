package com.mahyaddin.task.domain.enums;

public enum addressType {
    HOME(0),
    DECLARED(1);

    private final int value;

    addressType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
