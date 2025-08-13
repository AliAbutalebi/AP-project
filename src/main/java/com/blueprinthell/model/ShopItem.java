package com.blueprinthell.model;

import com.blueprinthell.controller.GameController;

public interface ShopItem {
    String getName();
    int getPrice();
    double getDuration();
    double getCooldown();
    String getDescription();
    void apply();

    boolean isEnabled();
    void enable();
}
