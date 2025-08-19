package com.blueprinthell.model.shop;

public class ShopItem2 {
    private final ItemType type;
    private boolean active;
    private double remainingDuration;
    private double remainingCooldown;

    public ShopItem2(ItemType type) {
        this.type = type;
        this.remainingDuration = 0;
        this.remainingCooldown = 0;
    }

    public void activate() {
        if (remainingCooldown == 0 && !active) {
            active = true;
            remainingDuration = type.getDuration();
            remainingCooldown = type.getCooldown();
        }
    }

    public void tick() {
        if (active) {
            remainingDuration--;
            if (remainingDuration <= 0) {
                active = false;
            }
        } else if (remainingCooldown > 0) {
            remainingCooldown--;
        }
    }

    public ItemType getType() { return type; }
    public boolean isActive() { return active; }
    public double getRemainingDuration() { return remainingDuration; }
    public double getRemainingCooldown() { return remainingCooldown; }
}
