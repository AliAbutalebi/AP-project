package com.blueprinthell.map;

import com.blueprinthell.map.adapters.PacketAdapter;
import com.blueprinthell.map.adapters.SystemNodeAdapter;
import com.blueprinthell.map.adapters.WireAdapter;
import com.blueprinthell.model.GameMap;
import com.blueprinthell.model.Packet;
import com.blueprinthell.model.SystemNode;
import com.blueprinthell.model.Wire;
import com.google.gson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveManager extends Thread {
    private static final SaveManager instance = new SaveManager();
    private static File[] autoSaveFiles;
    private static File currentAutoSaveFile;
    private static GameMap currentMap;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(SystemNode.class, new SystemNodeAdapter())
            .registerTypeAdapter(Packet.class, new PacketAdapter())
            .registerTypeAdapter(Wire.class, new WireAdapter())
            .create();
    private static final Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
    private static boolean running = true;
    private static final int SLEEP_INTERVAL = 2;

    private SaveManager() {
        autoSaveFiles = new File("./src/main/resources/com/blueprinthell/save/autosave").listFiles();
    }

    public static SaveManager getInstance() {
        return instance;
    }

    @Override
    public void run() {
        while (running) {
            save();

            try {
                Thread.sleep(SLEEP_INTERVAL * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void save() {
        JsonObject save = new JsonObject();
        save.add("systemNodes", nodesToJson());
        save.add("packets", packetsToJson());
        save.add("wires", wiresToJson());
        save.add("maxWireLength", maxWireLengthToJson());

        try (FileWriter writer = new FileWriter(autoSaveFiles[0])) {
            prettyGson.toJson(save, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentMap(GameMap map) {
        currentMap = map;
    }

    public JsonArray nodesToJson() {
        JsonArray systemNodes = new JsonArray();
        for (SystemNode node : currentMap.getSystemNodes()) {
            systemNodes.add(gson.toJsonTree(node, SystemNode.class));
        }
        return systemNodes;
    }

    public JsonArray packetsToJson() {
        JsonArray packets = new JsonArray();
        for (Packet packet : currentMap.getActivePackets()) {
            packets.add(gson.toJsonTree(packet, Packet.class));
        }
        return packets;
    }

    public JsonArray wiresToJson() {
        JsonArray wires = new JsonArray();
        for (Wire wire : currentMap.getWires()) {
            wires.add(gson.toJsonTree(wire, Wire.class));
        }
        return wires;
    }

    public JsonPrimitive maxWireLengthToJson() {
        return new JsonPrimitive(currentMap.getMaxWireLength());
    }

}
