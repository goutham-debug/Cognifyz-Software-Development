package com.cognifyz.level2.task3;

/**
 * Task Priority levels.
 */
public enum Priority {
    LOW,
    MEDIUM,
    HIGH;

    public static Priority fromString(String val) {
        if (val == null) return MEDIUM;
        return switch (val.trim().toUpperCase()) {
            case "L", "LOW", "1" -> LOW;
            case "H", "HIGH", "3" -> HIGH;
            default -> MEDIUM;
        };
    }
}
