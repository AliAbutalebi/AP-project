package com.blueprinthell.model;

import javafx.geometry.Dimension2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class SystemNode {
    private int id;
    private final Queue<Packet> packetQueue = new LinkedList<>();
    private static final int maxPacketInQueue = 5;
    // private final Dimension2D POSITION;
    // private final Dimension2D SIZE;
    private boolean isActive = false;
}

class regularSystemNode extends SystemNode {
    private final ArrayList<Port> inputPorts = new ArrayList<>();
    private final ArrayList<Port> outputPorts = new ArrayList<>();
}

class StartSystemNode extends SystemNode {
    private final ArrayList<Port> outputPorts = new ArrayList<>();
}

class EndSystemNode extends SystemNode {
    private final ArrayList<Port> inputPorts = new ArrayList<>();
}