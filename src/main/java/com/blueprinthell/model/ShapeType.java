package com.blueprinthell.model;

public enum ShapeType {
    SQUARE,
    TRIANGLE,
    HEXAGON,
    CONFIDENTIAL_ONE,
    CONFIDENTIAL_TWO,
    LARGE_ONE,
    LARGE_TWO,
    BIT_PACKET;

    public static ShapeType fromString(String value) {
        return switch (value.toUpperCase()) {
            case "SQUARE" -> SQUARE;
            case "TRIANGLE" -> TRIANGLE;
            case "HEXAGON" -> HEXAGON;
            case "CONFIDENTIAL_ONE" -> CONFIDENTIAL_ONE;
            case "CONFIDENTIAL_TWO" -> CONFIDENTIAL_TWO;
            case "LARGE_ONE" -> LARGE_ONE;
            case "LARGE_TWO" -> LARGE_TWO;
            case "BIT_PACKET" -> BIT_PACKET;
            default -> throw new IllegalArgumentException("Unknown shape type: " + value);
        };
    }
}
