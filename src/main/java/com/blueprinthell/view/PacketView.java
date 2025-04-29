package com.blueprinthell.view;

import com.blueprinthell.model.Packet;
import com.blueprinthell.model.SquarePacket;
import com.blueprinthell.model.TrianglePacket;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PacketView extends Polygon {

    private Packet packet;
    private static final double PACKET_SIZE = 10;

    public PacketView(Packet packet) {
        this.packet = packet;
        setupShape();
    }

    private void setupShape() {
        if (packet instanceof SquarePacket) {
            double halfSize = PACKET_SIZE / 2;
            getPoints().addAll(
                    -halfSize, -halfSize,
                    halfSize, -halfSize,
                    halfSize, halfSize,
                    -halfSize, halfSize
            );
            setFill(Color.TRANSPARENT);
            setStroke(Color.web("#00FF00"));
            setStrokeWidth(2);
        }
        else if (packet instanceof TrianglePacket) {
            double height = Math.sqrt(3) / 2 * PACKET_SIZE;
            double halfBase = PACKET_SIZE / 2;
            getPoints().addAll(
                    -halfBase, height / 2,
                    halfBase, height / 2,
                    0.0, -height / 2
            );
            setFill(Color.TRANSPARENT);
            setStroke(Color.web("#FFFF00"));
            setStrokeWidth(2);
        }
    }

    public void updateView() {
        setLayoutX(packet.getLocation().getX());
        setLayoutY(packet.getLocation().getY());
    }

    public void saveLocation() {
        Point2D location = localToScene(0, 0);
    }
}
