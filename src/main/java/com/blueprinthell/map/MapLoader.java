package com.blueprinthell.map;

import com.google.gson.Gson;
import com.blueprinthell.model.GameMap;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Random;

public class MapLoader {
    private static MapLoader instance = new MapLoader();
    private static int selectedMap = 0;

    private MapLoader() {
    }

    public static MapLoader getInstance() {
        return instance;
    }

    private static File[] mapFiles = new File("./src/main/resources/com/blueprinthell/map").listFiles();

    public static GameMap loadMap(File mapFile) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GameMap.class, new GameMapDeserializer())
                .create();

        try (Reader reader = new FileReader(mapFile)) {
            GameMap map = gson.fromJson(reader, GameMap.class);
            map.setMapName(mapFile.getName().substring(0, mapFile.getName().lastIndexOf('.')));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static GameMap loadRandomMap() {
        Random rand = new Random();
        File randomMapFile = mapFiles[rand.nextInt(mapFiles.length)];
        return loadMap(randomMapFile);
    }

    public static GameMap loadLevelMap(int level) {
        File randomMapFile = mapFiles[level];
        return loadMap(randomMapFile);
    }

    public static void setSelectedMap(int selectedMap) {
        MapLoader.selectedMap = selectedMap;
    }

    public static int getSelectedMap() {
        return selectedMap;
    }

    public static File[] getMapFiles() {
        return mapFiles;
    }
}
