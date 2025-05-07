package com.blueprinthell.model;

import javafx.geometry.Point2D;

public class Port {
    private int id;
    private boolean isInput;
    private ShapeType shapeType;
    private boolean occupied = false;
    private Wire connectedWire;
    private int parentSystemId;
    private SystemNode parentSystemNode;
    private Point2D location;

    public Port(boolean isInput, ShapeType shapeType, int parentSystemId) {
        this.isInput = isInput;
        this.shapeType = shapeType;
        this.parentSystemId = parentSystemId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setInput(boolean isInput) {
        this.isInput = isInput;
    }

    public boolean isInput() {
        return isInput;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setConnectedWire(Wire connectedWire) {
        this.connectedWire = connectedWire;
    }

    public Wire getConnectedWire() {
        return connectedWire;
    }

    public void setParentSystemId(int parentSystemId) {
        this.parentSystemId = parentSystemId;
    }

    public int getParentSystemId() {
        return parentSystemId;
    }

    public void setParentSystemNode(SystemNode parentSystemNode) {
        this.parentSystemNode = parentSystemNode;
    }

    public SystemNode getParentSystemNode() {
        return parentSystemNode;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public void sendPacket(Packet packet) {
        getParentSystemNode().getPacketQueue().remove(packet);
        packet.setParentSystemNode(null);
        getConnectedWire().setPacketOnWire(packet);
    }

    public void receivePacket(Packet packet) {
        getParentSystemNode().getPacketQueue().add(packet);
        packet.setParentSystemNode(getParentSystemNode());
        getConnectedWire().setPacketOnWire(null);
    }
}

