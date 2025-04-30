package com.blueprinthell.controller;

import com.blueprinthell.map.MapLoader;
import com.blueprinthell.model.*;
import com.blueprinthell.view.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane wirePane;
    @FXML
    private AnchorPane systemNodePane;

    private GameMap gameMap;

    private List<SystemNodeView> systemNodeViews = new ArrayList<>();
    private List<WireView> wireViews = new ArrayList<>();
    private List<PacketView> packetViews = new ArrayList<>();

    // Optional: Fast lookup maps if needed
    private Map<SystemNode, SystemNodeView> nodeToView = new HashMap<>();
    private Map<Wire, WireView> wireToView = new HashMap<>();
    private Map<Packet, PacketView> packetToView = new HashMap<>();

    private PortView startingPortView = null;
    private WireView draggingWire = null;

    private AnimationTimer gameLoop;

    @FXML
    public void initialize() {
        setGameMap(MapLoader.loadRandomMap());
        wirePane.setOnMouseDragged(this::onWireDragged);
        wirePane.setOnMouseReleased(this::onWireReleased);
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
        renderInitialMap();
        startGameLoop();
    }

    private void renderInitialMap() {
        for (SystemNode node : gameMap.getSystemNodes()) {
            SystemNodeView nodeView = new SystemNodeView(node);
            systemNodePane.getChildren().add(nodeView);
            systemNodeViews.add(nodeView);
            nodeToView.put(node, nodeView);

            for (PortView portView : nodeView.getInputPortViews()) {
                savePortLocation(portView);
            }
            for (PortView portView : nodeView.getOutputPortViews()) {
                savePortLocation(portView);
                portView.setOnMousePressed(event -> onWireStart(portView, event));
            }
        }



        // 2. Render all Wires
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        };
        gameLoop.start();
    }

    private void update() {

    }

    private void render() {

        for (WireView wireView : wireViews) {
            wireView.updateView();
        }

        for (PacketView packetView : packetViews) {
            packetView.updateView();
        }
    }

    private void savePortLocation(PortView portView) {
        portView.getPort().setLocation(portView.localToScene(0, 0));
    }

    private void onWireStart(PortView portView, MouseEvent event) {

    }

    private void onWireReleased(WireView wireView, MouseEvent event) {

    }

    private void onWireDragged(WireView wireView, MouseEvent event) {

    }
}

