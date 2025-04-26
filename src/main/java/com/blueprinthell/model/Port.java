package com.blueprinthell.model;

public abstract class Port {
    private int id;
    private final boolean isInput;
    private boolean occupied = false;
    private Wire connectedWire;
    private int parentSystemId;

    public Port(boolean isInput, int parentSystemId) {
        this.isInput = isInput;
        this.parentSystemId = parentSystemId;
    }
}

class SquarePort extends Port {
    public SquarePort(boolean isInput, int parentSystemId) {
        super(isInput, parentSystemId);
    }
}

class TrianglePort extends Port {
    public TrianglePort(boolean isInput, int parentSystemId) {
        super(isInput, parentSystemId);
    }
}
