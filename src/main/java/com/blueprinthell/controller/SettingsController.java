package com.blueprinthell.controller;

import com.blueprinthell.audio.MusicPlayer;
import com.blueprinthell.audio.SoundEffectManager;
import com.blueprinthell.model.ScreenDimensions;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class SettingsController extends BaseController {


    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox rootPane;

    @FXML
    private HBox musicVolumePane;
    @FXML
    private Slider musicSlider;
    @FXML
    private HBox sfxVolumePane;
    @FXML
    private Slider sfxSlider;


    private static final ScreenDimensions screenDimensions = ScreenDimensions.getInstance();

    private static final double SLIDER_WIDTH = screenDimensions.getWidth() / 5;
    private static final double SPACING = 10;

    MusicPlayer musicPlayer = MusicPlayer.getInstance();
    SoundEffectManager soundEffectManager = SoundEffectManager.getInstance();

    @FXML
    public void initialize() {
        scrollPane.setStyle("-fx-background-color: #333333");
        setupMusicVolumePane();
        setupSfxVolumePane();
        setupReturnButton();
        musicPlayer.play();
    }

    private void setupMusicVolumePane() {


        musicSlider.setPrefWidth(SLIDER_WIDTH);
        musicSlider.setMaxWidth(SLIDER_WIDTH);
        musicSlider.setMin(0);
        musicSlider.setMax(100);
        musicSlider.setValue(10);
        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
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
