package com.blueprinthell.model;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SystemNode {
    private int id;
    private final Queue<Packet> packetQueue = new LinkedList<>();
    private static final int QUEUE_CAPACITY = 5;
    private ArrayList<Port> inputPorts = new ArrayList<>();
    private ArrayList<Port> outputPorts = new ArrayList<>();
    private Point2D location;
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
    public static int getQueueCapacity() {
        return QUEUE_CAPACITY;
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
    public void setLocation(Point2D location) {
        this.location = location;
    }
    public Point2D getLocation() {
        return location;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public boolean isActive() {
        return isActive;
    }
    public boolean tryReceivePacket(Packet packet) {
        return packetQueue.size() < QUEUE_CAPACITY;
    }
}

