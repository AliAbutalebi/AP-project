package com.blueprinthell.controller;

import com.blueprinthell.model.ScreenDimensions;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SettingsController {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private VBox volumePane;
    @FXML
    private Label volumeLabel;
    @FXML
    private Slider volumeSlider;

    private static final ScreenDimensions screenDimensions = ScreenDimensions.getInstance();

    private static final double SLIDER_WIDTH = screenDimensions.getWidth() / 4;
    private static final double SPACING = 10;

    @FXML
    public void initialize() {
        setupVolumePane();
    }

    public void setupVolumePane() {
        AnchorPane.setTopAnchor(volumePane, 0.0);
        AnchorPane.setLeftAnchor(volumePane, 0.0);
        AnchorPane.setRightAnchor(volumePane, 0.0);
        AnchorPane.setBottomAnchor(volumePane, 0.0);
        volumePane.setAlignment(Pos.CENTER);
        volumePane.setSpacing(SPACING);

        volumeLabel.setTextFill(Color.web("#FFFFFF"));

        volumeSlider.setPrefWidth(SLIDER_WIDTH);
        volumeSlider.setMaxWidth(SLIDER_WIDTH);
        volumeSlider.setMin(-20);
        volumeSlider.setMax(20);
        volumeSlider.setValue(0);
        volumeSlider.setMajorTickUnit(4);
        volumeSlider.setMinorTickCount(10);
    }
}
