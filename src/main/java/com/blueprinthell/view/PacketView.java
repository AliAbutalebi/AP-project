package com.blueprinthell.view;

import com.blueprinthell.model.Packet;
import com.blueprinthell.model.ShapeType;
import com.blueprinthell.polygonization.Polygonizer;
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

    private final Polygonizer polygonizer = Polygonizer.getInstance();

    private Packet packet;
    private static final double PACKET_SIZE = 300;

    public PacketView(Packet packet) {
        this.packet = packet;
        setupShape();
        setupNoiseOpacity();
    }

    private void setupShape() {
        if (packet.isProtected()) createProtected();
        else if (packet.isTrojan()) createTrojan();
        else {
            switch (packet.getShapeType()) {
                case SQUARE -> createSquare();
                case TRIANGLE -> createTriangle();
                case HEXAGON -> createHexagon();
                case CONFIDENTIAL_ONE -> createConfidentialOne();
                case CONFIDENTIAL_TWO -> createConfidentialTwo();
                case LARGE_ONE -> createLargeOne();
                case LARGE_TWO -> createLargeTwo();
                case BIT_PACKET -> createBitPacket();
            }
        }

    }

    public void update() {
        setLayoutX(packet.getDeviatedLocation().getX());
        setLayoutY(packet.getDeviatedLocation().getY());

        getPoints().clear();
        setupShape();

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

    private void createSquare() {
        getPoints().addAll(createPolygon(4, PACKET_SIZE / 2).getPoints());
        setFill(Color.TRANSPARENT);
        setStroke(Color.web("#32c65f"));
        setStrokeWidth(3);
        setRotate(-30);
    }

    private void createTriangle() {
        getPoints().addAll(createPolygon(3, PACKET_SIZE / 2).getPoints());
        setFill(Color.TRANSPARENT);
        setStroke(Color.web("#FFFF00"));
        setStrokeWidth(3);
        setRotate(-40);
    }

    private void createHexagon() {
        getPoints().addAll(createPolygon(6, PACKET_SIZE / 2).getPoints());
        setFill(Color.TRANSPARENT);
        setStroke(Color.web("#EEEEEE"));
        setStrokeWidth(3);
        setRotate(-30);
    }

    private void createConfidentialOne() {
        Image pattern = new Image(new File("./src/main/resources/com/blueprinthell/image/packets/confidential-one.png").toURI().toString(), PACKET_SIZE, PACKET_SIZE, true, true);
        getPoints().addAll(polygonizer.polygonize(pattern, PACKET_SIZE).getPoints());
        setFill(new ImagePattern(pattern));
    }

    private void createConfidentialTwo() {
        Image pattern = new Image(new File("./src/main/resources/com/blueprinthell/image/packets/confidential-two.png").toURI().toString(), PACKET_SIZE, PACKET_SIZE, true, true);
        getPoints().addAll(polygonizer.polygonize(pattern, PACKET_SIZE).getPoints());
        setFill(new ImagePattern(pattern));
    }

    private void createLargeOne() {
        Image pattern = new Image(new File("./src/main/resources/com/blueprinthell/image/packets/large-one.png").toURI().toString(), PACKET_SIZE, PACKET_SIZE, true, true);
        getPoints().addAll(polygonizer.polygonize(pattern, PACKET_SIZE).getPoints());
        setFill(new ImagePattern(pattern));
    }

    private void createLargeTwo() {
        Image pattern = new Image(new File("./src/main/resources/com/blueprinthell/image/packets/large-two.png").toURI().toString(), PACKET_SIZE, PACKET_SIZE, true, true);
        getPoints().addAll(polygonizer.polygonize(pattern, PACKET_SIZE).getPoints());
        setFill(new ImagePattern(pattern));
    }

    private void createProtected() {
        Image pattern = new Image(new File("./src/main/resources/com/blueprinthell/image/packets/protected.png").toURI().toString(), PACKET_SIZE, PACKET_SIZE, true, true);
        getPoints().addAll(polygonizer.polygonize(pattern, PACKET_SIZE).getPoints());
        setFill(new ImagePattern(pattern));
    }

    private void createTrojan() {
        Image pattern = new Image(new File("./src/main/resources/com/blueprinthell/image/packets/trojan.png").toURI().toString(), PACKET_SIZE, PACKET_SIZE, true, true);
        getPoints().addAll(polygonizer.polygonize(pattern, PACKET_SIZE).getPoints());
        setFill(new ImagePattern(pattern));
    }

    private void createBitPacket() {
        getPoints().addAll(createPolygon(5, PACKET_SIZE / 3).getPoints());
        setFill(Color.RED);
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
