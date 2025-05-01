package com.blueprinthell.model;

import javafx.geometry.Point2D;

public class Packet {

    private int id;
    private double speed;
    private double noise;
    private double progressOnWire;
    private ShapeType shapeType;
    private boolean onWire;
    private Wire currentWire;
    private boolean isAlive = true;
    private Point2D center;
    private Point2D location;
    private double currentSpeed = SPEED;

    private static final double SPEED = 1;
    private static final double ACCELERATION = 1;

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

    public void setProgressOnWire(double progressOnWire) {
        this.progressOnWire = progressOnWire;
    }

    public double getProgressOnWire() {
        return progressOnWire;
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

    public double getSpeed() {
        return SPEED;
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
}

