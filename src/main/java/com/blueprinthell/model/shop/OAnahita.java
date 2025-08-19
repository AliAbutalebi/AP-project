package com.blueprinthell.model.shop;

import com.blueprinthell.controller.GameController;
import com.blueprinthell.model.HUD;

public class OAnahita implements ShopItem {
    private static final OAnahita instance = new OAnahita();

    private static final HUD hud = HUD.getInstance();

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
    public double getDuration() {
        return 0;
    }

    @Override
    public double getCooldown() {
        return 0;
    }

    @Override
    public String getDescription() {
        return "Sets the noise level of all current packets in the network to zero.";
    }

    @Override
    public void apply() {
        if (hud.getCoins() >= getPrice()) {
            hud.removeCoins(getPrice());
            enable();
        }
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void enable() {
        GameController.applyOAnahita();

    }
}
