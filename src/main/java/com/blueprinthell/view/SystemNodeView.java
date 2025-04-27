package com.blueprinthell.view;

import com.blueprinthell.model.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class SystemNodeView extends AnchorPane {


    private static final double NODE_WIDTH = ScreenDimensions.getInstance().getWidth() / 15;
    private static final double NODE_HEIGHT = ScreenDimensions.getInstance().getHeight() / 5;
    private static final double PORT_SIZE = 6;
    private static final double PORT_SPACING = 20;
    private static final double PORT_PANE_WIDTH = 25;
    private static final double INDICATOR_WIDTH = NODE_WIDTH / 3;
    private static final double INDICATOR_HEIGHT = 10;

    private SystemNode systemNode;
    private Pane inputPortPane;
    private Pane outputPortPane;
    private Pane indicatorPane;
    private Rectangle indicatorPanel;
    private Rectangle indicator;

    public SystemNodeView(SystemNode systemNode) {
        this.systemNode = systemNode;
        this.inputPortPane = new Pane();
        this.outputPortPane = new Pane();

        setupBackground();
        setupPorts();
        setupIndicator();
        getChildren().add(indicatorPane);


        setLayoutX(systemNode.getPosition().getWidth());
        setLayoutY(systemNode.getPosition().getHeight());

        getStyleClass().add("system-node");

    }

    private void setupBackground() {
        Rectangle background = new Rectangle(NODE_WIDTH, NODE_HEIGHT);
        background.setFill(Color.web("#4D4D4D"));
        background.setStroke(Color.web("#666666"));
        background.setStrokeWidth(5);
        background.setArcWidth(10);
        background.setArcHeight(10);
        indicatorPane = new Pane();
        indicatorPanel = new Rectangle(NODE_WIDTH, INDICATOR_HEIGHT + 10);
        indicatorPanel.setFill(Color.web("#3D3D3D"));
        indicatorPanel.setArcWidth(10);
        indicatorPanel.setArcHeight(10);
        background.getStyleClass().add("system-node-background");
        indicatorPanel.getStyleClass().add("system-node-indicator-panel");
        indicatorPane.getChildren().add(indicatorPanel);
        getChildren().add(background);
        indicatorPanel.setLayoutX(0);
        indicatorPanel.setLayoutY(0);

    }

    private void setupIndicator() {
        indicator = new Rectangle(INDICATOR_WIDTH, INDICATOR_HEIGHT);
        indicator.setFill(Color.web("#FF0000"));
        indicator.setStroke(Color.web("#666666"));
        indicator.setStrokeWidth(3);
        indicator.setLayoutX(5);
        indicator.setLayoutY(5);
        indicator.setArcWidth(10);
        indicator.setArcHeight(10);
        indicator.getStyleClass().add("indicator");
        indicatorPane.getChildren().add(indicator);
    }

    private void setupPorts() {
        List<Port> inputPorts = systemNode.getInputPorts();
        List<Port> outputPorts = systemNode.getOutputPorts();

        Rectangle inportPanel = new Rectangle(PORT_PANE_WIDTH, NODE_HEIGHT);
        Rectangle outportPanel = new Rectangle(PORT_PANE_WIDTH, NODE_HEIGHT);
        inportPanel.getStyleClass().add("input-port-panel");
        outportPanel.getStyleClass().add("output-port-panel");
        inportPanel.setFill(Color.web("#666666"));
        outportPanel.setFill(Color.web("#666666"));

        inputPortPane.setMaxWidth(PORT_PANE_WIDTH);
        inputPortPane.setLayoutX(0);
        inputPortPane.setLayoutY(0);
        outputPortPane.setMaxWidth(PORT_PANE_WIDTH);
        outputPortPane.setLayoutX(NODE_WIDTH - PORT_PANE_WIDTH);
        outputPortPane.setLayoutY(0);

        inputPortPane.getChildren().addAll(inportPanel);
        outputPortPane.getChildren().addAll(outportPanel);

        double startY = -(PORT_SPACING * (inputPorts.size() - 1)) / 2;

        for (int i = 0; i < inputPorts.size(); i++) {
            Port port = inputPorts.get(i);
            Polygon portView = createPortView(port);
            portView.setLayoutX(-NODE_WIDTH / 2 - PORT_SIZE);
            portView.setLayoutY(startY + i * PORT_SPACING);
            portView.getStyleClass().add("port");
            inputPortPane.getChildren().add(portView);
        }

        startY = -(PORT_SPACING * (outputPorts.size() - 1)) / 2;

        for (int i = 0; i < outputPorts.size(); i++) {
            Port port = outputPorts.get(i);
            Polygon portView = createPortView(port);
            portView.setLayoutX(NODE_WIDTH / 2 + PORT_SIZE);
            portView.setLayoutY(startY + i * PORT_SPACING);
            outputPortPane.getChildren().add(portView);
        }

        getChildren().addAll(inputPortPane, outputPortPane);
    }

    private Polygon createPortView(Port port) {
        if (port instanceof SquarePort) {
            double halfSize = PORT_SIZE / 2;

            Polygon square = new Polygon();
            square.setUserData(port);
            square.getPoints().addAll(
                    -halfSize, -halfSize,
                    halfSize, -halfSize,
                    halfSize, halfSize,
                    -halfSize, halfSize
            );
            return square;
        }

        else if (port instanceof TrianglePort) {
            double height = Math.sqrt(3) / 2 * PORT_SIZE;
            double halfBase = PORT_SIZE / 2;

            Polygon triangle = new Polygon();
            triangle.setUserData(port);
            triangle.getPoints().addAll(
                    -halfBase, height / 2,
                    halfBase, height / 2,
                    0.0, -height / 2
            );
            return triangle;
        }
        return null;
    }

    public void switchIndicator(boolean isActive) {
        if (isActive) {
            indicator.setFill(Color.web("#0000FF"));
        }
        else {
            indicator.setFill(Color.web("#FF0000"));
        }
    }
}
