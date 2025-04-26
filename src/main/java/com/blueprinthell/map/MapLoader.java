package com.blueprinthell.map;

import com.google.gson.Gson;
import com.blueprinthell.model.GameMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Random;

public class MapLoader {
    private static File[] mapFiles = new File("./src/main/resources/map").listFiles();
    public static GameMap loadMap(File mapFile) {
        Gson gson = new Gson();
        try {
            InputStream inputStream = new FileInputStream(mapFile);
            InputStreamReader reader = new InputStreamReader(inputStream);
            return gson.fromJson(reader, GameMap.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static GameMap loadRandomMap(String mapFile) {
        Random rand = new Random();
        File randomMapFile = mapFiles[rand.nextInt(mapFiles.length)];
        return loadMap(randomMapFile);
    }

    public static GameMap loadLevelMap(int level) {
        File randomMapFile = mapFiles[level];
        return loadMap(randomMapFile);
    }
}
