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
}

class SquarePacket extends Packet {
    private final int SIZE = 2;
}

class TrianglePacket extends Packet {
    private final int SIZE = 3;
}
