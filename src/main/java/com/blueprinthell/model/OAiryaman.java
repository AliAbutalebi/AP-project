package com.blueprinthell.model;

import com.blueprinthell.controller.GameController;

public class OAiryaman implements ShopItem{
    private static final OAiryaman instance = new OAiryaman();
    private OAiryaman() {

    }

    public static OAiryaman getInstance() {
        return instance;
    }

    @Override
    public String getName() {
        return "O' Airyaman";
    }

    @Override
    public int getPrice() {
        return 4;
    }

    @Override
    public String getDescription() {
        return "Disables packet collisions in the network for 5 seconds.";
    }

    @Override
    public void apply(GameController game) {
        // game.disablePacketCollisionsFor(5_000);
    }
}
