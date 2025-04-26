package com.blueprinthell.model;

import javafx.geometry.Dimension2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class SystemNode {
    private int id;
    private final Queue<Packet> packetQueue = new LinkedList<>();
    private static final int maxPacketInQueue = 5;
    private final ArrayList<Port> inputPorts = new ArrayList<>();
    private final ArrayList<Port> outputPorts = new ArrayList<>();
    private Dimension2D position;
    private boolean isActive = false;
}

class ReferenceSystemNode extends SystemNode {


}
