package com.blueprinthell.model;

import com.blueprinthell.controller.GameController;

public class OAnahita implements ShopItem{
    private static final OAnahita instance = new OAnahita();

    private OAnahita() {

    }

    public static OAnahita getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return "O' Anahita";
    }

    @Override
    public int getPrice() {
        return 5;
    }

    @Override
    public String getDescription() {
        return "Sets the noise level of all current packets in the network to zero.";
    }

    @Override
    public void apply(GameController game) {
        // game.clearAllPacketNoise();
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void enable() {}
}
