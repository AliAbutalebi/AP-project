package com.blueprinthell.view;

import com.blueprinthell.model.*;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class SystemNodeView extends AnchorPane {

    private static final double NODE_WIDTH = ScreenDimensions.getInstance().getWidth() / 11;
    private double nodeHeight;
    private static final double NODE_STROKE = 5;
    private static final double NODE_RADIUS = 10;
    private static final double PORT_SPACING = 30;
    private static final double PACKET_SPACING = 40;
    private static final double PORT_PANE_WIDTH = 25;
    private static final double INDICATOR_WIDTH = NODE_WIDTH / 4;
    private static final double INDICATOR_HEIGHT = 10;
    private static final double INDICATOR_PANEL_HEIGHT = INDICATOR_HEIGHT + 15;
    private static final double INDICATOR_STROKE = 2;
    private static final double RUN_BUTTON_WIDTH = NODE_WIDTH - 2 * PORT_PANE_WIDTH;
    private static final double RUN_BUTTON_HEIGHT = ScreenDimensions.getInstance().getHeight() / 25;
    private AnimationTimer antiTrojanTimer;
    private Circle antiTrojanCircle;

    private SystemNode systemNode;
    private Pane inputPortPane;
    private Pane outputPortPane;
    private Pane indicatorPane;
    private HBox labelPane;
    private Pane queuePane;
    private Rectangle background;
    private Rectangle indicatorPanel;
    private Rectangle indicator;
    Rectangle inportPanel;
    Rectangle outportPanel;
    private Button runButton;
    private final ArrayList<PortView> inputPortViews;
    private final ArrayList<PortView> outputPortViews;
    private final ArrayList<PacketView> packetViews;

    public SystemNodeView(SystemNode systemNode) {
        this.systemNode = systemNode;
        this.inputPortPane = new Pane();
        this.outputPortPane = new Pane();
        this.inputPortViews = new ArrayList<>();
        this.outputPortViews = new ArrayList<>();
        this.packetViews = new ArrayList<>();

        setupBackground();
        setupPorts();
        setupIndicator();
        getChildren().add(indicatorPane);
        setupPackets();

        setLayoutX(systemNode.getLocation().getX());
        setLayoutY(systemNode.getLocation().getY());

        if (systemNode.getSystemType() == SystemType.ANTI_TROJAN) {
            setupAntiTrojan();
        }
    }

    private void setupBackground() {
        calculateNodeHeight();

        background = new Rectangle(NODE_WIDTH, nodeHeight);
        background.setFill(Color.web("#4D4D4D"));
        background.setStroke(Color.web("#666666"));
        background.setStrokeWidth(NODE_STROKE);
        background.setArcWidth(NODE_RADIUS);
        background.setArcHeight(NODE_RADIUS);

        indicatorPane = new Pane();
        indicatorPanel = new Rectangle(NODE_WIDTH, INDICATOR_PANEL_HEIGHT);
        indicatorPanel.setFill(Color.web("#3D3D3D"));
        indicatorPanel.setStroke(Color.web("#666666"));
        indicatorPanel.setArcWidth(NODE_RADIUS);
        indicatorPanel.setArcHeight(NODE_RADIUS);

        indicatorPane.getChildren().add(indicatorPanel);
        getChildren().add(0, background);

        indicatorPanel.setLayoutX(0);
        indicatorPanel.setLayoutY(0);
    }

    private void setupIndicator() {
        indicator = new Rectangle(INDICATOR_WIDTH, INDICATOR_HEIGHT);
        indicator.setFill(Color.web("#222222"));
        indicator.setStroke(Color.web("#666666"));
        indicator.setStrokeWidth(INDICATOR_STROKE);
        indicator.setLayoutX(10);
        indicator.setLayoutY((INDICATOR_PANEL_HEIGHT - INDICATOR_HEIGHT) / 2);
        indicator.setArcWidth(10);
        indicator.setArcHeight(10);

        indicatorPane.getChildren().add(indicator);
    }

    private void setupPorts() {
        ArrayList<Port> inputPorts = systemNode.getInputPorts();
        ArrayList<Port> outputPorts = systemNode.getOutputPorts();

        inportPanel = new Rectangle(PORT_PANE_WIDTH, nodeHeight);
        outportPanel = new Rectangle(PORT_PANE_WIDTH, nodeHeight);
        inportPanel.setFill(Color.web("#666666"));
        outportPanel.setFill(Color.web("#666666"));

        inputPortPane.setMaxWidth(PORT_PANE_WIDTH);
        inputPortPane.setLayoutX(0);
        inputPortPane.setLayoutY(0);

        outputPortPane.setMaxWidth(PORT_PANE_WIDTH);
        outputPortPane.setLayoutX(NODE_WIDTH - PORT_PANE_WIDTH);
        outputPortPane.setLayoutY(0);

        inputPortPane.getChildren().add(inportPanel);
        outputPortPane.getChildren().add(outportPanel);

        double startY = INDICATOR_HEIGHT + 30;

        for (int i = 0; i < inputPorts.size(); i++) {
            Port port = inputPorts.get(i);
            PortView portView = new PortView(port);
            portView.setLayoutX(-NODE_STROKE / 2);
            portView.setLayoutY(startY + i * PORT_SPACING);
            inputPortViews.add(portView);
            inputPortPane.getChildren().add(portView);
        }

        for (int i = 0; i < outputPorts.size(); i++) {
            Port port = outputPorts.get(i);
            PortView portView = new PortView(port);
            portView.setLayoutX(PORT_PANE_WIDTH + (NODE_STROKE / 2));
            portView.setLayoutY(startY + i * PORT_SPACING);
            outputPortViews.add(portView);
            outputPortPane.getChildren().add(portView);
        }

        getChildren().addAll(inputPortPane, outputPortPane);
    }

    private void setupPackets() {
        ArrayList<Packet> packets = new ArrayList<>(systemNode.getPacketQueue());

        queuePane = new Pane();
        getChildren().add(queuePane);
        queuePane.setLayoutX(PORT_PANE_WIDTH);
        queuePane.setPrefWidth(RUN_BUTTON_WIDTH);
        if (systemNode.getSystemType() == SystemType.REFERENCE) {
            queuePane.setLayoutY(INDICATOR_HEIGHT + RUN_BUTTON_HEIGHT);
            queuePane.setPrefHeight(nodeHeight - INDICATOR_HEIGHT - RUN_BUTTON_HEIGHT);
        } else {
            queuePane.setLayoutY(INDICATOR_PANEL_HEIGHT);
            queuePane.setPrefHeight(nodeHeight - INDICATOR_PANEL_HEIGHT);
        }

        for (int i = 0; i < packets.size(); i++) {
            Packet packet = packets.get(i);
            PacketView packetView = new PacketView(packet);
            packetView.setLayoutX(queuePane.getPrefWidth() / 2);
            packetView.setLayoutY((i + 2) * PACKET_SPACING);
            packetViews.add(packetView);
            queuePane.getChildren().add(packetView);
        }
    }

    public void setupLabel() {
        labelPane = new HBox();
        labelPane.setPrefSize(NODE_WIDTH, INDICATOR_PANEL_HEIGHT);
        labelPane.setAlignment(Pos.CENTER_RIGHT);
        getChildren().add(labelPane);
        labelPane.setLayoutX(0);
        labelPane.setLayoutY(0);
        labelPane.setPadding(new Insets(0, 5, 0, 0));

        Label label = new Label(systemNode.getSystemType().name().replace("_", " "));
        label.getStyleClass().add("monograf-bold");
        label.setStyle(String.format("-fx-font-size: %d", (int) INDICATOR_PANEL_HEIGHT / 2));
        label.setTextFill(Color.WHITE);
        labelPane.getChildren().add(label);
    }

    public void setupRunButton() {
        runButton = new Button("Run");
        runButton.setPrefWidth(RUN_BUTTON_WIDTH);
        runButton.setPrefHeight(RUN_BUTTON_HEIGHT);
        getChildren().add(runButton);
        runButton.setLayoutX(PORT_PANE_WIDTH);
        runButton.setLayoutY(INDICATOR_PANEL_HEIGHT);
    }

    private void setupAntiTrojan() {
        antiTrojanCircle = new Circle();
        getChildren().add(0, antiTrojanCircle);
        antiTrojanCircle.setCenterX(background.getWidth() / 2);
        antiTrojanCircle.setCenterY(background.getHeight() / 2);
        antiTrojanCircle.setFill(Color.TRANSPARENT);
        antiTrojanCircle.setStroke(Color.BLUE);
        antiTrojanCircle.setStrokeWidth(30);
        antiTrojanCircle.setRadius(0);
        antiTrojanCircle.setMouseTransparent(true);

        antiTrojanTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                antiTrojanCircle.setRadius(antiTrojanCircle.getRadius() + 2);
                antiTrojanCircle.setOpacity((1 - (antiTrojanCircle.getRadius() / SystemNode.getAntiTrojanRadius())) / 2);
                if (antiTrojanCircle.getRadius() > SystemNode.getAntiTrojanRadius()) {
                    antiTrojanCircle.setRadius(0);
                    antiTrojanCircle.setOpacity(1);
                }
            }
        };
        antiTrojanTimer.start();
    }

    public void switchAntiTrojan(boolean isActive) {
        if (isActive) {
            antiTrojanTimer.start();
        } else {
            antiTrojanTimer.stop();
            antiTrojanCircle.setRadius(0);
        }
    }

    public void switchIndicator(boolean isReady, boolean isActive) {
        if (isReady && !isActive) indicator.setFill(Color.web("#FF0000"));
        else if (isReady) indicator.setFill(Color.web("#00FEFE"));
        else indicator.setFill(Color.web("#222222"));
    }

    public List<PortView> getInputPortViews() {
        return inputPortViews;
    }

    public List<PortView> getOutputPortViews() {
        return outputPortViews;
    }

    public void setSystemNode(SystemNode systemNode) {
        this.systemNode = systemNode;
    }

    public SystemNode getSystemNode() {
        return systemNode;
    }

    public Button getRunButton() {
        return runButton;
    }

    public void update() {
        queuePane.getChildren().clear();
        updateSystemNodeSize();
        setupPackets();
        switchIndicator(systemNode.isReady(), systemNode.isActive());

        if (systemNode.getSystemType() == SystemType.ANTI_TROJAN) {
            antiTrojanTimer.stop();
            getChildren().remove(antiTrojanCircle);
            setupAntiTrojan();
        }
    }

    private void updateSystemNodeSize() {
        calculateNodeHeight();

        setHeight(nodeHeight);
        background.setHeight(nodeHeight);

        inportPanel.setHeight(nodeHeight);
        outportPanel.setHeight(nodeHeight);

        queuePane.setPrefHeight(nodeHeight - INDICATOR_PANEL_HEIGHT);
    }

    private void calculateNodeHeight() {
        double heightByPackets = (systemNode.getPacketQueue().size() + 2) * PACKET_SPACING + INDICATOR_PANEL_HEIGHT;
        double heightByPorts = (Math.max(systemNode.getInputPorts().size(), systemNode.getOutputPorts().size())) * PORT_SPACING + INDICATOR_PANEL_HEIGHT;
        nodeHeight = Math.max(heightByPackets, heightByPorts);
        if (systemNode.getSystemType() == SystemType.REFERENCE) {
            nodeHeight += RUN_BUTTON_HEIGHT;
        }
    }
}
