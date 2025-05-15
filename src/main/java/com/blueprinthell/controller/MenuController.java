package com.blueprinthell.controller;

import com.blueprinthell.model.ScenePath;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController extends BaseController {

    @FXML
    private AnchorPane rootPane;

    public void initialize() {}

    @FXML
    private void handleStartGame(ActionEvent event) throws IOException {
        super.switchScene(ScenePath.GAME.getResourceURL(), event);
    }
    @FXML
    private void handleSelectLevel(ActionEvent event) throws IOException {
        //TODO: create "Select Level" scene and write the SwitchScene
    }
    @FXML
    private void handleSettings(ActionEvent event) throws IOException {
        super.switchScene(ScenePath.SETTINGS.getResourceURL(), event);
    }
    @FXML
    private void handleExit(ActionEvent event) throws IOException {
        System.exit(0);
    }
}
