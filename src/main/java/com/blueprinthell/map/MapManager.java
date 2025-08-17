package com.blueprinthell.map;

import com.blueprinthell.model.GameMap;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MapManager {
    private static final MapManager instance = new MapManager();
    private static final LoadManager loadManager = LoadManager.getInstance();
    private static final SaveManager saveManager = SaveManager.getInstance();
    private static File[] vanillaFiles;
    private static File[] autoSaveFiles;

    private final HashMap<Integer, File[]> maps = new HashMap<>();
    private int currentLevel = 1;
    private boolean autoSave = false;

    private MapManager() {
        vanillaFiles = new File("./src/main/resources/com/blueprinthell/map").listFiles();
        autoSaveFiles = new File("./src/main/resources/com/blueprinthell/save/autosave").listFiles();
        listMaps();
    }

    public static MapManager getInstance() {
        return instance;
    }

    private void listMaps() {
        if (vanillaFiles.length == 0) return;

        for (int i = 0; i < vanillaFiles.length; i++) {
            File[] mapFiles = new File[]{vanillaFiles[i], autoSaveFiles[i]};
            maps.put(Integer.parseInt(vanillaFiles[i].getName().replace("map", "")
                    .replace(".json", "")), mapFiles);
        }
    }

    public void setLevel(int level) {
        currentLevel = level;
    }

    public int getLevel() {
        return currentLevel;
    }

    public GameMap getVanillaMap(int level) {
        return loadManager.load(maps.get(level)[0]);
    }

    public GameMap getAutoSaveMap(int level) {
        return loadManager.load(maps.get(level)[1]);
    }

    public void startAutoSave(GameMap map) {
        saveManager.setCurrentMap(map);
        saveManager.setAutoSaveFile(maps.get(map.getLevel())[1]);
        saveManager.start();
    }

    public void stopAutoSave() {
        saveManager.interrupt();
    }

    public void save(GameMap map) {
        saveManager.setCurrentMap(map);
        saveManager.setAutoSaveFile(maps.get(map.getLevel())[1]);
        saveManager.save();
    }

    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    public boolean isAutoSave() {
        return autoSave;
    }

    public GameMap load() {
        if (autoSave) {
            return getAutoSaveMap(currentLevel);
        } else {
            return getVanillaMap(currentLevel);
        }
    }

    public int getMapCount() {
        return maps.size();
    }
}
