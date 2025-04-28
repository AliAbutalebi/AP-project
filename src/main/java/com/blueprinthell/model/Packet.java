package com.blueprinthell.model;

import javafx.geometry.Dimension2D;

public abstract class Packet {

    private int id;
    private double speed;
    private double noise;
    private double stateOnWire;
    private Dimension2D center;
    private Wire currentWire;
    private boolean isAlive = true;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setNoise(double noise) {
        this.noise = noise;
    }

    public double getNoise() {
        return noise;
    }

    public void setStateOnWire(double stateOnWire) {
        this.stateOnWire = stateOnWire;
    }

    public double getStateOnWire() {
        return stateOnWire;
    }

    public void setCenter(Dimension2D center) {
        this.center = center;
    }

    public Dimension2D getCenter() {
        return center;
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
}

