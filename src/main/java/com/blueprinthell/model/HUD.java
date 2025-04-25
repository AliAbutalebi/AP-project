package com.blueprinthell.model;

public class HUD {
    private static HUD instance;

    private double remainingWireLength;
    private double temporalProgress;
    private double packetLoss;
    private int coins;

    private HUD() {
    }

    public static HUD getInstance() {
        if (instance == null) {
            instance = new HUD();
        }
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

}
