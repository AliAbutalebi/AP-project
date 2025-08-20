package com.blueprinthell.view;

import com.blueprinthell.model.HUD;
import com.blueprinthell.model.ScreenDimensions;
import com.blueprinthell.model.shop.Shop;
import com.blueprinthell.model.shop.ShopItem;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;

public class HUDView extends AnchorPane {
    private static final double WIDTH = ScreenDimensions.getInstance().getWidth() / 3;
    private static final double HEIGHT = ScreenDimensions.getInstance().getHeight() / 3;
    private static final double TITLE_PANE_HEIGHT = HEIGHT / 5;
    private static final Color BACKGROUND_COLOR = Color.web("#E6E6E6");
    private static final Color BORDER_COLOR = Color.web("#B3B3B3");
    private static final double CONTENT_SPACING = 35;
    private static HUDView instance;
    private HUD hud;
    private Shop shop = Shop.getInstance();
    private Rectangle background;
    private HBox titlePane;
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

        Line line = new Line();
        line.setStroke(BORDER_COLOR);
        line.setStrokeWidth(3);
        getChildren().add(line);
        line.setStartX(0);
        line.setStartY(TITLE_PANE_HEIGHT);
        line.setEndX(WIDTH);
        line.setEndY(TITLE_PANE_HEIGHT);
    }

    private void setupTitle() {
        titlePane = new HBox();
        titlePane.setPrefSize(WIDTH, TITLE_PANE_HEIGHT);
        titlePane.setMinWidth(WIDTH);
        titlePane.setAlignment(Pos.CENTER_LEFT);
        titlePane.setSpacing(30);
        titlePane.setPadding(new Insets(0, 20, 0, 40));
        getChildren().add(titlePane);
        titlePane.setLayoutX(0);
        titlePane.setLayoutY(0);
        setLeftAnchor(titlePane, 0.0);
        setRightAnchor(titlePane, 0.0);
        setTopAnchor(titlePane, 0.0);

        Label hudTitle = new Label("HUD");
        hudTitle.getStyleClass().add("hud-title");
        hudTitle.setTextFill(Color.web("#333333"));
        titlePane.getChildren().add(hudTitle);

        HBox activeItemsPane = new HBox();
        HBox.setHgrow(activeItemsPane, Priority.ALWAYS);
        activeItemsPane.setPrefHeight(TITLE_PANE_HEIGHT);
        titlePane.getChildren().add(activeItemsPane);
        activeItemsPane.setAlignment(Pos.CENTER_RIGHT);
        activeItemsPane.setSpacing(20);

        for (ShopItem item : shop.getActiveItems()) {
            activeItemsPane.getChildren().add(getItemIcon(item));
        }
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
                else if (hud.getContents().keySet().toArray()[i].equals("Temporal Progress")) {
                    content.setText(content.getText() + "s");
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
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);
        fadeIn.play();
        setVisible(true);
    }

    public void hideHUD() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), this);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        fadeOut.setOnFinished(e -> {
            contentPane.getChildren().clear();
            setVisible(false);
        });
        fadeOut.play();

    }

    public double getHUDWidth() {
        return WIDTH;
    }

    public double getHUDHeight() {
        return HEIGHT;
    }

    public void update() {
        hud.update();
        titlePane.getChildren().clear();
        contentPane.getChildren().clear();
        setupTitle();
        setupContent();
    }

    private ImageView getItemIcon(ShopItem item) {
        ImageView itemIcon = new ImageView();
        itemIcon.setPreserveRatio(true);
        itemIcon.setFitHeight(TITLE_PANE_HEIGHT / 2);

        switch (item.getType()) {
            case OATAR -> {
                itemIcon.setImage(new Image(new File("./src/main/resources/com/blueprinthell/image/shop-items/oatar.png").toURI().toString()));
            }
            case OAIRYAMAN -> {
                itemIcon.setImage(new Image(new File("./src/main/resources/com/blueprinthell/image/shop-items/oairyaman.png").toURI().toString()));
            }
            case OANAHITA -> {
                itemIcon.setImage(new Image(new File("./src/main/resources/com/blueprinthell/image/shop-items/oanahita.png").toURI().toString()));
            }
            case AERGIA -> {
                itemIcon.setImage(new Image(new File("./src/main/resources/com/blueprinthell/image/shop-items/aergia.png").toURI().toString()));
            }
            case SISYPHUS -> {
                itemIcon.setImage(new Image(new File("./src/main/resources/com/blueprinthell/image/shop-items/sisyphus.png").toURI().toString()));
            }
            case ELIPHAS -> {
                itemIcon.setImage(new Image(new File("./src/main/resources/com/blueprinthell/image/shop-items/eliphas.png").toURI().toString()));
            }
        }
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-1.0);
        colorAdjust.setContrast(1.0);

        itemIcon.setEffect(colorAdjust);
        return itemIcon;
    }
}
