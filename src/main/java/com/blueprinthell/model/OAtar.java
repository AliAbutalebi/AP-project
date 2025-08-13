package com.blueprinthell.model;

import com.blueprinthell.controller.GameController;

public class OAtar implements ShopItem {
    private static final OAtar instance = new OAtar();
    private static double remainingTime;
    private static boolean enabled;

    private static final HUD hud = HUD.getInstance();

    private OAtar() {

    }

    public static OAtar getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return "O' Atar";
    }

    @Override
    public int getPrice() {
        return 3;
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
        return "Disables the effect of Impact Waves for 10 seconds.";
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
        remainingTime = 10;
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
