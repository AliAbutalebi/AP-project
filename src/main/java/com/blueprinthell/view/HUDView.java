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

public class HUDView extends AnchorPane {
    private static final double WIDTH = ScreenDimensions.getInstance().getWidth() / 3;
    private static final double HEIGHT = ScreenDimensions.getInstance().getHeight() / 3;
    private static final double TITLE_PANE_HEIGHT = HEIGHT / 5;
    private static final Color BACKGROUND_COLOR = Color.web("#E6E6E6");
    private static final Color BORDER_COLOR = Color.web("#B3B3B3");
    private static final double CONTENT_SPACING = 35;
    private static HUDView instance;
    private HUD hud;
    private Rectangle background;
    private Pane titlePane;
    private Pane contentPane = new Pane();

    private HUDView() {
        hud = HUD.getInstance();
        setupBackground();
        setupTitle();
        getChildren().add(contentPane);
        setupContent();
    }

    public static HUDView getInstance() {
        HUDView instance = new HUDView();
        return instance;
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

        Label hudTitle = new Label("HUD");
        hudTitle.getStyleClass().add("hud-title");
        hudTitle.setTextFill(Color.web("#333333"));
        titlePane.getChildren().add(hudTitle);
        hudTitle.setLayoutX(20);
        Platform.runLater(() -> {
            hudTitle.setLayoutY(titlePane.getHeight() / 2 - hudTitle.getHeight() / 2);
        });

        Line line = new Line();
        line.setStroke(BORDER_COLOR);
        line.setStrokeWidth(3);
        titlePane.getChildren().add(line);
        line.setStartX(0);
        line.setStartY(TITLE_PANE_HEIGHT);
        line.setEndX(WIDTH);
        line.setEndY(TITLE_PANE_HEIGHT);
    }

    private void setupContent() {
        HUD.update();
        contentPane.setPrefSize(WIDTH, HEIGHT - TITLE_PANE_HEIGHT);
        contentPane.setLayoutX(0);
        contentPane.setLayoutY(TITLE_PANE_HEIGHT);
        for (int i = 0; i < hud.getContents().size(); i++) {
            Label title = new Label(hud.getContents().keySet().toArray()[i] + ":");
            title.getStyleClass().add("hud-content-title");
            title.setTextFill(Color.web("#333333"));
            contentPane.getChildren().add(title);
            title.setLayoutX(40);
            title.setLayoutY(TITLE_PANE_HEIGHT + i * CONTENT_SPACING);

            Object contentObject = hud.getContents().values().toArray()[i];
            Label content = new Label(contentObject.toString());
            content.setTextFill(Color.web("#4D4D4D"));
            if (contentObject instanceof Double) {
                Double d = (Double) contentObject;
                content = new Label(Math.round(d) + "");
                if (d < 0) {
                    content.setTextFill(Color.web("#FF0000"));
                }
                if (hud.getContents().keySet().toArray()[i].equals("Packet Loss")) {
                 content.setText(content.getText() + "%");
                }
            }
            else if (contentObject instanceof Integer) {
                content = new Label(contentObject.toString());
            }
            content.getStyleClass().add("hud-content");
            contentPane.getChildren().add(content);
            content.setLayoutY(TITLE_PANE_HEIGHT + i * CONTENT_SPACING);
            content.setLayoutX(WIDTH / 2);
        }
    }

    public void showHUD() {
        setupContent();
        setVisible(true);
    }

    public void hideHUD() {
        contentPane.getChildren().clear();
        setVisible(false);
    }

    public double getHUDWidth() {
        return WIDTH;
    }

    public double getHUDHeight() {
        return HEIGHT;
    }

    public void update() {
        hud.update();
        contentPane.getChildren().clear();
        setupContent();
    }
}
