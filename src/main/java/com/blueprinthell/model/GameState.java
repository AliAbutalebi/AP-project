package com.blueprinthell.model;

import java.util.ArrayList;

public class GameState {
    private ArrayList<SystemNode> systemNodes;
    private ArrayList<Wire> wires;
    private ArrayList<Packet> packets;
    private int coins;
    private int initialPackets;
    private int packetLoss;
    private double timeProgress;
    private static final double LOSS_THRESHOLD = 0.5;
    private boolean gameOver;
}
