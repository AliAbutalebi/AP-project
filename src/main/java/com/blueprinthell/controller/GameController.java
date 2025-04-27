package com.blueprinthell.controller;

import com.blueprinthell.model.SystemNode;
import com.blueprinthell.view.SystemNodeView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GameController extends BaseController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    public void initialize() {
        // TODO: Fix the disconnection of SystemNodeView and game.css
        SystemNode systemNode = new SystemNode();
        systemNode.setPosition(new Dimension2D(100, 100));
        SystemNodeView systemNodeView = new SystemNodeView(systemNode);
        rootPane.getChildren().add(systemNodeView);
    }
}
