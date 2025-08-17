package com.blueprinthell.view;

import com.blueprinthell.controller.GameController;
import com.blueprinthell.model.ScreenDimensions;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.kordamp.ikonli.fontawesome6.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome6.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class TopBarView extends AnchorPane {
    private static TopBarView instance;

    private static final ScreenDimensions screenDimensions = ScreenDimensions.getInstance();

    private HBox contentPane = new HBox();
    private StackPane mapTitlePane = new StackPane();
    private StackPane temporalProgressPane = new StackPane();
    private StackPane shopButtonPane = new StackPane();

    private Slider temporalProgressSlider;
    private Button shopButton;
    private Label mapTitleLabel;

    private GameController gameController;

    private static final double BAR_WIDTH = screenDimensions.getWidth();
    private static final double BAR_HEIGHT = screenDimensions.getHeight() / 8;
    private static final double MAP_TITLE_WIDTH = BAR_WIDTH / 12;
    private static final double BAR_CONTENT_HEIGHT = BAR_HEIGHT * 0.6;
    private static final double TEMPORAL_PROGRESS_WIDTH = BAR_WIDTH * 0.8;
    private static final double SLIDER_WIDTH = TEMPORAL_PROGRESS_WIDTH * 0.94;
    // private static final double SLIDER_HEIGHT = BAR_CONTENT_HEIGHT * 0.625;

    private TopBarView() {
        setupBackground();

        getChildren().add(contentPane);
        contentPane.setAlignment(Pos.CENTER);
        AnchorPane.setLeftAnchor(contentPane, 0.0);
        AnchorPane.setRightAnchor(contentPane, 0.0);
        AnchorPane.setTopAnchor(contentPane, 0.0);
        AnchorPane.setBottomAnchor(contentPane, 0.0);
        contentPane.setSpacing(20);

        setupMapTitle();
        setupTemporalProgress();
        setupShopButton();
    }

    public static TopBarView getInstance() {
        if (instance == null) {
            instance = new TopBarView();
        }
        return instance;
    }

    private void setupBackground() {
        setWidth(BAR_WIDTH);
        setHeight(BAR_HEIGHT);
        setLayoutX(0);
        setLayoutY(0);

        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);
        Rectangle background = new Rectangle();
        background.setFill(Color.web("#666666"));
        background.setStroke(Color.web("#4d4d4d"));
        background.setStrokeWidth(5);
        background.setWidth(BAR_WIDTH);
        background.setHeight(BAR_HEIGHT);
        getChildren().add(0, background);
        background.setLayoutX(0);
        background.setLayoutY(0);

    }

    private void setupMapTitle() {
        mapTitlePane.setAlignment(Pos.CENTER);

        mapTitlePane.setPrefWidth(MAP_TITLE_WIDTH);
        mapTitlePane.setPrefHeight(BAR_CONTENT_HEIGHT);
        contentPane.getChildren().add(mapTitlePane);


        Rectangle mapTitleBackground = new Rectangle();
        mapTitleBackground.setFill(Color.web("#333333"));
        mapTitleBackground.setStroke(Color.web("#2D2D2D"));
        mapTitleBackground.setStrokeWidth(5);
        mapTitleBackground.setArcWidth(10);
        mapTitleBackground.setArcHeight(10);
        mapTitleBackground.setWidth(MAP_TITLE_WIDTH);
        mapTitleBackground.setHeight(BAR_CONTENT_HEIGHT);
        mapTitlePane.getChildren().add(mapTitleBackground);


        mapTitleLabel = new Label("MAP1");
        mapTitleLabel.setTextFill(Color.WHITE);
        mapTitleLabel.setStyle(String.format("-fx-font-size: %d", (int) BAR_CONTENT_HEIGHT / 2));
        mapTitleLabel.getStyleClass().add("monograf-bold");
        mapTitlePane.getChildren().add(mapTitleLabel);

    }

    private void setupTemporalProgress() {
        temporalProgressPane.setAlignment(Pos.CENTER);
        temporalProgressPane.setPrefWidth(TEMPORAL_PROGRESS_WIDTH);
        temporalProgressPane.setPrefHeight(BAR_CONTENT_HEIGHT);
        contentPane.getChildren().add(temporalProgressPane);

        Rectangle temporalProgressBackground = new Rectangle();
        temporalProgressBackground.setFill(Color.web("#333333"));
        temporalProgressBackground.setStroke(Color.web("#2D2D2D"));
        temporalProgressBackground.setStrokeWidth(5);
        temporalProgressBackground.setArcWidth(BAR_CONTENT_HEIGHT);
        temporalProgressBackground.setArcHeight(BAR_CONTENT_HEIGHT);
        temporalProgressBackground.setWidth(TEMPORAL_PROGRESS_WIDTH);
        temporalProgressBackground.setHeight(BAR_CONTENT_HEIGHT);
        temporalProgressPane.getChildren().add(temporalProgressBackground);

        temporalProgressSlider = new Slider();
        temporalProgressSlider.setMaxWidth(SLIDER_WIDTH);
        temporalProgressSlider.getStyleClass().add("temporal-progress");
        temporalProgressSlider.setMin(0);
        temporalProgressSlider.setMax(120);
        temporalProgressSlider.setValue(0);

        temporalProgressSlider.setOnMouseReleased(event -> {
            double time = temporalProgressSlider.getValue();
            gameController.handleTemporalProgress(time);
        });

        temporalProgressPane.getChildren().add(temporalProgressSlider);
    }

    private void setupShopButton() {
        shopButtonPane.setAlignment(Pos.CENTER);
        shopButtonPane.setPrefWidth(BAR_CONTENT_HEIGHT);
        shopButtonPane.setPrefHeight(BAR_CONTENT_HEIGHT);
        contentPane.getChildren().add(shopButtonPane);

        FontIcon shopIcon = FontIcon.of(FontAwesomeSolid.SHOPPING_CART);
        shopIcon.setIconSize((int) BAR_CONTENT_HEIGHT / 2);
        shopButton = new Button();
        shopButton.setGraphic(shopIcon);
        shopButton.setPrefWidth(BAR_CONTENT_HEIGHT);
        shopButton.setPrefHeight(BAR_CONTENT_HEIGHT);
        shopButtonPane.getChildren().add(shopButton);
    }

    public Button getShopButton() {
        return shopButton;
    }

    public Slider getTemporalProgressSlider() {
        return temporalProgressSlider;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void updateMapTitle(int mapLevel) {
        mapTitleLabel.setText("MAP" + mapLevel);
    }
}
