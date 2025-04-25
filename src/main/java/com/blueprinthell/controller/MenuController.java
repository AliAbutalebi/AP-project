package com.blueprinthell.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MenuController extends BaseController {

    public void initialize() {}

    @FXML
    private void handleStartGame(ActionEvent event) throws IOException {
        super.switchScene("/com/blueprinthell/view/GameView.fxml", event);
    }
    @FXML
    private void handleSelectLevel(ActionEvent event) throws IOException {
        //TODO: create "Select Level" scene and write the SwitchScene
    }
    @FXML
    private void handleSettings(ActionEvent event) throws IOException {
        //TODO: create "Settings" scene and write the SwitchScene
    }
    @FXML
    private void handleExit(ActionEvent event) throws IOException {
        System.exit(0);
    }
}
