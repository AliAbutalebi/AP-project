package com.blueprinthell.controller;

import com.blueprinthell.map.MapLoader;
import com.blueprinthell.model.*;
import com.blueprinthell.view.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

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

    private static final Color DRAGGING_COLOR = Color.web("#888888");
    private static final Color SQUARE_COLOR = Color.web("#00FF00");
    private static final Color TRIANGLE_COLOR = Color.web("#FFFF00");

    @FXML
    public void initialize() {
        setGameMap(MapLoader.loadRandomMap());
        rootPane.setOnMouseDragged(this::onWireDragged);
        rootPane.setOnMouseReleased(this::onWireReleased);
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
        renderInitialMap();
        // startGameLoop();
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
                portView.setOnMousePressed(event -> onPortClicked(portView, event));
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

    private void onPortClicked(PortView portView, MouseEvent event) {
        if (portView.getPort().isInput()) {
            return;
        }
        else if (portView.getPort().isOccupied()) {
            removeWire(portView.getPort().getConnectedWire());
            return;
        }
        else {
            startingPortView = portView;
            Wire wire = new Wire(startingPortView.getPort().getLocation(), startingPortView.getPort().getLocation());
            draggingWire = new WireView(wire);
            draggingWire.getWire().setSourcePort(portView.getPort());
            wirePane.getChildren().add(draggingWire);
            setColor(draggingWire);
        }
    }

    private void onWireDragged(MouseEvent event) {
        draggingWire.getWire().setEndLocation(new Point2D(event.getX(), event.getY()));
        draggingWire.updateView();
    }

    private void onWireReleased(MouseEvent event) {
        PortView targetPortView = findHoveredInputPort(event.getX(), event.getY());

        if (draggingWire == null || startingPortView == null || targetPortView == null) {
            clearDraggingWire();
            return;
        }

        if (!isValidConnection(draggingWire.getWire().getSourcePort(), targetPortView.getPort())) {
            clearDraggingWire();
            return;
        }

        finalizeWireConnection(startingPortView, targetPortView);
    }

    private void finalizeWireConnection(PortView from, PortView to) {

        draggingWire.getWire().setEndLocation(to.getPort().getLocation());
        draggingWire.getWire().setDestinationPort(to.getPort());
        from.getPort().setConnectedWire(draggingWire.getWire());
        from.getPort().setOccupied(true);
        to.getPort().setConnectedWire(draggingWire.getWire());
        to.getPort().setOccupied(true);

        setColor(draggingWire);

        wireViews.add(draggingWire);
        clearDraggingWire();
        wirePane.getChildren().add(wireViews.get(wireViews.size() - 1));
    }


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

    private void removeWire(WireView wireView) {
        wireView.getWire().getSourcePort().setConnectedWire(null);
        wireView.getWire().getDestinationPort().setConnectedWire(null);
        wireView.getWire().getSourcePort().setOccupied(false);
        wireView.getWire().getDestinationPort().setOccupied(false);
        wirePane.getChildren().remove(wireView);
        wireViews.remove(wireView);
        wireToView.remove(wireView.getWire());
        checkActiveNode();
    }

    public void checkActiveNode() {
        outer:
        for (SystemNodeView nodeView : systemNodeViews) {
            for (PortView portView : nodeView.getInputPortViews()) {
                if (portView.getPort().getConnectedWire() == null) {
                    nodeView.getSystemNode().setActive(false);
                    nodeView.switchIndicator(false);
                    continue outer;
                }
            }
            for (PortView portView : nodeView.getOutputPortViews()) {
                if (portView.getPort().getConnectedWire() == null) {
                    nodeView.getSystemNode().setActive(false);
                    nodeView.switchIndicator(false);
                    continue outer;
                }
            }
            nodeView.getSystemNode().setActive(true);
            nodeView.switchIndicator(true);
        }
    }

    private Color getWireColor(Wire wire) {
        if (wire.getDestinationPort() == null) return DRAGGING_COLOR;
        if (wire.getSourcePort() instanceof SquarePort) return SQUARE_COLOR;
        if (wire.getSourcePort() instanceof TrianglePort) return TRIANGLE_COLOR;
        return Color.GRAY;
    }
}

