package com.blueprinthell.model;

public abstract class Port {
    private int id;
    private final boolean isInput;
    private boolean occupied = false;
    private Wire connectedWire;
    private SystemNode parentSystem;

    public Port(boolean isInput, SystemNode parentSystem) {
        this.isInput = isInput;
        this.parentSystem = parentSystem;
    }
}

class SquarePort extends Port {
    public SquarePort(boolean isInput, SystemNode parentSystem) {
        super(isInput, parentSystem);
    }
}

class TrianglePort extends Port {
    public TrianglePort(boolean isInput, SystemNode parentSystem) {
        super(isInput, parentSystem);
    }
}
