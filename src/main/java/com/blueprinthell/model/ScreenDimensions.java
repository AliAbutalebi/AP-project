package com.blueprinthell.model;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public final class ScreenDimensions {
    private static ScreenDimensions INSTANCE;
    private final double WIDTH;
    private final double HEIGHT;

    private ScreenDimensions() {
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        this.WIDTH = bounds.getWidth();
        this.HEIGHT = bounds.getHeight();
    }

    public static ScreenDimensions getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ScreenDimensions();
        }
        return INSTANCE;
    }


    public double getWidth() {
        return WIDTH;
    }

    public double getHeight() {
        return HEIGHT;
    }
}
