package com.blueprinthell.model;

public enum ShapeType {
    SQUARE,
    TRIANGLE;

    public static ShapeType fromString(String value) {
        return switch (value.toUpperCase()) {
            case "SQUARE" -> SQUARE;
            case "TRIANGLE" -> TRIANGLE;
            default -> throw new IllegalArgumentException("Unknown shape type: " + value);
        };
    }
}
