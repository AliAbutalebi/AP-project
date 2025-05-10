package com.blueprinthell.audio;

import javafx.scene.media.AudioClip;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SoundEffectManager {
    private static SoundEffectManager instance = new SoundEffectManager();
    Map<String, AudioClip> soundEffects = new HashMap<>();

    private SoundEffectManager() {
        loadSoundEffects();
    }

    private void loadSoundEffects() {
        File[] soundEffectFiles = new File("com/blueprinthell/audio/sound-effects").listFiles();
        for (File soundEffectFile : soundEffectFiles) {
            soundEffects.put(soundEffectFile.getName(), new AudioClip(soundEffectFile.toURI().toString()));
        }
    }


    public static SoundEffectManager getInstance() {
        if (instance == null) {
            instance = new SoundEffectManager();
        }
        return instance;
    }
}
