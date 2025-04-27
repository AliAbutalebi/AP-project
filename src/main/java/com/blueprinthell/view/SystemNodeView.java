package com.blueprinthell.view;

import com.blueprinthell.model.SystemNode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SystemNodeView extends StackPane {


    private static final double NODE_WIDTH = 80;
    private static final double NODE_HEIGHT = 120;
    private static final double PORT_RADIUS = 6;
    private static final double PORT_SPACING = 20;
    private static final double INDICATOR_WIDTH = NODE_WIDTH /2;
    private static final double INDICATOR_HEIGHT = 20;

    private SystemNode systemNode;
    private Pane inputPortPane;
    private Pane outputPortPane;
    private Rectangle indicator;

    public SystemNodeView(SystemNode systemNode) {
        this.systemNode = systemNode;
        this.inputPortPane = new Pane();
        this.outputPortPane = new Pane();

        setLayoutX(systemNode.getPosition().getWidth());
        setLayoutY(systemNode.getPosition().getHeight());

    }
}
