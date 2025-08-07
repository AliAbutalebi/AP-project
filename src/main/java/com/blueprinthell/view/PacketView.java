package com.blueprinthell.view;

import com.blueprinthell.model.Packet;
import com.blueprinthell.model.ShapeType;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class PacketView extends Polygon {

    private Packet packet;
    private static final double PACKET_SIZE = 8;

    public PacketView(Packet packet) {
        this.packet = packet;
        setupShape();
        setupNoiseOpacity();
    }

    private void setupShape() {
        if (packet.getShapeType() == ShapeType.SQUARE) {
            createSquare();
        } else if (packet.getShapeType() == ShapeType.TRIANGLE) {
            createTriangle();
        } else if (packet.getShapeType() == ShapeType.HEXAGON) {
            createHexagon();
        } else if (packet.getShapeType() == ShapeType.CONFIDENTIAL_ONE) {
            createConfidentialOne();
        }
    }

    public void update() {
        setLayoutX(packet.getDeviatedLocation().getX());
        setLayoutY(packet.getDeviatedLocation().getY());
        setupNoiseOpacity();
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
        setOpacity(1 - (double) packet.getNoise() / (packet.getSize() + 1));
    }

    public static double getPacketSize() {
        return PACKET_SIZE;
    }

    private void createSquare() {
        getPoints().addAll(createPolygon(4, PACKET_SIZE).getPoints());
        setFill(Color.TRANSPARENT);
        setStroke(Color.web("#32c65f"));
        setStrokeWidth(3);
        setRotate(-30);
    }

    private void createTriangle() {
        getPoints().addAll(createPolygon(3, PACKET_SIZE).getPoints());
        setFill(Color.TRANSPARENT);
        setStroke(Color.web("#FFFF00"));
        setStrokeWidth(3);
        setRotate(-40);
    }

    private void createHexagon() {
        getPoints().addAll(createPolygon(6, PACKET_SIZE).getPoints());
        setFill(Color.TRANSPARENT);
        setStroke(Color.web("#EEEEEE"));
        setStrokeWidth(3);
        setRotate(-30);
    }

    private void createConfidentialOne() {
        getPoints().addAll(createPolygon(20, PACKET_SIZE * 1.2).getPoints());
        Image pattern = new Image(new File("./src/main/resources/com/blueprinthell/image/packets/confidential-one.png").toURI().toString());
        setFill(new ImagePattern(pattern));
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
