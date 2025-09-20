package com.blueprinthell.model.shop;

import com.blueprinthell.model.Wire;

import javafx.geometry.Point2D;
import java.util.*;

public class Shop {
    private static final Shop instance = new Shop();

    private final ArrayList<ItemType> itemTypes = new ArrayList<>(Arrays.asList(ItemType.values()));
    private final ArrayList<ShopItem> activeItems = new ArrayList<>();
    private boolean waitForSelection = false;


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

    public boolean cantActivate(ItemType type) {
        switch (type) {
            case OATAR, OAIRYAMAN, AERGIA -> {
                return isActive(type);
            }
            case OANAHITA -> {
                return false;
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

    public boolean waitingForSelection() {
        return waitForSelection;
    }

    public void setWaitForSelection(boolean waitForSelection) {
        this.waitForSelection = waitForSelection;
    }

    public void checkForSelection(ItemType item) {
        if (item.equals(ItemType.AERGIA) || item.equals(ItemType.SISYPHUS) || item.equals(ItemType.ELIPHAS)) {
            waitForSelection = true;
        }
    }

    public void addItem(ItemType item) {
        ShopItem itemToAdd = new ShopItem(item);
        itemToAdd.activate();
        activeItems.add(itemToAdd);
    }
}
