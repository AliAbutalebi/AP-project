package com.blueprinthell.model;

import javafx.geometry.Point2D;

import java.util.Random;

public class Port {
    private int id;
    private boolean isInput;
    private ShapeType shapeType;
    private boolean occupied = false;
    private Wire connectedWire;
    private int connectedWireId;
    private int parentSystemNodeId;
    private SystemNode parentSystemNode;
    private Point2D location;

    public Port() {}

    public Port(boolean isInput, ShapeType shapeType, int parentSystemId) {
        this.isInput = isInput;
        this.shapeType = shapeType;
        this.parentSystemNodeId = parentSystemId;
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

    public void setParentSystemNodeId(int parentSystemNodeId) {
        this.parentSystemNodeId = parentSystemNodeId;
    }

    public int getParentSystemNodeId() {
        return parentSystemNodeId;
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
        packet.setCurrentSystemNode(null);
        getConnectedWire().setPacketOnWire(packet);
    }

    public void receivePacket(Packet packet) {
        packet.setCurrentSystemNode(getParentSystemNode());
        getConnectedWire().setPacketOnWire(null);
    }

    public int getConnectedWireId() {
        return connectedWireId;
    }

    public void setConnectedWireId(int connectedWireId) {
        this.connectedWireId = connectedWireId;
    }

    public void setRandomShapeType() {
        ShapeType[] types = new ShapeType[] {ShapeType.SQUARE, ShapeType.TRIANGLE, ShapeType.HEXAGON};
        Random rand = new Random();
        ShapeType newType = types[rand.nextInt(types.length)];
        while (newType == shapeType) {
            newType = types[rand.nextInt(types.length)];
        }
        this.shapeType = newType;
    }
}

