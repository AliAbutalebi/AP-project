package com.blueprinthell.model;

import java.util.HashMap;
import java.util.Map;

public class HUD {
    private static HUD instance;
    private static final Map<String, Object> contents = new HashMap<>();
    private static double remainingWireLength;
    private static double temporalProgress;
    private static int lostPackets = 0;
    private static int packetsCount = 0;
    private static int coins;

    private static boolean isVisible = false;

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

    public int getLostPackets() {
        return lostPackets;
    }

    public void setLostPackets(int lostPackets) {
        this.lostPackets = lostPackets;
    }

    public int getPacketsCount() {
        return packetsCount;
    }
    public void setPacketsCount(int packetsCount) {
        this.packetsCount = packetsCount;
    }

    public static double getPacketLoss() {
        return (double) lostPackets / packetsCount * 100;
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
        contents.put("Packet Loss", getPacketLoss());
        contents.put("Coins", coins);
    }

    public Map<String, Object> getContents() {
        return contents;
    }

    public void toggleVisibility() {
        isVisible = !isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public void removeCoins(int amount) {
        coins -= amount;
    }

}
