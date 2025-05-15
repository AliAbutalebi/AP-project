package com.blueprinthell.audio;

import com.mpatric.mp3agic.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Random;

public class MusicPlayer {
    private static MusicPlayer instance;

    private static MediaPlayer mediaPlayer;
    private static File[] soundtrack = new File("./src/main/resources/com/blueprinthell/audio/soundtrack").listFiles();

    private MusicPlayer() {
        mediaPlayer = new MediaPlayer(selectMusic());
        setupMediaPlayer();
    }

    private void setupMediaPlayer() {
        mediaPlayer.setCycleCount(1);
        mediaPlayer.setAutoPlay(false);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setOnEndOfMedia(this::switchMusic);
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

    private Media selectMusic() {
        Random rand = new Random();
        int index = rand.nextInt(soundtrack.length);
        return new Media(soundtrack[index].toURI().toString());
    }

    public void switchMusic() {
        while (true) {
            Media selectedMusic = selectMusic();
            if (!selectedMusic.equals(mediaPlayer.getMedia())) {
                stop();
                mediaPlayer.dispose();
                mediaPlayer = new MediaPlayer(selectedMusic);
                setupMediaPlayer();
                play();
                break;
            }
        }
    }

    public String getMusicTitle() throws InvalidDataException, UnsupportedTagException, IOException {
        Mp3File currentMP3 = new Mp3File(new File(URI.create(mediaPlayer.getMedia().getSource())));
        ID3v2 currentTag = currentMP3.getId3v2Tag();
        return currentTag.getTitle() + " - " + currentTag.getArtist();
    }
}
