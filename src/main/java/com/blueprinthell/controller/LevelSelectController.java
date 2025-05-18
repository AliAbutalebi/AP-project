package com.blueprinthell.controller;

import com.blueprinthell.map.MapLoader;
import com.blueprinthell.model.ScenePath;
import com.blueprinthell.model.ScreenDimensions;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class LevelSelectController extends BaseController {

    private static final ScreenDimensions screenDimensions = ScreenDimensions.getInstance();

    private static final double MAP_WIDTH = screenDimensions.getWidth() / 4;
    private static final double MAP_HEIGHT = screenDimensions.getHeight() / 2;
    private static final Color BACKGROUND_COLOR = Color.web("#E6E6E6");
    private static final Color BORDER_COLOR = Color.web("#B3B3B3");

    @FXML
    private AnchorPane rootPane;
    @FXML
    private HBox mapsPane;


    @FXML
    public void initialize() {
        for (int i = 0; i < MapLoader.getMapFiles().length; i++) {
            File mapFile = MapLoader.getMapFiles()[i];

            StackPane mapPane = new StackPane();
            mapPane.setPrefSize(MAP_WIDTH, MAP_HEIGHT);
            mapPane.setAlignment(Pos.CENTER);
            mapsPane.getChildren().add(mapPane);

            Rectangle background = new Rectangle(MAP_WIDTH, MAP_HEIGHT);
            background.setFill(BACKGROUND_COLOR);
            background.setStroke(BORDER_COLOR);
            background.setStrokeWidth(5);
            background.setArcWidth(10);
            background.setArcHeight(10);
            mapPane.getChildren().add(background);

            VBox mapContentPane = new VBox();
            mapContentPane.setPrefWidth(MAP_WIDTH);
            mapContentPane.setPrefHeight(MAP_HEIGHT);
            mapContentPane.setAlignment(Pos.CENTER);
            mapPane.getChildren().add(mapContentPane);

            Label mapNameLabel = new Label(MapLoader.loadMap(mapFile).getMapName().toUpperCase());
            mapNameLabel.getStyleClass().add("monograf-bold");
            mapNameLabel.setStyle(String.format("-fx-font-size: %d;", (int) MAP_HEIGHT / 4));
            mapContentPane.getChildren().add(mapNameLabel);

            Button selectMapButton = new Button("SELECT");
            selectMapButton.getStyleClass().add("monograf-bold");
            selectMapButton.setPrefWidth(MAP_WIDTH * 0.8);
            mapContentPane.getChildren().add(selectMapButton);
            int finalI = i;
            selectMapButton.setOnAction(e -> {
                MapLoader.setSelectedMap(finalI);
            });

        }

        HBox returnPane = new HBox();
        returnPane.setAlignment(Pos.CENTER);
        rootPane.getChildren().add(returnPane);
        AnchorPane.setBottomAnchor(returnPane, 0.0);
        returnPane.setPrefWidth(screenDimensions.getWidth());
        returnPane.setPrefHeight(screenDimensions.getHeight() / 10);

        Button returnButton = new Button("RETURN TO MENU");
        returnButton.getStyleClass().add("monograf-bold");
        returnButton.setPrefWidth(MAP_WIDTH * 0.8);
        returnPane.getChildren().add(returnButton);
        returnButton.setLayoutY(screenDimensions.getHeight() - 100);
        returnButton.setOnAction(e -> {
            try {
                super.switchScene(ScenePath.MAIN_MENU.getResourceURL(), e);
            } catch (Exception ignored) {
            }
        });

    }
}
