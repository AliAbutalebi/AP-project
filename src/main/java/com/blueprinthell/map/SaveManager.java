package com.blueprinthell.map;

import com.blueprinthell.map.adapters.Point2DAdapter;
import com.blueprinthell.model.GameMap;
import com.blueprinthell.model.Packet;
import com.blueprinthell.model.SystemNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.geometry.Point2D;

import java.io.File;
import java.util.ArrayList;

public class SaveManager extends Thread {
    private static final SaveManager instance = new SaveManager();
    private static File[] autoSaveFiles;
    private static File currentAutoSaveFile;
    private static GameMap currentMap;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private SaveManager() {
        autoSaveFiles = new File("./src/main/resources/com/blueprinthell/save/autosave").listFiles();
    }

    public static SaveManager getInstance() {
        return instance;
    }

    public void setCurrentMap(GameMap map) {
        currentMap = map;
    }

    public String nodesToJson() {

    }

    private String nodeToJson() {

    }

    private String portToJson() {}

    private String packetToJson(Packet packet) {
        String json = gson.toJson(packet);
    }


}
