package com.blueprinthell.model.shop;

public enum ItemType {
    OATAR("O, Atar",3, 10, 0, "Disables the effect of Impact Waves for 10 seconds."),
    OAIRYAMAN("O' Airyaman",4, 5, 0, "Disables packet collisions in the network for 5 seconds."),
    OANAHITA("O, Anahita",5, 0, 0, "Sets the noise level of all current packets in the network to zero.") ,
    AERGIA("Scroll of Aergia", 10, 20, 10, "Sets packet acceleration to zero at a chosen wire point for 20 seconds, with cooldown."),
    SISYPHUS("Scroll of Sisyphus",15, 0, 0, "Moves a non-reference system within safe limits."),
    ELIPHAS("Scroll of Eliphas",20, 30, 0, "Sets packet deviation to zero on wires for 30 seconds.");

    private final String name;
    private final int price;
    private final double duration;
    private final double cooldown;
    private final String description;

    ItemType(String name, int price, double duration, double cooldown, String description) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.cooldown = cooldown;
        this.description = description;
    }

    public String getName() {
        return name;
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
    public String getDescription() {
        return description;
    }
}
