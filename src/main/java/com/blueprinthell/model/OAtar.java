package com.blueprinthell.model;

import com.blueprinthell.controller.GameController;

public class OAtar implements ShopItem {
    private static final OAtar instance = new OAtar();
    private OAtar() {

    }

    public static OAtar getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return "O' Atar";
    }

    @Override
    public int getPrice() {
        return 3;
    }

    @Override
    public String getDescription() {
        return "Disables the effect of Impact Waves for 10 seconds.";
    }

    @Override
    public void apply(GameController game) {
        // game.disableImpactWavesFor(10_000);
    }
}
