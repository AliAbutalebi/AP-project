package com.blueprinthell.model;

import javafx.geometry.Dimension2D;

import java.util.ArrayList;

public class Wire {
    private int id;
    private Port sourcePort;
    private Port destinationPort;
    private double length;
    private Packet packetOnWire;
    private double startX, startY, endX, endY;

    public Wire(Dimension2D startPoint,Dimension2D endPoint) {}

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setSourcePort(Port sourcePort) {
        this.sourcePort = sourcePort;
    }
    public Port getSourcePort() {
        return sourcePort;
    }
    public void setDestinationPort(Port destinationPort) {
        this.destinationPort = destinationPort;
    }
    public Port getDestinationPort() {
        return destinationPort;
    }
    public void setLength(double length) {
        this.length = length;
    }
    public double getLength() {
        return length;
    }
    public void setPacketOnWire(Packet packetOnWire) {
        this.packetOnWire = packetOnWire;
    }
    public Packet getPacketOnWire() {
        return packetOnWire;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }
    public double getStartX() {
        return startX;
    }
    public void setStartY(double startY) {
        this.startY = startY;
    }
    public double getStartY() {
        return startY;
    }
    public void setEndX(double endX) {
        this.endX = endX;
    }
    public double getEndX() {
        return endX;
    }
    public void setEndY(double endY) {
        this.endY = endY;
    }
    public double getEndY() {
        return endY;
    }

}
