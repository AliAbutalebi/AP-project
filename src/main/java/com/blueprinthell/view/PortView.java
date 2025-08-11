package com.blueprinthell.view;

import com.blueprinthell.model.Port;
import com.blueprinthell.model.ShapeType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class PortView extends Polygon {

    private Port port;
    private static final double PORT_SIZE = 20;

    public PortView(Port port) {
        this.port = port;
        setupShape();
    }

    private void setupShape() {
        if (port.getShapeType() == ShapeType.SQUARE) createSquare();
        else if (port.getShapeType() == ShapeType.TRIANGLE) createTriangle();
        else if (port.getShapeType() == ShapeType.HEXAGON) createHexagon();
        setStrokeWidth(3);
        switch (port.isInput()) {
            case true:
                setStroke(Color.web("#000000"));
                break;
            case false:
                setStroke(Color.web("#666666"));
                break;
        }
    }

    public void updateView() {

    }

    public Port getPort() {
        return port;
    }

    public void saveLocation() {
        Point2D location = localToScene(0, 0);
        port.setLocation(location);
    }

    private void createSquare() {
        getPoints().addAll(createPolygon(4, PORT_SIZE / 2).getPoints());
        setFill(Color.web("#32c65f"));
    }

    private void createTriangle() {
        getPoints().addAll(createPolygon(3, PORT_SIZE / 2).getPoints());
        setFill(Color.web("#FFFF00"));
    }

    private void createHexagon() {
        getPoints().addAll(createPolygon(6, PORT_SIZE / 2).getPoints());
        setFill(Color.web("#EEEEEE"));
    }

    private Polygon createPolygon(int vertexCount, double radius) {
        if (vertexCount < 3) return null;

        Polygon polygon = new Polygon();
        double angleStep = 2 * Math.PI / vertexCount;

        for (int i = 0; i < vertexCount; i++) {
            double angle = angleStep * i - Math.PI / 2;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            polygon.getPoints().addAll(x, y);
        }

        return polygon;
    }
}
