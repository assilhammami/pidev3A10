package com.esprit.models;

public enum NoteCours {
    UNE(1),
    DEUX(2),
    TROIS(3),
    QUATRE(4),
    CINQ(5);

    private final int value;

    NoteCours(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

