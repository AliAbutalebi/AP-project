package com.blueprinthell.view;

import com.blueprinthell.model.Wire;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.HashMap;

public class WireView extends Group {

    private Wire wire;
    private Shape wireShape;

    private final HashMap<Circle, Point2D> controlPointFromView = new HashMap<>();

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
        }

        wireShape.setOpacity(1 - ((double) wire.getPassedLargePackets() / (Wire.getPssedLargePacketLimit() + 1)));

        for (int i = 0; i < wire.getControlPoints().size(); i++) {
            ArrayList<Point2D> controlPoints = wire.getControlPoints();
            Circle circle = new Circle();
            circle.setCenterX(controlPoints.get(i).getX());
            circle.setCenterY(controlPoints.get(i).getY());
            circle.setRadius(DEFAULT_STROKE_WIDTH * 2);
            circle.setFill(Color.WHITE);
            controlPointFromView.put(circle, controlPoints.get(i));
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
        controlPointFromView.clear();
        drawWire();
        getChildren().add(0, wireShape);
        wire.setLength(getLength());
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

    public HashMap<Circle, Point2D> getControlPointFromView() {
        return controlPointFromView;
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
                2 * midX - wire.getControlPoints().get(1).getX(), 2 * midY - wire.getControlPoints().get(1).getY(),
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
        for (Circle circle : controlPointFromView.keySet()) {
            circle.setFill(SQUARE_COLOR);
        }
    }

    private void markTriangle() {
        wireShape.setStroke(TRIANGLE_COLOR);
        for (Circle circle : controlPointFromView.keySet()) {
            circle.setFill(TRIANGLE_COLOR);
        }

    }

    private void markHexagon() {
        wireShape.setStroke(HEXAGON_COLOR);
        for (Circle circle : controlPointFromView.keySet()) {
            circle.setFill(HEXAGON_COLOR);
        }
    }

    public Wire getWire() {
        return wire;
    }

    public double getLength() {
        if (wireShape == null) return 0;

        double length = 0;

        if (wireShape instanceof Line line) {
            length = line.getStartX() == line.getEndX() && line.getStartY() == line.getEndY()
                    ? 0
                    : Math.hypot(line.getEndX() - line.getStartX(), line.getEndY() - line.getStartY());

        } else if (wireShape instanceof QuadCurve quad) {
            Point2D start = new Point2D(quad.getStartX(), quad.getStartY());
            Point2D control = new Point2D(quad.getControlX(), quad.getControlY());
            Point2D end = new Point2D(quad.getEndX(), quad.getEndY());
            length = approximateQuadLength(start, control, end);

        } else if (wireShape instanceof CubicCurve cubic) {
            Point2D start = new Point2D(cubic.getStartX(), cubic.getStartY());
            Point2D c1 = new Point2D(cubic.getControlX1(), cubic.getControlY1());
            Point2D c2 = new Point2D(cubic.getControlX2(), cubic.getControlY2());
            Point2D end = new Point2D(cubic.getEndX(), cubic.getEndY());
            length = approximateCubicLength(start, c1, c2, end);

        } else if (wireShape instanceof Path path) {
            Point2D prev = null;
            for (PathElement elem : path.getElements()) {
                if (elem instanceof MoveTo move) {
                    prev = new Point2D(move.getX(), move.getY());
                } else if (elem instanceof CubicCurveTo cubic) {
                    Point2D start = prev;
                    Point2D c1 = new Point2D(cubic.getControlX1(), cubic.getControlY1());
                    Point2D c2 = new Point2D(cubic.getControlX2(), cubic.getControlY2());
                    Point2D end = new Point2D(cubic.getX(), cubic.getY());
                    length += approximateCubicLength(start, c1, c2, end);
                    prev = end;
                }
            }
        }

        return length;
    }

    private double approximateQuadLength(Point2D start, Point2D control, Point2D end) {
        double length = 0;
        Point2D prev = start;
        int steps = 100;
        for (int i = 1; i <= steps; i++) {
            double t = i / (double) steps;
            double x = Math.pow(1 - t, 2) * start.getX() + 2 * (1 - t) * t * control.getX() + Math.pow(t, 2) * end.getX();
            double y = Math.pow(1 - t, 2) * start.getY() + 2 * (1 - t) * t * control.getY() + Math.pow(t, 2) * end.getY();
            Point2D curr = new Point2D(x, y);
            length += curr.distance(prev);
            prev = curr;
        }
        return length;
    }

    private double approximateCubicLength(Point2D start, Point2D c1, Point2D c2, Point2D end) {
        double length = 0;
        Point2D prev = start;
        int steps = 100;
        for (int i = 1; i <= steps; i++) {
            double t = i / (double) steps;
            double x = Math.pow(1 - t, 3) * start.getX()
                    + 3 * Math.pow(1 - t, 2) * t * c1.getX()
                    + 3 * (1 - t) * Math.pow(t, 2) * c2.getX()
                    + Math.pow(t, 3) * end.getX();
            double y = Math.pow(1 - t, 3) * start.getY()
                    + 3 * Math.pow(1 - t, 2) * t * c1.getY()
                    + 3 * (1 - t) * Math.pow(t, 2) * c2.getY()
                    + Math.pow(t, 3) * end.getY();
            Point2D curr = new Point2D(x, y);
            length += curr.distance(prev);
            prev = curr;
        }
        return length;
    }

}