package com.blueprinthell.view;

import com.blueprinthell.model.Packet;
import com.blueprinthell.model.ShapeType;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class PacketView extends Polygon {

    private Packet packet;
    private static final double PACKET_SIZE = 10;

    public PacketView(Packet packet) {
        this.packet = packet;
        setupShape();
        setupNoiseOpacity();
    }

    private void setupShape() {
        if (packet.getShapeType() == ShapeType.SQUARE) {
            double halfSize = PACKET_SIZE / 2;
            getPoints().addAll(
                    -halfSize, -halfSize,
                    halfSize, -halfSize,
                    halfSize, halfSize,
                    -halfSize, halfSize
            );
            setFill(Color.TRANSPARENT);
            setStroke(Color.web("#00FF00"));
            setStrokeWidth(3);
        } else if (packet.getShapeType() == ShapeType.TRIANGLE) {
            double height = Math.sqrt(3) / 2 * PACKET_SIZE;
            double halfBase = PACKET_SIZE / 2;
            getPoints().addAll(
                    -halfBase, height / 2,
                    halfBase, height / 2,
                    0.0, -height / 2
            );
            setFill(Color.TRANSPARENT);
            setStroke(Color.web("#FFFF00"));
            setStrokeWidth(3);
        }
    }

    public void update() {
        setLayoutX(packet.getDeviatedLocation().getX());
        setLayoutY(packet.getDeviatedLocation().getY());
    }

    public void saveLocation() {
        Point2D location = localToScene(0, 0);
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void applyCollision() {
        TranslateTransition shake = new TranslateTransition(Duration.millis(50), this);
        shake.setByX(5);
        shake.setCycleCount(10);
        shake.setAutoReverse(true);
        shake.play();
        setupNoiseOpacity();
    }

    public void setupNoiseOpacity() {
        setOpacity(1 - (double) packet.getNoise() / (packet.getMaxNoise() + 1));
    }
}
