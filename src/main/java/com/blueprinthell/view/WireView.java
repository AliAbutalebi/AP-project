package com.blueprinthell.view;

import com.blueprinthell.model.Wire;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class WireView extends Line {

    private Wire wire;

    private static final double DEFAULT_STROKE_WIDTH = 3;
    private static final Color INVALID_COLOR = Color.RED;

    public WireView(Wire wire) {
        super(wire.getStartLocation().getX(), wire.getStartLocation().getY(), wire.getEndLocation().getX(), wire.getEndLocation().getY());

        this.wire = wire;
        initializeStyle();
    }

    private void initializeStyle() {
        setStrokeWidth(DEFAULT_STROKE_WIDTH);
        setSmooth(true);
    }

    public void updateView() {
        setStartX(wire.getStartLocation().getX());
        setStartY(wire.getStartLocation().getY());
        setEndX(wire.getEndLocation().getX());
        setEndY(wire.getEndLocation().getY());
    }

    public void markInvalid() {
        setStroke(INVALID_COLOR);
    }

    public void setWire(Wire wire) {
        this.wire = wire;
    }

    public Wire getWire() {
        return wire;
    }
}