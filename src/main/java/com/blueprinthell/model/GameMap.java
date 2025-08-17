package com.blueprinthell.model;

import java.util.ArrayList;

public class GameMap {
    private int level;
    private ArrayList<SystemNode> systemNodes;
    private ArrayList<Wire> wires;
    private ArrayList<Packet> packets;

    private double maxWireLength; //TODO: double-check for making maxWireLength final

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

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

    public void setPackets(ArrayList<Packet> packets) {
        this.packets = packets;
    }

    public ArrayList<Packet> getPackets() {
        return packets;
    }

    public void resetPackets(ArrayList<Packet> snapshot) {

    }


}
