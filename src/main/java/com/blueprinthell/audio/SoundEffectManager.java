package com.blueprinthell.audio;

import javafx.scene.media.AudioClip;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SoundEffectManager {
    private static SoundEffectManager instance;
    Map<String, AudioClip> soundEffects = new HashMap<>();

    private SoundEffectManager() {
        loadSoundEffects();
    }

    private void loadSoundEffects() {
        File[] soundEffectFiles = new File("./src/main/resources/com/blueprinthell/audio/sound-effects").listFiles();
        for (File soundEffectFile : soundEffectFiles) {
            soundEffects.put(soundEffectFile.getName().substring(0, soundEffectFile.getName().length() - 4), new AudioClip(soundEffectFile.toURI().toString()));
        }
    }

    public void play(String soundName) {
        AudioClip clip = soundEffects.get(soundName);
        if (clip != null) {
            clip.play();
        }
    }

    public static SoundEffectManager getInstance() {
        if (instance == null) {
            instance = new SoundEffectManager();
        }
        return instance;
    }
}
