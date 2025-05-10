package com.blueprinthell.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Random;

public class MusicPlayer {
    private static MusicPlayer instance;

    private static MediaPlayer mediaPlayer;
    private File[] soundtrack = new File("./src/main/resources/com/blueprinthell/audio/soundtrack").listFiles();

    private MusicPlayer() {
        mediaPlayer = new MediaPlayer(selectMusic());
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(false);
    }

    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }


    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }

    public Media selectMusic() {
        Random rand = new Random();
        int index = rand.nextInt(soundtrack.length);
        return new Media(soundtrack[index].toURI().toString());
    }
}
