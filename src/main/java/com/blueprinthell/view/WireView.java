package com.blueprinthell.view;

import com.blueprinthell.model.Wire;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class WireView extends Line {

    private Wire wire;

    private static final double DEFAULT_STROKE_WIDTH = 2.0;
    private static final Color DEFAULT_COLOR = Color.GRAY;
    private static final Color ACTIVE_COLOR = Color.LIMEGREEN;
    private static final Color INVALID_COLOR = Color.RED;

    public WireView(Wire wire) {
        super(wire.getStartX(), wire.getStartY(), wire.getEndX(), wire.getEndY());

        this.wire = wire;
        initializeStyle();
    }

    private void initializeStyle() {
        setStroke(DEFAULT_COLOR);
        setStrokeWidth(DEFAULT_STROKE_WIDTH);
        setSmooth(true);
    }

    public void updateView() {
        setStartX(wire.getStartX());
        setStartY(wire.getStartY());
        setEndX(wire.getEndX());
        setEndY(wire.getEndY());
    }

    public void activate() {
        setStroke(ACTIVE_COLOR);
    }

    public void deactivate() {
        setStroke(DEFAULT_COLOR);
    }

    public void markInvalid() {
        setStroke(INVALID_COLOR);
    }

    public void markValid() {
        setStroke(DEFAULT_COLOR);
    }
}