package com.blueprinthell.model;

import javafx.geometry.Point2D;

public class Packet {

    private static final double BASE_SPEED = 20;
    private static final double ACCELERATION = 100;
    private static final int SQUARE_COINS = 1;
    private static final int TRIANGLE_COINS = 2;
    private static final int MAX_NOISE= 2;
    private int id;
    private double currentSpeed = BASE_SPEED;
    private int noise = 0;
    private double distanceOnWire;
    private ShapeType shapeType;
    private boolean onWire;
    private Wire currentWire;
    private boolean isAlive = true;
    private Point2D center;
    private Point2D location = new Point2D(0, 0);
    private SystemNode parentSystemNode;
    private boolean colliding = false;

    public Packet(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoise() {
        return noise;
    }

    public void setNoise(int noise) {
        this.noise = noise;
    }

    public int getMaxNoise() {
        return MAX_NOISE;
    }

    public double getDistanceOnWire() {
        return distanceOnWire;
    }

    public void setDistanceOnWire(double distanceOnWire) {
        this.distanceOnWire = distanceOnWire;
    }

    public Point2D getCenter() {
        return center;
    }

    public void setCenter(Point2D center) {
        this.center = center;
    }

    public boolean isOnWire() {
        return onWire;
    }

    public void setOnWire(boolean onWire) {
        this.onWire = onWire;
    }

    public Wire getCurrentWire() {
        return currentWire;
    }

    public void setCurrentWire(Wire currentWire) {
        this.currentWire = currentWire;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public double getBaseSpeed() {
        return BASE_SPEED;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(double baseSpeed) {
        this.currentSpeed = baseSpeed;
    }

    public double getAcceleration() {
        return ACCELERATION;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public int getSquareCoins() {
        return SQUARE_COINS;
    }

    public int getTriangleCoins() {
        return TRIANGLE_COINS;
    }

    public SystemNode getParentSystemNode() {
        return parentSystemNode;
    }

    public void setParentSystemNode(SystemNode parentSystemNode) {
        this.parentSystemNode = parentSystemNode;
    }

    public boolean isColliding() {
        return colliding;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

    public void applyCollision() {
        noise++;
    }
}

