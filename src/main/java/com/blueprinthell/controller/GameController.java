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
        rootPane.setOnMouseDragged(this::onWireDragged);
        rootPane.setOnMouseReleased(this::onWireReleased);
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
        if (portView.getPort().isInput() || portView.getPort().isOccupied()) {
            return;
        } else {
            startingPortView = portView;
            Wire wire = new Wire(startingPortView.getPort().getLocation(), startingPortView.getPort().getLocation());
            draggingWire = new WireView(wire);
            draggingWire.getWire().setSourcePort(portView.getPort());
            wirePane.getChildren().add(draggingWire);
        }
    }

    private void onWireDragged(MouseEvent event) {
        draggingWire.getWire().setEndLocation(new Point2D(event.getX(), event.getY()));
        draggingWire.updateView();
    }

    private void onWireReleased(MouseEvent event) {
        PortView hoveredInputPort = findHoveredInputPort(event.getX(), event.getY());
        if (hoveredInputPort == null) {
            clearDraggingWire();
        } else if (!isValidConnection(draggingWire.getWire().getSourcePort(), hoveredInputPort.getPort())) {
            clearDraggingWire();
        } else {
            draggingWire.getWire().setEndLocation(hoveredInputPort.getPort().getLocation());
            draggingWire.getWire().setDestinationPort(hoveredInputPort.getPort());
            wireViews.add(draggingWire);
            wireToView.put(draggingWire.getWire(), draggingWire);
            startingPortView.getPort().setConnectedWire(wireViews.get(wireViews.size() - 1).getWire());
            startingPortView.getPort().setOccupied(true);
            hoveredInputPort.getPort().setConnectedWire(wireViews.get(wireViews.size() - 1).getWire());
            hoveredInputPort.getPort().setOccupied(true);
            clearDraggingWire();
            wirePane.getChildren().add(wireViews.get(wireViews.size() - 1));
        }
    }

        wireView.setColor();
    private PortView findHoveredInputPort(double x, double y) {
        final double radius = 10;
        for (SystemNodeView nodeView : systemNodeViews) {
            for (PortView portView : nodeView.getInputPortViews()) {
                Point2D portPos = portView.getPort().getLocation();
                if (portPos.distance(x, y) <= radius) {
                    return portView;
                }
            }
        }
        return null;
    }

    private void clearDraggingWire() {
        wirePane.getChildren().remove(draggingWire);
        draggingWire = null;
        startingPortView = null;
    }

    private boolean isValidConnection(Port from, Port to) {
        return from != to && !from.isInput() && to.isInput() && from.getClass().equals(to.getClass());
    }
}

