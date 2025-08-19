package com.blueprinthell.model.shop;

public enum ItemType {
    OATAR(3, 10, 0),
    OAIRYAMAN(4, 5, 0),
    OANAHITA(5, 0, 0),
    AERGIA(10, 20, 10),
    SISYPHUS(15, 0, 0),
    ELIPHAS(20, 30, 0);

    private final int price;
    private final double duration;
    private final double cooldown;

    ItemType(int price, double duration, double cooldown) {
        this.price = price;
        this.duration = duration;
        this.cooldown = cooldown;
    }

    public int getPrice() {
        return price;
    }
    public double getDuration() {
        return duration;
    }
    public double getCooldown() {
        return cooldown;
    }
}
