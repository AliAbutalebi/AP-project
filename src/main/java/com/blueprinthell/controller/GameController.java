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

import java.util.*;

public class GameController {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane wirePane;
    @FXML
    private AnchorPane packetPane;
    @FXML
    private AnchorPane systemNodePane;

    private GameMap gameMap;

    private final List<SystemNodeView> systemNodeViews = new ArrayList<>();
    private final List<WireView> wireViews = new ArrayList<>();
    private final List<PacketView> packetViews = new ArrayList<>();

    private final Map<SystemNode, SystemNodeView> nodeToView = new HashMap<>();
    private final Map<Wire, WireView> wireToView = new HashMap<>();
    private final Map<Packet, PacketView> packetToView = new HashMap<>();

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
        for (Packet packet : gameMap.getActivePackets()) {
            PacketView packetView = new PacketView(packet);
            packetViews.add(packetView);
            packetToView.put(packet, packetView);
        }

        for (SystemNode node : gameMap.getSystemNodes()) {
            SystemNodeView nodeView = new SystemNodeView(node);
            if (nodeView.getSystemNode() instanceof ReferenceSystemNode) {
                nodeView.setupReferenceLabel();
                nodeView.setupRunButton();
                setReferenceSystemNodeQueue((ReferenceSystemNode) node);
            }

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

        for (Wire wire : gameMap.getWires()) {
            WireView wireView = new WireView(wire);
            wirePane.getChildren().add(wireView);
            wireViews.add(wireView);
            wireToView.put(wire, wireView);
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
        } else if (portView.getPort().isOccupied()) {
            removeWire(wireToView.get(portView.getPort().getConnectedWire()));
            return;
        } else {
            startingPortView = portView;
            Wire wire = new Wire(startingPortView.getPort().getLocation(), startingPortView.getPort().getLocation());
            draggingWire = new WireView(wire);
            draggingWire.getWire().setSourcePort(portView.getPort());
            wirePane.getChildren().add(draggingWire);
            draggingWire.setStroke(getWireColor(draggingWire.getWire()));
        }
    }

    private void onWireDragged(MouseEvent event) {
        if (draggingWire == null) {
            return;
        }
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
        checkActiveNode();
    }

    private void finalizeWireConnection(PortView from, PortView to) {

        draggingWire.getWire().setEndLocation(to.getPort().getLocation());
        draggingWire.getWire().setDestinationPort(to.getPort());
        from.getPort().setConnectedWire(draggingWire.getWire());
        from.getPort().setOccupied(true);
        to.getPort().setConnectedWire(draggingWire.getWire());
        to.getPort().setOccupied(true);

        draggingWire.setStroke(getWireColor(draggingWire.getWire()));

        wireViews.add(draggingWire);
        wireToView.put(draggingWire.getWire(), draggingWire);
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
        if (draggingWire != null) {
            draggingWire.updateView();
            wirePane.getChildren().remove(draggingWire);
            draggingWire = null;
            startingPortView = null;
        }
    }

    private boolean isValidConnection(Port from, Port to) {
        return from != to && !from.isInput() && to.isInput() && from.getShapeType() == to.getShapeType() && from.getParentSystemId() != to.getParentSystemId();
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
                    setSystemNodeActive(nodeView, false);
                    continue outer;
                }
            }
            for (PortView portView : nodeView.getOutputPortViews()) {
                if (portView.getPort().getConnectedWire() == null) {
                    setSystemNodeActive(nodeView, false);
                    continue outer;
                }
            }
            setSystemNodeActive(nodeView, true);
        }
    }

    private Color getWireColor(Wire wire) {
        if (wire.getDestinationPort() == null) return DRAGGING_COLOR;
        if (wire.getShapeType() == ShapeType.SQUARE) return SQUARE_COLOR;
        if (wire.getShapeType() == ShapeType.TRIANGLE) return TRIANGLE_COLOR;
        return Color.GRAY;
    }

    private void setSystemNodeActive(SystemNodeView view, boolean active) {
        view.getSystemNode().setActive(active);
        view.switchIndicator(active);
    }

    private void setReferenceSystemNodeQueue(ReferenceSystemNode refNode) {
        for (PacketView packetView : packetViews) {
            refNode.getPacketQueue().add(packetView.getPacket());
        }
    }
}

