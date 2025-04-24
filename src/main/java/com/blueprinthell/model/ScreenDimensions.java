package com.blueprinthell.model;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public final class ScreenDimensions {
    private final double width;
    private final double height;

    private ScreenDimensions() {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        this.width = bounds.getWidth();
        this.height = bounds.getHeight();
    }

    private static class Holder {
        private static final ScreenDimensions INSTANCE = new ScreenDimensions();
    }

    public static ScreenDimensions getInstance() {
        return Holder.INSTANCE;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
