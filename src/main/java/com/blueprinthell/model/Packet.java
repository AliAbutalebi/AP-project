package com.blueprinthell.model;

import javafx.geometry.Point2D;

public class Packet {

    private int id;
    private static final double BASE_SPEED = 1;
    private static final double ACCELERATION = 1;
    private double currentSpeed = BASE_SPEED;
    private double noise;
    private double distanceOnWire;
    private ShapeType shapeType;
    private boolean onWire;
    private Wire currentWire;
    private boolean isAlive = true;
    private Point2D center;
    private Point2D location;

    private static final int SQUARE_COINS = 1;
    private static final int TRIANGLE_COINS = 2;

    public Packet(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNoise(double noise) {
        this.noise = noise;
    }

    public double getNoise() {
        return noise;
    }

    public void setDistanceOnWire(double distanceOnWire) {
        this.distanceOnWire = distanceOnWire;
    }

    public double getDistanceOnWire() {
        return distanceOnWire;
    }

    public void setCenter(Point2D center) {
        this.center = center;
    }

    public Point2D getCenter() {
        return center;
    }

    public void setOnWire(boolean onWire) {
        this.onWire = onWire;
    }

    public boolean isOnWire() {
        return onWire;
    }

    public void setCurrentWire(Wire currentWire) {
        this.currentWire = currentWire;
    }

    public Wire getCurrentWire() {
        return currentWire;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public Point2D getLocation() {
        return location;
    }

    public double getBaseSpeed() {
        return BASE_SPEED;
    }

    public void setCurrentSpeed(double baseSpeed) {
        this.currentSpeed = baseSpeed;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public double getAcceleration() {
        return ACCELERATION;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public int getSquareCoins() {
        return SQUARE_COINS;
    }

    public int getTriangleCoins() {
        return TRIANGLE_COINS;
    }

}

