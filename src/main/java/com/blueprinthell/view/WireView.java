package com.blueprinthell.view;

import com.blueprinthell.model.Port;
import com.blueprinthell.model.SquarePort;
import com.blueprinthell.model.TrianglePort;
import com.blueprinthell.model.Wire;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class WireView extends Line {

    private Wire wire;

    private static final double DEFAULT_STROKE_WIDTH = 3;
    private static final Color DEFAULT_COLOR = Color.GRAY;
    private static final Color ACTIVE_COLOR = Color.LIMEGREEN;
    private static final Color INVALID_COLOR = Color.RED;

    public WireView(Wire wire) {
        super(wire.getStartLocation().getX(), wire.getStartLocation().getY(), wire.getEndLocation().getX(), wire.getEndLocation().getY());

        this.wire = wire;
        initializeStyle();
    }

    private void initializeStyle() {
        setStroke(DEFAULT_COLOR);
        setStrokeWidth(DEFAULT_STROKE_WIDTH);
        setSmooth(true);
    }

    public void updateView() {
        setStartX(wire.getStartLocation().getX());
        setStartY(wire.getStartLocation().getY());
        setEndX(wire.getEndLocation().getX());
        setEndY(wire.getEndLocation().getY());
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

    public void setColor() {
        if (getWire().getSourcePort() instanceof SquarePort && getWire().getDestinationPort() instanceof SquarePort) {
            setStroke(Color.web("#00FF00"));
        }
        else if (getWire().getSourcePort() instanceof TrianglePort && getWire().getDestinationPort() instanceof TrianglePort) {
            setStroke(Color.web("#FFFF00"));

        }
    }

    public void setWire(Wire wire) {
        this.wire = wire;
    }

    public Wire getWire() {
        return wire;
    }
}