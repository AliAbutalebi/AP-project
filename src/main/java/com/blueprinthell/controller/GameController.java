package com.blueprinthell.controller;

import com.blueprinthell.audio.MusicPlayer;
import com.blueprinthell.audio.SoundEffectManager;
import com.blueprinthell.log.Logger;
import com.blueprinthell.map.MapLoader;
import com.blueprinthell.model.*;
import com.blueprinthell.view.*;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import org.kordamp.ikonli.fontawesome6.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController extends BaseController {

    private static final Color DRAGGING_COLOR = Color.web("#888888");
    private static final Color SQUARE_COLOR = Color.web("#32c65f");
    private static final Color TRIANGLE_COLOR = Color.web("#FFFF00");
    private static final Color GRID_COLOR = Color.web("#040505");
    private static final double GRID_SIZE = 30;
    private static final double GRID_STROKE = 3;
    private final List<SystemNodeView> systemNodeViews = new ArrayList<>();
    private final List<WireView> wireViews = new ArrayList<>();
    private static final List<PacketView> packetViews = new ArrayList<>();
    private final Map<SystemNode, SystemNodeView> nodeToView = new HashMap<>();
    private final Map<Wire, WireView> wireToView = new HashMap<>();
    private final Map<Packet, PacketView> packetToView = new HashMap<>();
    private SystemNode referenceSystemNode;

    private final ArrayList<Packet> movingPackets = new ArrayList<>();
    private final Map<Packet, Packet> potentialCollisions = new HashMap<>();
    private final double PACKET_PROXIMITY = 20;

    private TopBarView topBarView;
    private final Logger logger = Logger.getInstance();
    private static final ScreenDimensions screenDimensions = ScreenDimensions.getInstance();
    private HUD hud;
    private HUDView hudView;

    private ShopView shopView = ShopView.getInstance();

    private static final MusicPlayer musicPlayer = MusicPlayer.getInstance();
    private static final SoundEffectManager soundEffectManager = SoundEffectManager.getInstance();

    private static final OAtar oAtar = OAtar.getInstance();
    private static final OAiryaman oAiryaman = OAiryaman.getInstance();
    private static final OAnahita oAnahita = OAnahita.getInstance();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane wirePane;
    @FXML
    private AnchorPane packetPane;
    @FXML
    private AnchorPane systemNodePane;
    @FXML
    private AnchorPane hudPane;
    @FXML
    private AnchorPane uiPane;
    private static VBox messagesPane;
    private GameMap gameMap;
    private PortView startingPortView = null;
    private WireView draggingWire = null;

    private AnimationTimer gameLoop;
    private long lastUpdateTime = 0;
    private boolean pause;


    @FXML
    public void initialize() {
        musicPlayer.play();
        drawGrid();
        setGameMap(MapLoader.loadRandomMap());
        rootPane.setOnMouseDragged(this::onWireDragged);
        rootPane.setOnMouseReleased(this::onWireReleased);
        setupHUD();
        hud.setPacketsCount(packetViews.size());
        setupMessagesPane();
        setupTopBarView();
        setupShopButtons();
        resume();
    }

    public void setGameMap(GameMap gameMap) {
        logger.info("setGameMap");
        this.gameMap = gameMap;
        renderInitialMap();
    }

    private void renderInitialMap() {
        logger.info("renderInitialMap");
        for (Packet packet : gameMap.getActivePackets()) {
            PacketView packetView = new PacketView(packet);
            packetViews.add(packetView);
            packetToView.put(packet, packetView);
        }

        for (SystemNode node : gameMap.getSystemNodes()) {
            SystemNodeView nodeView = new SystemNodeView(node);
            if (nodeView.getSystemNode() instanceof ReferenceSystemNode) {
                referenceSystemNode = nodeView.getSystemNode();
                nodeView.setupReferenceLabel();
                nodeView.setupRunButton();
                nodeView.getRunButton().setOnAction(this::startGameLoop);
            }

            systemNodePane.getChildren().add(nodeView);
            systemNodeViews.add(nodeView);
            nodeToView.put(node, nodeView);

            for (PortView portView : nodeView.getInputPortViews()) {
                savePortLocation(portView);
                portView.getPort().setParentSystemNode(node);
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

        // checkRunButton();
    }

    private void startGameLoop(ActionEvent event) {
        logger.info("startGameLoop");
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!pause) {
                    if (lastUpdateTime > 0) {
                        double deltaTime = (now - lastUpdateTime) / 1_000_000_000.0;
                        update(deltaTime);
                    }
                    lastUpdateTime = now;
                }
            }
        };
        gameLoop.start();
        topBarView.getShopButton().setDisable(false);
    }

    private void update(double deltaTime) {
        packetFromSystemsToWires();
        for (Packet packet : movingPackets) {
            movePacketOnWire(packet, deltaTime);
        }

        if (!oAiryaman.isEnabled()) {
            handleCollisions();
        }

        checkArrivals();

        if (oAtar.isEnabled()) {
            if (oAtar.isExpired()) {
                oAtar.disable();
                oAiryaman.resetRemainingTime();
            } else {
                oAtar.updateRemainingTime(deltaTime);
            }
        }

        if (oAiryaman.isEnabled()) {
            if (oAiryaman.isExpired()) {
                oAiryaman.disable();
                oAiryaman.resetRemainingTime();
            } else {
                oAiryaman.updateRemainingTime(deltaTime);
            }
        }
    }

    private void savePortLocation(PortView portView) {
        portView.getPort().setLocation(portView.localToScene(0, 0));
    }

    private void onPortClicked(PortView portView, MouseEvent event) {
        logger.info("onPortClicked");
        if (portView.getPort().isInput()) {
            return;
        } else if (portView.getPort().isOccupied()) {
            removeWire(wireToView.get(portView.getPort().getConnectedWire()));
        } else {
            startingPortView = portView;
            Wire wire = new Wire(startingPortView.getPort().getLocation(), startingPortView.getPort().getLocation());
            draggingWire = new WireView(wire);
            draggingWire.getWire().setSourcePort(portView.getPort());
            wirePane.getChildren().add(draggingWire);
            draggingWire.markDragging();
            soundEffectManager.play("wire");
        }
    }

    private void onWireDragged(MouseEvent event) {
        if (draggingWire == null) {
            return;
        }
        draggingWire.getWire().setEndLocation(new Point2D(event.getX(), event.getY()));
        draggingWire.updateView();

        PortView hoveredPortView = findHoveredInputPort(event.getX(), event.getY());
        if (hoveredPortView != null) {
            if (!isValidConnection(draggingWire.getWire().getSourcePort(), hoveredPortView.getPort())) {
                draggingWire.markInvalid();
            }
        } else {
            draggingWire.markDragging();
        }
    }

    private void onWireReleased(MouseEvent event) {
        logger.info("onWireReleased");
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
        hud.setRemainingWireLength(hud.getRemainingWireLength() - targetPortView.getPort().getConnectedWire().getLength());
        hudView.update();
        checkActiveNode();
        soundEffectManager.play("wire");
    }

    private void finalizeWireConnection(PortView from, PortView to) {
        logger.info("finilizeWireConnection");
        draggingWire.getWire().setEndLocation(to.getPort().getLocation());
        draggingWire.getWire().setDestinationPort(to.getPort());
        from.getPort().setConnectedWire(draggingWire.getWire());
        from.getPort().setOccupied(true);
        to.getPort().setConnectedWire(draggingWire.getWire());
        to.getPort().setOccupied(true);

        switch (draggingWire.getWire().getShapeType()) {
            case SQUARE -> draggingWire.markSquare();
            case TRIANGLE -> draggingWire.markTriangle();
        }

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
        return from != to && !from.isInput() && to.isInput() && from.getShapeType() == to.getShapeType() && from.getParentSystemId() != to.getParentSystemId() && !from.isOccupied() && !to.isOccupied();
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
        // checkRunButton();
    }

    private void setSystemNodeActive(SystemNodeView view, boolean active) {
        view.getSystemNode().setActive(active);
        view.switchIndicator(active);
    }

    private void packetFromSystemsToWires() {
        for (SystemNodeView nodeView : systemNodeViews) {
            SystemNode node = nodeView.getSystemNode();
            Packet packet = node.getPacketQueue().peek();
            if (packet != null && !packet.isReceived()) {
                Port selectedOutputPort = findPort(node, packet);
                if (selectedOutputPort != null) {
                    packet.getParentSystemNode().getPacketQueue().remove(packet);
                    packet.setParentSystemNode(null);

                    packet.setOnWire(true);
                    packet.setCurrentWire(selectedOutputPort.getConnectedWire());
                    packet.setDistanceOnWire(0);

                    nodeToView.get(packet.getCurrentWire().getSourcePort().getParentSystemNode()).update();

                    selectedOutputPort.getConnectedWire().setPacketOnWire(packet);

                    selectedOutputPort.sendPacket(packet);

                    PacketView packetView = packetToView.get(packet);
                    packet.setLocation(selectedOutputPort.getLocation());
                    packetPane.getChildren().add(packetView);
                    movingPackets.add(packet);
                    nodeView.update();
                }
            }
        }
    }

    private Port findPort(SystemNode node, Packet packet) {
        for (Port outputPort : node.getOutputPorts()) {
            if (outputPort.getConnectedWire() != null) {
                if (outputPort.getConnectedWire().getPacketOnWire() == null) {
                    if (outputPort.getShapeType() == packet.getShapeType()) return outputPort;
                }
            }
        }
        for (Port outputPort : node.getOutputPorts()) {
            if (outputPort.getConnectedWire() != null) {
                if (outputPort.getConnectedWire().getPacketOnWire() == null) return outputPort;
            }
        }
        return null;
    }

    private void movePacketOnWire(Packet packet, double deltaTime) {
        calculateNewDistance(packet, deltaTime);
        double progress = packet.getDistanceOnWire() / packet.getCurrentWire().getLength();
        Point2D newLocation = packet.getCurrentWire().interpolate(progress);
        packet.setLocation(newLocation);
        packetToView.get(packet).update();
    }

    private void calculateNewDistance(Packet packet, double deltaTime) {
        double deltaDistance = 0;
        if (packet.getShapeType() == packet.getCurrentWire().getShapeType()) {
            deltaDistance = packet.getBaseSpeed() * deltaTime;
        } else if (packet.getShapeType() == ShapeType.SQUARE) {
            deltaDistance = packet.getBaseSpeed() / 2 * deltaTime;
        } else if (packet.getShapeType() == ShapeType.TRIANGLE) {
            deltaDistance = packet.getCurrentSpeed() * deltaTime;
            packet.setCurrentSpeed(packet.getCurrentSpeed() + packet.getAcceleration() * deltaTime);
        }
        packet.setDistanceOnWire(packet.getDistanceOnWire() + deltaDistance);
    }

    private void checkArrivals() {
        ArrayList<Packet> arrived = new ArrayList<>();
        for (Packet packet : movingPackets) {
            if (packet.getDistanceOnWire() >= packet.getCurrentWire().getLength()) {
                if (packet.getCurrentWire().getDestinationPort().getParentSystemNode().getPacketQueue().size() < SystemNode.getQueueCapacity()) {
                    arrived.add(packet);

                    packet.setParentSystemNode(packet.getCurrentWire().getDestinationPort().getParentSystemNode());
                    packet.getCurrentWire().getDestinationPort().getParentSystemNode().getPacketQueue().add(packet);

                    packet.getCurrentWire().getDestinationPort().receivePacket(packet);

                    SystemNodeView nodeView = nodeToView.get(packet.getCurrentWire().getDestinationPort().getParentSystemNode());
                    PacketView packetView = packetToView.get(packet);
                    packetPane.getChildren().remove(packetView);
                    nodeView.update();

                    if (nodeView.getSystemNode() instanceof ReferenceSystemNode) {
                        packet.setReceived(true);
                    }

                    nodeToView.get(packet.getCurrentWire().getDestinationPort().getParentSystemNode()).update();

                    packet.setCurrentSpeed(packet.getBaseSpeed());
                    packet.setOnWire(false);
                    packet.getCurrentWire().setPacketOnWire(null);
                    packet.setCurrentWire(null);
                    packet.setDistanceOnWire(0);
                    if (packet.getShapeType() == ShapeType.SQUARE) {
                        hud.addCoins(1);
                        hudView.update();
                    } else if (packet.getShapeType() == ShapeType.TRIANGLE) {
                        hud.addCoins(2);
                        hudView.update();
                    }
                    soundEffectManager.play("packet-arrival");
                } else {
                    packetLoss(packet);
                }
            }
        }
        movingPackets.removeAll(arrived);
    }


    private void setupHUD() {
        hud = HUD.getInstance();
        hudView = HUDView.getInstance();
        hudView.setLayoutX(screenDimensions.getWidth() - hudView.getHUDWidth() - 20);
        hudView.setLayoutY(screenDimensions.getHeight() - hudView.getHUDHeight() - 20);
        rootPane.setOnKeyPressed(this::handleHUDEvent);
        hud.setRemainingWireLength(gameMap.getMaxWireLength());
        hudView.setVisible(false);
        hudPane.setMouseTransparent(true);

        Button hudButton = new Button();
        FontIcon hudIcon = new FontIcon(FontAwesomeSolid.INFO);
        hudButton.setGraphic(hudIcon);
        hudButton.setPrefSize(50, 50);
        hudButton.getStyleClass().add("hud-button");
        hudButton.setLayoutX(screenDimensions.getWidth() - hudButton.getPrefWidth() - 20);
        hudButton.setLayoutY(screenDimensions.getHeight() - hudButton.getPrefHeight() - 20);
        hudButton.setOnAction(this::handleHudButton);
        hudButton.requestFocus();

        systemNodePane.getChildren().add(hudButton);
        hudPane.getChildren().add(hudView);
    }

    private void handleHUDEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.CAPS) {
            hud.toggleVisibility();
            if (hud.isVisible()) {
                HUD.update();
                hudView.showHUD();
            } else {
                hudView.hideHUD();
            }
        }
    }

    private void setPotentialCollisions() {
        potentialCollisions.clear();
        for (Packet packet1 : movingPackets) {
            for (Packet packet2 : movingPackets) {
                if (packet1.equals(packet2)) continue;
                else if (potentialCollisions.containsKey(packet2) && potentialCollisions.get(packet2).equals(packet1))
                    continue;
                else {
                    if (packet1.getLocation().distance(packet2.getLocation()) < PACKET_PROXIMITY) {
                        potentialCollisions.put(packet1, packet2);
                    }
                }
            }
        }
    }

    private boolean checkCollision(Packet packet1, Packet packet2) {
        return packetToView.get(packet1).getBoundsInParent().intersects(packetToView.get(packet2).getBoundsInParent());
    }

    private void handleCollisions() {
        setPotentialCollisions();

        for (Map.Entry<Packet, Packet> entry : potentialCollisions.entrySet()) {
            Packet packet1 = entry.getKey();
            Packet packet2 = entry.getValue();
            boolean collided = checkCollision(packet1, packet2);
            if (collided && (!packet1.isColliding() || !packet2.isColliding())) {
                packet1.setColliding(true);
                packet2.setColliding(true);

                entry.getKey().applyCollision();
                packetToView.get(entry.getKey()).applyCollision();

                entry.getValue().applyCollision();
                packetToView.get(entry.getValue()).applyCollision();

                soundEffectManager.play("packet-collision");

                if (!oAtar.isEnabled()) {
                    handleImpacts(getImpactCenter(packet1, packet2));
                }

                handlePacketLoss(packet1);
                handlePacketLoss(packet2);
            } else if (!collided && (packet1.isColliding() || packet2.isColliding())) {
                packet1.setColliding(false);
                packet2.setColliding(false);
            }

        }
    }

    private Point2D getImpactCenter(Packet packet1, Packet packet2) {
        double x = (packet1.getDeviatedLocation().getX() + packet2.getDeviatedLocation().getX()) / 2;
        double y = (packet1.getDeviatedLocation().getY() + packet2.getDeviatedLocation().getY()) / 2;
        return new Point2D(x, y);
    }

    private void handleImpacts(Point2D impactCenter) {
        for (Packet packet : movingPackets) {
            packet.absorbImpact(impactCenter);
            packetToView.get(packet).update();

            if (!packetToView.get(packet).contains(packet.getLocation())) {
                packetLoss(packet);
            }
        }
    }

    private void handlePacketLoss(Packet packet) {
        if (checkPacketLoss(packet)) {
            packetLoss(packet);
        }
    }

    private boolean checkPacketLoss(Packet packet) {
        return packet.getNoise() > packet.getMaxNoise();
    }

    private void packetLoss(Packet packet) {
        packet.setAlive(false);

        packet.getCurrentWire().setPacketOnWire(null);

        packetPane.getChildren().remove(packetToView.get(packet));
        movingPackets.remove(packet);

        hud.setLostPackets(hud.getLostPackets() + 1);
        hudView.update();

        handleGameOver();
    }

    private void handleGameOver() {
        if (checkGameOver()) {
            gameOver();
        }
    }

    private boolean checkGameOver() {
        return (double) hud.getLostPackets() / hud.getPacketsCount() > 0.5;
    }

    private void gameOver() {
        musicPlayer.stop();

        if (gameLoop != null) {
            pause();
        }


        try {
            super.switchScene(ScenePath.GAME_OVER.getResourceURL(), rootPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void drawGrid() {
        for (int x = 0; x <= screenDimensions.getWidth(); x += GRID_SIZE) {
            Line vertical = new Line(x, 0, x, screenDimensions.getHeight());
            vertical.setStroke(GRID_COLOR);
            vertical.setStrokeWidth(GRID_STROKE);
            rootPane.getChildren().add(0, vertical);
        }
        for (int y = 0; y <= screenDimensions.getHeight(); y += GRID_SIZE) {
            Line horizontal = new Line(0, y, screenDimensions.getWidth(), y);
            horizontal.setStroke(GRID_COLOR);
            horizontal.setStrokeWidth(GRID_STROKE);
            rootPane.getChildren().add(0, horizontal);
        }
    }

    private void handleHudButton(ActionEvent event) {
        hud.toggleVisibility();
        if (hud.isVisible()) {
            HUD.update();
            hudView.showHUD();

            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> {
                hudView.hideHUD();
                hud.toggleVisibility();
            });
            pause.play();
        }
    }

    private void checkRunButton() {
        for (SystemNodeView nodeView : systemNodeViews) {
            if (!nodeView.getSystemNode().isActive()) {
                nodeToView.get(referenceSystemNode).getRunButton().setDisable(true);
                return;
            }
        }
        if (hud.getRemainingWireLength() < 0) {
            nodeToView.get(referenceSystemNode).getRunButton().setDisable(true);
            return;
        }
        nodeToView.get(referenceSystemNode).getRunButton().setDisable(false);
    }

    private void setupTopBarView() {
        topBarView = TopBarView.getInstance();
        systemNodePane.getChildren().add(topBarView);
    }

    public void handleTemporalProgress() {
        movingPackets.clear();
        for (SystemNodeView nodeView : systemNodeViews) {
            nodeView.getSystemNode().getPacketQueue().clear();
        }
        referenceSystemNode.getPacketQueue().addAll(packetToView.keySet());
        nodeToView.get(referenceSystemNode).update();

    }

    private void setupShopButtons() {
        topBarView.getShopButton().setDisable(true);
        topBarView.getShopButton().setOnAction(e -> {
            openShopView();
        });

        shopView.getReturnButton().setOnAction(e -> {
           returnFromShopView();
        });
    }

    private void openShopView() {
        pause();
        shopView.update();
        rootPane.getChildren().add(shopView);
    }

    private void returnFromShopView() {
        rootPane.getChildren().remove(shopView);
        resume();
    }

    private void pause() {
        pause = true;
    }

    private void resume() {
        pause = false;
    }

    public static void applyOAnahita() {
        for (PacketView packetView : packetViews) {
            packetView.getPacket().setNoise(0);
            packetView.update();
        }
    }

    private void setupMessagesPane() {
        messagesPane = new VBox();
        messagesPane.setPrefSize(screenDimensions.getWidth() / 5, screenDimensions.getHeight() - 10);
        messagesPane.setAlignment(Pos.BOTTOM_LEFT);
        messagesPane.setSpacing(20);
        messagesPane.setLayoutX(10);
        messagesPane.setLayoutY(0);
        uiPane.getChildren().add(messagesPane);

        AnchorPane.setTopAnchor(messagesPane, 0.0);
        AnchorPane.setLeftAnchor(messagesPane, 0.0);
        AnchorPane.setBottomAnchor(messagesPane, 0.0);
    }

    public static void newMessage(String message, double time) {
        MessageView messageView = new MessageView(message, time);
        messagesPane.getChildren().add(messageView);
    }
}

