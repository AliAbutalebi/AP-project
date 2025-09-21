package com.blueprinthell.model.shop;

public class ShopItem {
    private final ItemType type;
    private boolean active;
    private boolean cooldown;
    private double remainingDuration;
    private double remainingCooldown;

    public ShopItem(ItemType type) {
        this.type = type;
        activate();
    }

    public void activate() {
        active = true;
        remainingDuration = type.getDuration();
        remainingCooldown = type.getCooldown();

    }

    public void tick(double deltaTime) {
        if (active) {
            if (type.getDuration() == 0) {
                active = false;
                cooldown = true;
            }
                remainingDuration -= deltaTime;
                if (remainingDuration <= 0) {
                    active = false;
                    cooldown = true;
                }
            } else if (cooldown) {
                if (type.getCooldown() == 0) cooldown = false;
                remainingCooldown -= deltaTime;
                if (remainingCooldown <= 0) {
                    cooldown = false;
                }
            }
        }

        public ItemType getType () {
            return type;
        }

        public boolean isActive () {
            return active;
        }

        public void setActive ( boolean active){
            this.active = active;
        }

        public boolean isCooldown () {
            return cooldown;
        }

        public double getRemainingDuration () {
            return remainingDuration;
        }

        public double getRemainingCooldown () {
            return remainingCooldown;
        }
    }
