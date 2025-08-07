package com.blueprinthell.model;

public enum ShapeType {
    SQUARE,
    TRIANGLE,
    HEXAGON,
    CONFIDENTIAL_ONE,
    CONFIDENTIAL_TWO;

    public static ShapeType fromString(String value) {
        return switch (value.toUpperCase()) {
            case "SQUARE" -> SQUARE;
            case "TRIANGLE" -> TRIANGLE;
            case "HEXAGON" -> HEXAGON;
            case "CONFIDENTIAL_ONE" -> CONFIDENTIAL_ONE;
            case "CONFIDENTIAL_TWO" -> CONFIDENTIAL_TWO;
            default -> throw new IllegalArgumentException("Unknown shape type: " + value);
        };
    }
}
