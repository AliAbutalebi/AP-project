package com.blueprinthell.model;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SystemNode {
    private int id;
    private final Queue<Packet> packetQueue = new LinkedList<>();
    private static final int QUEUE_CAPACITY = 5;
    private static final double ANTI_TROJAN_RADIUS = 250;
    private ArrayList<Port> inputPorts = new ArrayList<>();
    private ArrayList<Port> outputPorts = new ArrayList<>();
    private Point2D location;
    private boolean isActive = true;
    private SystemType systemType;
    private ArrayList<Packet> protectedPackets = new ArrayList<>();

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

    public static double getAntiTrojanRadius() {return ANTI_TROJAN_RADIUS;}

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

    public boolean isReady() {
        for (Port port : inputPorts) {
            if (port.getConnectedWire() == null) return false;
        }
        for (Port port : outputPorts) {
            if (port.getConnectedWire() == null) return false;
        }
        return true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean tryReceivePacket(Packet packet) {
        return packetQueue.size() < QUEUE_CAPACITY;
    }

    public void setSystemType(SystemType systemType) {
        this.systemType = systemType;
    }

    public SystemType getSystemType() {
        return systemType;
    }

    public ArrayList<Packet> getProtectedPackets() {
        return protectedPackets;
    }

    public void receivePacket(Packet packet) {
        switch (systemType) {
            case SABOTEUR -> {
                if (packet.isProtected()) {
                    packet.setProtected(false);
                    return;
                }
                if (packet.getNoise() == 0) {
                    packet.setNoise(1);
                }
                Random random = new Random();
                if (random.nextBoolean()) packet.setTrojan(true);
            }
        }
    }

    public void sendPacket(Packet packet) {
        switch (systemType) {
            case VPN -> {
                if (!packet.isProtected()) {
                    packet.setProtected(true);
                    packet.setProtector(this);
                }
            }
        }
    }

    public void activate() {
        isActive = true;

    }

    public void deactivate() {
        isActive = false;

        if (systemType == SystemType.VPN) {
            for (Packet packet : protectedPackets) {
                packet.setProtected(false);
                packet.setProtector(null);
            }
            protectedPackets.clear();
        }
    }

    public int getQueueSize() {
        int n = 0;
        for (Packet packet : packetQueue) {
            if (packet.getShapeType() != ShapeType.BIT_PACKET) n++;
        }
        return n;
    }

}

