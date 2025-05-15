package com.blueprinthell.model;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public final class ScreenDimensions {
    private static ScreenDimensions instance;
    private final double WIDTH;
    private final double HEIGHT;

    private ScreenDimensions() {
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        this.WIDTH = bounds.getWidth();
        this.HEIGHT = bounds.getHeight();
    }

    public static ScreenDimensions getInstance() {
        if(instance == null) {
            instance = new ScreenDimensions();
        }
        return instance;
    }


    public double getWidth() {
        return WIDTH;
    }

    public double getHeight() {
        return HEIGHT;
    }
}
