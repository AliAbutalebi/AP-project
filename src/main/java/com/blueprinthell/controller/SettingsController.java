package com.blueprinthell.controller;

import com.blueprinthell.audio.MusicPlayer;
import com.blueprinthell.audio.SoundEffectManager;
import com.blueprinthell.model.ScenePath;
import com.blueprinthell.model.ScreenDimensions;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SettingsController extends BaseController {


    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox rootPane;

    @FXML
    private Label musicTitle;
    @FXML
    private Button musicSwitchButton;
    @FXML
    private Slider musicSlider;
    @FXML
    private Slider sfxSlider;


    private static final ScreenDimensions screenDimensions = ScreenDimensions.getInstance();

    private static final double SLIDER_WIDTH = screenDimensions.getWidth() / 5;
    private static final double SPACING = 10;

    MusicPlayer musicPlayer = MusicPlayer.getInstance();
    SoundEffectManager soundEffectManager = SoundEffectManager.getInstance();

    @FXML
    public void initialize() {
        rootPane.setPrefHeight(screenDimensions.getHeight());
        setupMusicSwitchPane();
        setupMusicVolumePane();
        setupSfxVolumePane();
        setupReturnButton();
        musicPlayer.play();
    }

    private void setupMusicSwitchPane() {
        updateMusicTitle();

        musicSwitchButton.setOnAction(event -> {
            musicPlayer.switchMusic();
            updateMusicTitle();
        });
    }

    private void setupMusicVolumePane() {
        musicSlider.setPrefWidth(SLIDER_WIDTH);
        musicSlider.setMaxWidth(SLIDER_WIDTH);
        musicSlider.setMin(0);
        musicSlider.setMax(100);
        musicSlider.setValue(20);
        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            musicPlayer.setVolume(newValue.doubleValue() / 100);
        });
    }
    private void setupSfxVolumePane() {
        sfxSlider.setPrefWidth(SLIDER_WIDTH);
        sfxSlider.setMaxWidth(SLIDER_WIDTH);
        sfxSlider.setMin(0);
        sfxSlider.setMax(100);
        sfxSlider.setValue(20);
        sfxSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            soundEffectManager.setVolume(newValue.doubleValue() / 100);
        });
    }

    private void setupReturnButton () {
        Button returnButton = new Button("RETURN TO MENU");
        rootPane.getChildren().add(returnButton);
        returnButton.setLayoutX(0);
        returnButton.setLayoutY(0);
        returnButton.setPrefWidth(SLIDER_WIDTH);
        returnButton.setOnAction(event -> {
            try {
                super.switchScene(ScenePath.MAIN_MENU.getResourceURL(), event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void updateMusicTitle() {
        try {
            String newMusicTitle = musicPlayer.getMusicTitle();
            musicTitle.setText(newMusicTitle);
        }
        catch (InvalidDataException | UnsupportedTagException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
