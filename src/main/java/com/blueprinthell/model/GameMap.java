package com.blueprinthell.model;

import java.util.ArrayList;

public class GameMap {
    private ArrayList<SystemNode> systemNodes;
    private double maxWireLength; //TODO: double-check for making maxWireLength final

    public void setSystemNodes(ArrayList<SystemNode> systemNodes) {
        this.systemNodes = systemNodes;
    }

    public ArrayList<SystemNode> getSystemNodes() {
        return systemNodes;
    }

    public void setMaxWireLength(double maxWireLength) {
        this.maxWireLength = maxWireLength;
    }

    public double getMaxWireLength() {
        return maxWireLength;
    }

}
