package com.blueprinthell.model;

public enum ShapeType {
    SQUARE,
    TRIANGLE,
    HEXAGON;

    public static ShapeType fromString(String value) {
        return switch (value.toUpperCase()) {
            case "SQUARE" -> SQUARE;
            case "TRIANGLE" -> TRIANGLE;
            case "HEXAGON" -> HEXAGON;
            default -> throw new IllegalArgumentException("Unknown shape type: " + value);
        };
    }
}
