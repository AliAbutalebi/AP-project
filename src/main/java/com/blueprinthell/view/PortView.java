package com.blueprinthell.view;

import com.blueprinthell.model.Port;
import com.blueprinthell.model.ShapeType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class PortView extends Polygon {

    private Port port;
    private static final double PORT_SIZE = 350;

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

    private Polygon createPolygon(int n, double area) {
        if (n < 3) return null;

        double R = Math.sqrt((2 * area) / (n * Math.sin(2 * Math.PI / n)));

        Polygon polygon = new Polygon();
        double angleOffset = -Math.PI / 2;
        for (int i = 0; i < n; i++) {
            double theta = angleOffset + 2 * Math.PI * i / n;
            polygon.getPoints().add(R * Math.cos(theta));
            polygon.getPoints().add(R * Math.sin(theta));
        }
        return polygon;
    }
}
