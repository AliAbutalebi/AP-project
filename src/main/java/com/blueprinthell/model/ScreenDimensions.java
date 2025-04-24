package com.blueprinthell.model;

public final class ScreenDimensions {
    private final int width;
    private final int height;

    public ScreenDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
