package com.blueprinthell.view;

import com.blueprinthell.model.Port;
import com.blueprinthell.model.SquarePort;
import com.blueprinthell.model.SystemNode;
import com.blueprinthell.model.TrianglePort;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class SystemNodeView extends StackPane {


    private static final double NODE_WIDTH = 80;
    private static final double NODE_HEIGHT = 120;
    private static final double PORT_SIZE = 6;
    private static final double PORT_SPACING = 20;
    private static final double INDICATOR_WIDTH = NODE_WIDTH /2;
    private static final double INDICATOR_HEIGHT = 20;

    private SystemNode systemNode;
    private Pane inputPortPane;
    private Pane outputPortPane;
    private Pane indicatorPane;
    private Rectangle indicator;

    public SystemNodeView(SystemNode systemNode) {
        this.systemNode = systemNode;
        this.inputPortPane = new Pane();
        this.outputPortPane = new Pane();

        setupBackground();
        setupIndicator();
        setupPorts();

        setLayoutX(systemNode.getPosition().getWidth());
        setLayoutY(systemNode.getPosition().getHeight());

    }

    private void setupBackground() {
        Rectangle background = new Rectangle(NODE_WIDTH, NODE_HEIGHT);
        Rectangle indicatorPanel = new Rectangle(NODE_WIDTH, INDICATOR_HEIGHT + 10);
        indicatorPanel.setLayoutX(0);
        indicatorPanel.setLayoutY(0);
        getChildren().add(background);
    }

    private void setupIndicator() {
        indicator = new Rectangle(INDICATOR_WIDTH, INDICATOR_HEIGHT);
        indicator.setLayoutX((NODE_WIDTH - INDICATOR_WIDTH) / 2);
        indicator.setLayoutY((NODE_HEIGHT - INDICATOR_HEIGHT - 10) / 2);
        getChildren().add(indicator);
    }

    private void setupPorts() {
        List<Port> inputPorts = systemNode.getInputPorts();
        List<Port> outputPorts = systemNode.getOutputPorts();

        double startY = -(PORT_SPACING * (inputPorts.size() - 1)) / 2;

        for (int i = 0; i < inputPorts.size(); i++) {
            Port port = inputPorts.get(i);
            Polygon portView = createPortView(port);
            portView.setLayoutX(-NODE_WIDTH / 2 - PORT_SIZE);
            portView.setLayoutY(startY + i * PORT_SPACING);
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
}
