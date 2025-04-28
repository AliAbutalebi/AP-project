package com.blueprinthell.model;

import javafx.geometry.Dimension2D;

import java.util.ArrayList;

public class Wire {
    private int id;
    private Port sourcePort;
    private Port destinationPort;
    private double length;
    private Packet packetOnWire;
    private Dimension2D startPoint, endPoint;

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
    public void setStartPoint(Dimension2D startPoint) {
        this.startPoint = startPoint;
    }
    public Dimension2D getStartPoint() {
        return startPoint;
    }
    public void setEndPoint(Dimension2D endPoint) {
        this.endPoint = endPoint;
    }
    public Dimension2D getEndPoint() {
        return endPoint;
    }

}
