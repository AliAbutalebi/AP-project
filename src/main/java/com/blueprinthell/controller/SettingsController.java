package com.blueprinthell.controller;

import com.blueprinthell.audio.MusicPlayer;
import com.blueprinthell.model.ScreenDimensions;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class SettingsController extends BaseController {

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

    MusicPlayer musicPlayer = MusicPlayer.getInstance();
    SoundEffectManager soundEffectManager = SoundEffectManager.getInstance();

    @FXML
    public void initialize() {
        setupVolumePane();
        setupReturnButton();
        musicPlayer.play();
    }

    private void setupVolumePane() {
        AnchorPane.setTopAnchor(volumePane, 0.0);
        AnchorPane.setLeftAnchor(volumePane, 0.0);
        AnchorPane.setRightAnchor(volumePane, 0.0);
        AnchorPane.setBottomAnchor(volumePane, 0.0);
        volumePane.setAlignment(Pos.CENTER);
        volumePane.setSpacing(SPACING);

        volumeLabel.setTextFill(Color.web("#FFFFFF"));

        volumeSlider.setPrefWidth(SLIDER_WIDTH);
        volumeSlider.setMaxWidth(SLIDER_WIDTH);
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeSlider.setValue(10);
        volumeSlider.setMajorTickUnit(10);
        volumeSlider.setMinorTickCount(10);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            musicPlayer.setVolume(newValue.doubleValue() / 100);
        });
    }
    private void setupSfxVolumePane() {
        sfxSlider.setPrefWidth(SLIDER_WIDTH);
        sfxSlider.setMaxWidth(SLIDER_WIDTH);
        sfxSlider.setMin(0);
        sfxSlider.setMax(100);
        sfxSlider.setValue(10);
        sfxSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            soundEffectManager.setVolume(newValue.doubleValue() / 100);
        });
    }

    private void setupReturnButton () {
        Button returnButton = new Button("Return");
        rootPane.getChildren().add(returnButton);
        returnButton.setLayoutX(0);
        returnButton.setLayoutY(0);
        returnButton.setPrefWidth(SLIDER_WIDTH);
        returnButton.setOnAction(event -> {
            try {
                super.switchScene("/com/blueprinthell/view/MainMenu.fxml", event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
