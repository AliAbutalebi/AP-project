package com.blueprinthell.view;

import com.blueprinthell.model.Port;
import com.blueprinthell.model.ShapeType;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PortView extends Polygon {

    private Port port;
    private static final double PORT_SIZE = 15;

    public PortView(Port port) {
        this.port = port;
        setupShape();
    }

    private void setupShape() {
        if (port.getShapeType() == ShapeType.SQUARE) {
            double halfSize = PORT_SIZE / 2;
            getPoints().addAll(
                    -halfSize, -halfSize,
                    halfSize, -halfSize,
                    halfSize, halfSize,
                    -halfSize, halfSize
            );
            setFill(Color.web("#00FF00"));
            setStrokeWidth(2);
        }
        else if (port.getShapeType() == ShapeType.TRIANGLE) {
            double height = Math.sqrt(3) / 2 * PORT_SIZE;
            double halfBase = PORT_SIZE / 2;
            getPoints().addAll(
                    -halfBase, height / 2,
                    halfBase, height / 2,
                    0.0, -height / 2
            );
            setFill(Color.web("#FFFF00"));
            setStrokeWidth(2);
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
}
