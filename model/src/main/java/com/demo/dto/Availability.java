package com.demo.dto;

import java.util.HashMap;
import java.util.Map;

public enum Availability {

    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    private static final Map<String, Availability> TYPES = new HashMap<>();

    static {
        for (final Availability type : values()) {
            TYPES.put(type.value, type);
        }
    }

    private final String value;

    Availability(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isType(final String value) {
        return TYPES.containsKey(value);
    }

    public static Availability fromValue(final String value) {
        if (!TYPES.containsKey(value)) {
            throw new IllegalArgumentException("Invalid availability value " + value);
        }
        return TYPES.get(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
