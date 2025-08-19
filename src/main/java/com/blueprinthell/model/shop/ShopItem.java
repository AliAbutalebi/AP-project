package com.blueprinthell.model.shop;

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
