package com.blueprinthell.model;

public abstract class Port {
    private int id;
    private boolean isInput;
    private boolean occupied = false;
    private Wire connectedWire;
    private int parentSystemId;

    public Port(boolean isInput, int parentSystemId) {
        this.isInput = isInput;
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
}

