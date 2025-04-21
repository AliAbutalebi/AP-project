package com.blueprinthell.model;

public abstract class Packet {

    private int id;
    private int size;
    private double speed;
    private double noise;
    private double stateOnWire;
    private Wire currentWire;
    private boolean isAlive = true;
}
