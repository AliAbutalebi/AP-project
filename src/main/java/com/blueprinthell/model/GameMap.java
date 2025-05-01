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
    public void setPackets(ArrayList<Packet> packets) {
        this.activePackets = packets;
    }
    public ArrayList<Packet> getPackets() {
        return activePackets;
    }

    public void updatePackets(double deltaTime) {
        ArrayList<Packet> toRemove = new ArrayList<>();

        for (Packet packet : activePackets) {
            if (packet.isOnWire()) {
                double newProgress = packet.getProgressOnWire();
                if (packet.getShapeType() == packet.getCurrentWire().getShapeType()) {
                    newProgress = packet.getProgressOnWire() + packet.getSpeed() * deltaTime;
                }
                else if (packet.getShapeType() == ShapeType.SQUARE) {
                    newProgress = packet.getProgressOnWire() + packet.getSpeed() / 2 * deltaTime;
                }
                else if (packet.getShapeType() == ShapeType.TRIANGLE) {
                    newProgress = packet.getProgressOnWire() + packet.getSpeed() * deltaTime + packet.getAcceleration() * Math.pow(deltaTime, 2) / 2;
                }
                packet.setProgressOnWire(Math.min(newProgress, 1.0));
                packet.setLocation(packet.getCurrentWire().interpolate(packet.getProgressOnWire()));
            }

            if (!packet.isAlive()) {
                toRemove.add(packet);
                // gameState.incrementPacketLoss();
            }
        }

        activePackets.removeAll(toRemove);
    }

    public void resetPackets(ArrayList<Packet> snapshot) {

    }




}
