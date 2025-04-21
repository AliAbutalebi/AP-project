package com.blueprinthell.model;

import javafx.geometry.Dimension2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SystemNode {
    private int id;
    private final ArrayList<Port> inputPorts = new ArrayList<>();
    private final ArrayList<Port> outputPorts = new ArrayList<>();
    private final Queue<Packet> packetQueue = new LinkedList<>();
    private static final int maxPacketInQueue = 5;
    // private final Dimension2D position;
    // private final Dimension2D size;
    private boolean isActive = false;
    
}
