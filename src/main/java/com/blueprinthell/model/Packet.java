package com.blueprinthell.model;

import javafx.geometry.Point2D;

public class Packet {

    private static final double BASE_SPEED = 100;
    private static final double ACCELERATION = 200;
    private static final double SPEED_LIMIT = 400;
    private static final double IMPACT = 10;
    private int id;
    private boolean isProtected = false;
    private SystemNode protector;
    private boolean isTrojan = false;
    private boolean passedIncompatiblePort = false;
    private double currentSpeed = BASE_SPEED;
    private int noise = 0;
    private double progressOnWire;
    private ShapeType shapeType;
    private boolean onWire;
    private Wire currentWire;
    private boolean isAlive = true;
    private Point2D deviation = new Point2D(0, 0);
    private Point2D location = new Point2D(0, 0);
    private SystemNode currentSystemNode;
    private boolean colliding = false;
    private boolean received = false;

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

    public double getProgressOnWire() {
        return progressOnWire;
    }

    public void setProgressOnWire(double progressOnWire) {
        this.progressOnWire = progressOnWire;
    }

    public Point2D getDeviation() {
        return deviation;
    }

    public void setDeviation(Point2D deviation) {
        this.deviation = deviation;
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

    public void setPassedIncompatiblePort(boolean passedIncompatiblePort) {
        this.passedIncompatiblePort = passedIncompatiblePort;
    }

    public boolean isPassedIncompatiblePort() {
        return passedIncompatiblePort;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public Point2D getDeviatedLocation() {
        return new Point2D(location.getX() + deviation.getX(), location.getY() + deviation.getY());
    }

    public double getBaseSpeed() {
        if (passedIncompatiblePort) return BASE_SPEED * 2;
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

    public boolean hasIllegalSpeed() {
        return currentSpeed > SPEED_LIMIT;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public int getPacketCoins() {
         if (isProtected) return 5;
         if (shapeType == ShapeType.CONFIDENTIAL_ONE) return 3;
         if (shapeType == ShapeType.CONFIDENTIAL_TWO) return 4;
         return getSize();
    }

    public SystemNode getCurrentSystemNode() {
        return currentSystemNode;
    }

    public void setCurrentSystemNode(SystemNode currentSystemNode) {
        this.currentSystemNode = currentSystemNode;
    }

    public boolean isColliding() {
        return colliding;
    }

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public boolean isReceived() {
        return received;
    }

    public void applyCollision() {
        noise++;
    }

    public void absorbImpact(Point2D impactCenter) {
        Point2D collisionDistance = getDeviatedLocation().subtract(impactCenter);
        double impactX = IMPACT / collisionDistance.getY();
        double impactY = IMPACT / collisionDistance.getX();
        deviation = deviation.add(new Point2D(impactX, impactY));
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public void setProtector(SystemNode protector) {
        this.protector = protector;
    }

    public SystemNode getProtector() {
        return protector;
    }

    public int getSize() {
        int size = 0;
        switch (shapeType) {
            case SQUARE -> size = 2;
            case TRIANGLE -> size = 3;
            case HEXAGON -> size = 1;
            case CONFIDENTIAL_ONE -> size = 4;
            case CONFIDENTIAL_TWO -> size = 6;
            case LARGE_ONE -> size = 8;
            case LARGE_TWO -> size = 10;
        }
        if (isProtected) return size * 2;
        return size;
    }

    public boolean isTrojan() {
        return isTrojan;
    }

    public void setTrojan(boolean trojan) {
        isTrojan = trojan;
    }
}

