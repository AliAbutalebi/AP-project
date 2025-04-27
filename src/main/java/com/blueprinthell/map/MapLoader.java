package com.blueprinthell.map;

import com.google.gson.Gson;
import com.blueprinthell.model.GameMap;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Random;

public class MapLoader {
    private static File[] mapFiles = new File("./src/main/resources/com/blueprinthell/map").listFiles();
    
    public static GameMap loadMap(File mapFile) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GameMap.class, new GameMapDeserializer())
                .create();

        try (Reader reader = new FileReader(mapFile)) {
            return gson.fromJson(reader, GameMap.class);
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
}
