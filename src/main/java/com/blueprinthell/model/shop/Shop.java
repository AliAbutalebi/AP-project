package com.blueprinthell.model.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shop {
    private static final Shop instance = new Shop();

    private final ArrayList<ItemType> itemTypes = new ArrayList<>(Arrays.asList(ItemType.values()));
    private final ArrayList<ShopItem2> purchasedItems = new ArrayList<>();

    private Shop() {

    }

    public static Shop getInstance() {
        return instance;
    }

    public List<ItemType> getItemTypes() {
        return itemTypes;
    }

    public List<ShopItem2> getPurchasedItems() {return purchasedItems;}

    public boolean isActive(ItemType itemType) {
        for (ShopItem2 shopItem : purchasedItems) {
            if (shopItem.getType().equals(itemType)) {
                if (shopItem.isActive()) {
                    return true;
                }
            }
        }
        return false;
    }
}
