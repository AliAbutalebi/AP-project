package com.blueprinthell.view;

import com.blueprinthell.model.HUD;
import com.blueprinthell.model.ScreenDimensions;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class HUDView extends AnchorPane {
    private static final double WIDTH = ScreenDimensions.getInstance().getWidth() / 3;
    private static final double HEIGHT = ScreenDimensions.getInstance().getHeight() / 3;
    private static final double TITLE_PANE_HEIGHT = HEIGHT / 5;
    private static final Color BACKGROUND_COLOR = Color.web("#E6E6E6");
    private static final Color BORDER_COLOR = Color.web("#B3B3B3");
    private static HUDView instance;
    private HUD hud;
    private Rectangle background;
    private Pane titlePane;

    private HUDView() {
        hud = HUD.getInstance();
        setupBackground();
        setupTitle();
    }

    private void setupBackground() {
        background = new Rectangle(WIDTH, HEIGHT);
        background.setFill(BACKGROUND_COLOR);
        background.setArcWidth(20);
        background.setArcHeight(20);
        background.setStroke(BORDER_COLOR);
        background.setStrokeWidth(5);
        getChildren().add(background);
    }

    private void setupTitle() {
        titlePane = new Pane();
        titlePane.setPrefSize(WIDTH, TITLE_PANE_HEIGHT);
        getChildren().add(titlePane);
        titlePane.setLayoutX(0);
        titlePane.setLayoutY(0);
        setLeftAnchor(titlePane, 0.0);
        setRightAnchor(titlePane, 0.0);

        Label title = new Label("HUD");
        title.setFont(Font.loadFont(getClass().getResourceAsStream("/com/blueprinthell/font/Monograf/monograf-bold.ttf"), 16));
        titlePane.getChildren().add(title);
        title.setLayoutX(20);
        Platform.runLater(() -> {
            title.setLayoutY(titlePane.getHeight() / 2 - title.getHeight() / 2);
        });

        Line line = new Line();
        line.setStroke(BORDER_COLOR);
        line.setStrokeWidth(5);
        titlePane.getChildren().add(line);
        line.setStartX(0);
        line.setStartY(TITLE_PANE_HEIGHT);
        line.setEndX(WIDTH);
        line.setEndY(TITLE_PANE_HEIGHT);
    }

    public static HUDView getInstance() {
        HUDView instance = new HUDView();
        return instance;
    }
}
