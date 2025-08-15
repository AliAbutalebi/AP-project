package com.blueprinthell.view;

import com.blueprinthell.model.ShapeType;
import com.blueprinthell.model.Wire;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class WireView extends Group {

    private Wire wire;
    private Shape wireShape;

    private static final double DEFAULT_STROKE_WIDTH = 3;
    private static final Color INVALID_COLOR = Color.web("#FF0000");
    private static final Color DRAGGING_COLOR = Color.web("#666666");
    private static final Color SQUARE_COLOR = Color.web("#32c65f");
    private static final Color TRIANGLE_COLOR = Color.web("#FFFF00");
    private static final Color HEXAGON_COLOR = Color.web("#EEEEEE");



    public WireView(Wire wire) {
        this.wire = wire;
        drawWire();
        getChildren().add(wireShape);
    }

    private void drawWire() {
        switch (wire.getControlPoints().size()) {
            case 0 -> draw0CPWire();
            case 1 -> draw1CPWire();
            case 2 -> draw2CPWire();
            case 3 -> draw3CPWire();
            default -> draw0CPWire();
        }

        if (wire.getSourcePort() != null) {
            switch (wire.getShapeType()) {
                case SQUARE -> markSquare();
                case TRIANGLE -> markTriangle();
                case HEXAGON -> markHexagon();
            }
        }

        wireShape.setStrokeWidth(DEFAULT_STROKE_WIDTH);
    }

    public void update() {
        getChildren().clear();
        drawWire();
        getChildren().add(wireShape);
    }

    private void draw0CPWire() {
        Line line = new Line();
        line.setStartX(wire.getStartLocation().getX());
        line.setStartY(wire.getStartLocation().getY());
        line.setEndX(wire.getEndLocation().getX());
        line.setEndY(wire.getEndLocation().getY());

        wireShape = line;
    }

    private void draw1CPWire() {
        QuadCurve quadCurve = new QuadCurve();
        quadCurve.setStartX(wire.getStartLocation().getX());
        quadCurve.setStartY(wire.getStartLocation().getY());
        quadCurve.setEndX(wire.getEndLocation().getX());
        quadCurve.setEndY(wire.getEndLocation().getY());
        quadCurve.setControlX(wire.getControlPoints().get(0).getX());
        quadCurve.setControlY(wire.getControlPoints().get(0).getY());

        wireShape = quadCurve;
    }

    private void draw2CPWire() {
        CubicCurve cubicCurve = new CubicCurve();
        cubicCurve.setStartX(wire.getStartLocation().getX());
        cubicCurve.setStartY(wire.getStartLocation().getY());
        cubicCurve.setEndX(wire.getEndLocation().getX());
        cubicCurve.setEndY(wire.getEndLocation().getY());
        cubicCurve.setControlX1(wire.getControlPoints().get(0).getX());
        cubicCurve.setControlY1(wire.getControlPoints().get(0).getY());
        cubicCurve.setControlX2(wire.getControlPoints().get(1).getX());
        cubicCurve.setControlY2(wire.getControlPoints().get(1).getY());

        wireShape = cubicCurve;
    }

    private void draw3CPWire() {
        Path path = new Path();
        path.getElements().add(new MoveTo(wire.getStartLocation().getX(), wire.getStartLocation().getY()));

        double midX = (wire.getControlPoints().get(1).getX() + wire.getControlPoints().get(2).getX()) / 2;
        double midY = (wire.getControlPoints().get(1).getY() + wire.getControlPoints().get(2).getY()) / 2;

        path.getElements().add(new CubicCurveTo(
                wire.getControlPoints().get(0).getX(), wire.getControlPoints().get(0).getY(),
                wire.getControlPoints().get(1).getX(), wire.getControlPoints().get(1).getY(),
                midX, midY
        ));

        path.getElements().add(new CubicCurveTo(
                2*midX - wire.getControlPoints().get(1).getX(), 2*midY - wire.getControlPoints().get(1).getY(),
                wire.getControlPoints().get(2).getX(), wire.getControlPoints().get(2).getY(),
                wire.getEndLocation().getX(), wire.getEndLocation().getY()
        ));

        wireShape = path;
    }


    public void markInvalid() {
        wireShape.setStroke(INVALID_COLOR);
    }

    public void markDragging() {
        wireShape.setStroke(DRAGGING_COLOR);
    }

    private void markSquare() {
        wireShape.setStroke(SQUARE_COLOR);
    }

    private void markTriangle() {
        wireShape.setStroke(TRIANGLE_COLOR);

    }

    private void markHexagon() {
        wireShape.setStroke(HEXAGON_COLOR);
    }

    public Wire getWire() {
        return wire;
    }
}