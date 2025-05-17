package com.blueprinthell.model;

import com.blueprinthell.controller.GameController;

public interface ShopItem {
    String getName();
    int getPrice();
    String getDescription();
    void apply(GameController game);
}
