package com.example.orchestrator.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Currency {
    USD,
    EUR,
    KRW,
    JPY;

    @JsonCreator
    public static Currency fromValue(String value) {
        try {
            return Currency.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency. Allowed values are: USD, EUR, KRW, JPY.");
        }
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
