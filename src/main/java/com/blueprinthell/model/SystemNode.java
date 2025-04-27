package com.blueprinthell.model;

import javafx.geometry.Dimension2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SystemNode {
    private int id;
    private final Queue<Packet> packetQueue = new LinkedList<>();
    private static final int maxPacketInQueue = 5;
    private ArrayList<Port> inputPorts = new ArrayList<>();
    private ArrayList<Port> outputPorts = new ArrayList<>();
    private Dimension2D position;
    private boolean isActive = false;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setPacketQueue(Queue<Packet> packetQueue) {
        this.packetQueue.addAll(packetQueue);
    }
    public Queue<Packet> getPacketQueue() {
        return packetQueue;
    }
    public void setInputPorts(ArrayList<Port> inputPorts) {
        this.inputPorts = inputPorts;
    }
    public ArrayList<Port> getInputPorts() {
        return inputPorts;
    }
    public void setOutputPorts(ArrayList<Port> outputPorts) {
        this.outputPorts = outputPorts;
    }
    public ArrayList<Port> getOutputPorts() {
        return outputPorts;
    }
    public void setPosition(Dimension2D position) {
        this.position = position;
    }
    public Dimension2D getPosition() {
        return position;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public boolean isActive() {
        return isActive;
    }
}

