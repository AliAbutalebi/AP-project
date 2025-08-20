package com.blueprinthell.model.shop;

public class ShopItem {
    private final ItemType type;
    private boolean active;
    private boolean cooldown;
    private double remainingDuration;
    private double remainingCooldown;

    public ShopItem(ItemType type) {
        this.type = type;
        this.remainingDuration = 0;
        this.remainingCooldown = 0;
    }

    public void activate() {
        if (remainingCooldown == 0 && !active) {
            active = true;
            remainingDuration = type.getDuration() * 1000000000L;
            remainingCooldown = type.getCooldown() * 1000000000L;
        }
    }

    public void tick(double deltaTime) {
        if (active) {
            remainingDuration -= deltaTime;
            if (remainingDuration <= 0) {
                active = false;
                cooldown = true;
            }
        } else if (remainingCooldown > 0) {
            remainingCooldown -= deltaTime;
        } else if (remainingCooldown <= 0) {
            cooldown = false;
        }
    }

    public ItemType getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isCooldown() {
        return cooldown;
    }

    public double getRemainingDuration() {
        return remainingDuration;
    }

    public double getRemainingCooldown() {
        return remainingCooldown;
    }
}
