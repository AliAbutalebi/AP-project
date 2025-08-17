package com.blueprinthell.controller;

import com.blueprinthell.map.MapManager;
import com.blueprinthell.model.ScenePath;
import com.blueprinthell.view.AutoSaveView;
import com.blueprinthell.view.ShopView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class MenuController extends BaseController {

    private final MapManager mapManager = MapManager.getInstance();
    @FXML
    private AnchorPane rootPane;

    public void initialize() {}

    @FXML
    private void handleStartGame(ActionEvent event) throws IOException {
        if (mapManager.hasAutoSave()) {
            AutoSaveView autoSaveView = new AutoSaveView();
            rootPane.getChildren().add(autoSaveView);
            autoSaveView.setOnClose(() -> {
                try {
                    super.switchScene(ScenePath.GAME.getResourceURL(), event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else super.switchScene(ScenePath.GAME.getResourceURL(), event);
    }
    @FXML
    private void handleSelectLevel(ActionEvent event) throws IOException {
        super.switchScene(ScenePath.LEVEL_SELECT.getResourceURL(), event);
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
