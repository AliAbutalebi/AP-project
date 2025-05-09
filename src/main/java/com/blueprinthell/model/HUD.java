package com.blueprinthell.model;

import java.util.HashMap;
import java.util.Map;

public class HUD {
    private static HUD instance;
    private static final Map<String, Object> contents = new HashMap<>();
    private static double remainingWireLength;
    private static double temporalProgress;
    private static double packetLoss;
    private static int coins;

    private HUD() {
    }

    public static HUD getInstance() {
        if (instance == null) {
            instance = new HUD();
        }
        update();
        return instance;
    }

    public double getRemainingWireLength() {
        return remainingWireLength;
    }

    public void setRemainingWireLength(double remainingWireLength) {
        this.remainingWireLength = remainingWireLength;
    }

    public double getTemporalProgress() {
        return temporalProgress;
    }

    public void setTemporalProgress(double temporalProgress) {
        this.temporalProgress = temporalProgress;
    }

    public double getPacketLoss() {
        return packetLoss;
    }

    public void setPacketLoss(double packetLoss) {
        this.packetLoss = packetLoss;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public static void update() {
        contents.put("Remaining Wire Length", remainingWireLength);
        contents.put("Temporal Progress", temporalProgress);
        contents.put("Packet Loss", packetLoss);
        contents.put("Coins", coins);
    }

    public Map<String, Object> getContents() {
        return contents;
    }

}
