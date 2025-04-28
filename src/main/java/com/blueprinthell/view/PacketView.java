package com.blueprinthell.view;

import com.blueprinthell.model.Packet;
import com.blueprinthell.model.SquarePacket;
import com.blueprinthell.model.TrianglePacket;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PacketView extends Polygon {

    private Packet packet;
    private static final double SIZE = 10;

    public PacketView(Packet packet) {
        this.packet = packet;
        setupShape();
    }

    private void setupShape() {
        if (packet instanceof SquarePacket) {
            double halfSize = SIZE / 2;
            getPoints().addAll(
                    -halfSize, -halfSize,
                    halfSize, -halfSize,
                    halfSize, halfSize,
                    -halfSize, halfSize
            );
            setFill(Color.CYAN);
            setStroke(Color.BLACK);
            setStrokeWidth(1);
        }
        else if (packet instanceof TrianglePacket) {
            double height = Math.sqrt(3) / 2 * SIZE;
            double halfBase = SIZE / 2;
            getPoints().addAll(
                    -halfBase, height / 2,
                    halfBase, height / 2,
                    0.0, -height / 2
            );
            setFill(Color.ORANGE);
            setStroke(Color.BLACK);
            setStrokeWidth(1);
        }
    }

    public void updateView() {
        setLayoutX(packet.getPosition().getWidth());
        setLayoutY(packet.getPosition().getHeight());
    }
}
