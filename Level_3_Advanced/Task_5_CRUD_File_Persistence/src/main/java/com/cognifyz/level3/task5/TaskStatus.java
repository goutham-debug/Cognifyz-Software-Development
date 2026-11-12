package com.cognifyz.level3.task5;

/**
 * Task lifecycle status enumeration.
 */
public enum TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED;

    public static TaskStatus fromString(String val) {
        if (val == null) return PENDING;
        return switch (val.trim().toUpperCase()) {
            case "P", "PENDING", "1" -> PENDING;
            case "I", "IN_PROGRESS", "2" -> IN_PROGRESS;
            case "C", "COMPLETED", "3" -> COMPLETED;
            default -> PENDING;
        };
    }
}
