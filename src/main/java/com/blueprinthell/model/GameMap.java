package com.blueprinthell.model;

import java.util.ArrayList;

public class GameMap {
    private ArrayList<SystemNode> systemNodes;
    private ArrayList<Wire> wires;
    private ArrayList<Packet> activePackets;

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

    public void setWires(ArrayList<Wire> wires) {
        this.wires = wires;
    }

    public ArrayList<Wire> getWires() {
        return wires;
    }

    public void setActivePackets(ArrayList<Packet> activePackets) {
        this.activePackets = activePackets;
    }

    public ArrayList<Packet> getActivePackets() {
        return activePackets;
    }

    public void resetPackets(ArrayList<Packet> snapshot) {

    }


}
