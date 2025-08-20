package com.blueprinthell.model.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shop {
    private static final Shop instance = new Shop();

    private final ArrayList<ItemType> itemTypes = new ArrayList<>(Arrays.asList(ItemType.values()));
    private final ArrayList<ShopItem> activeItems = new ArrayList<>();

    private Shop() {

    }

    public static Shop getInstance() {
        return instance;
    }

    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    public List<ShopItem> getActiveItems() {return activeItems;}

    public boolean isActive(ItemType type) {
        for (ShopItem item : activeItems) {
            if (item.getType().equals(type) && item.isActive()) return true;
        }
        return false;
    }

    public boolean canActivate(ItemType type) {
        switch (type) {
            case OATAR, OAIRYAMAN, OANAHITA, AERGIA -> {
                return isActive(type);
            }
        }
        return false;
    }

    public boolean isCoolingDown(ItemType type) {
        for (ShopItem item : activeItems) {
            if (item.getType().equals(type)) {
                if (item.isCooldown()) return true;
            }
        }
        return false;
    }

    public ShopItem getItem(ItemType itemType) {
        for (int i = activeItems.size() - 1; i >= 0; i--) {
            ShopItem item = activeItems.get(i);
            if (item.getType().equals(itemType)) return item;
        }
        return null;
    }

    public void tick(double deltaTime) {
        ArrayList<ShopItem> outdatedItems = new ArrayList<>();
        for (ShopItem item : activeItems) {
            item.tick(deltaTime);

            if (!item.isActive() && !item.isCooldown()) {
                outdatedItems.add(item);
            }
        }
        activeItems.removeAll(outdatedItems);
    }
}
