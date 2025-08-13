package com.blueprinthell.model;

import com.blueprinthell.controller.GameController;

public class OAiryaman implements ShopItem{
    private static final OAiryaman instance = new OAiryaman();
    private static double remainingTime = 5;
    private static boolean enabled = false;

    private static final HUD hud = HUD.getInstance();

    private OAiryaman() {

    }

    public static OAiryaman getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return "O' Airyaman";
    }

    @Override
    public int getPrice() {
        return 4;
    }

    @Override
    public double getDuration() {
        return 0;
    }

    @Override
    public double getCooldown() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "Disables packet collisions in the network for 5 seconds.";
    }

    @Override
    public void apply() {
        if (hud.getCoins() >= getPrice()) {
            resetRemainingTime();
            hud.removeCoins(getPrice());
            enable();
        }
    }

    public void updateRemainingTime(double deltaTime) {
        remainingTime -= deltaTime;
    }

    public void resetRemainingTime() {
        remainingTime = 5;
    }

    public boolean isExpired() {
        return remainingTime <= 0;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

}
