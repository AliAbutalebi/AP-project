package com.blueprinthell.controller;

import com.blueprinthell.audio.SoundEffectManager;
import com.blueprinthell.model.HUD;
import com.blueprinthell.model.ScreenDimensions;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class GameOverController {

    @FXML
    private HBox gameOverPane;
    @FXML
    private HBox dataPane;
    @FXML
    private Label receivedPackets;
    @FXML
    private Label packetLoss;

    ScreenDimensions screenDimensions = ScreenDimensions.getInstance();

    SoundEffectManager soundEffectManager = SoundEffectManager.getInstance();

    HUD hud = HUD.getInstance();

    @FXML
    public void initialize() {
        soundEffectManager.play("game-over");

        dataPane.setMaxWidth(screenDimensions.getWidth() / 2);
        receivedPackets.setText(hud.getPacketsCount() - hud.getLostPackets() + "");

        Double packetLossPercentage = (double) Math.round(hud.getLostPackets() / (double) hud.getPacketsCount() * 100);
        packetLoss.setText(packetLossPercentage + "%");

    }


}
