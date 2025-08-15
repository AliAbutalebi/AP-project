package com.blueprinthell.map;

import com.blueprinthell.map.adapters.PacketAdapter;
import com.blueprinthell.map.adapters.SystemNodeAdapter;
import com.blueprinthell.map.adapters.WireAdapter;
import com.blueprinthell.model.*;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class LoadManager {
    private static final LoadManager instance = new LoadManager();
    private static GameMap currentMap;
    private Gson gson = new GsonBuilder().registerTypeAdapter(SystemNode.class, new SystemNodeAdapter()).registerTypeAdapter(Packet.class, new PacketAdapter()).registerTypeAdapter(Wire.class, new WireAdapter()).serializeNulls().create();

    private LoadManager() {
    }

    public static LoadManager getInstance() {
        return instance;
    }

    public GameMap load() {
        JsonObject jsonObject = new JsonObject();
        try (Reader reader = new FileReader("packets.json")) {
            JsonElement root = JsonParser.parseReader(reader);
            jsonObject = root.getAsJsonObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        currentMap = new GameMap();
        currentMap.setSystemNodes(jsonToNodes(jsonObject.getAsJsonArray("systemNodes")));
        currentMap.setPackets(jsonToPackets(jsonObject.getAsJsonArray("packets")));
        currentMap.setWires(jsonToWires(jsonObject.getAsJsonArray("wires")));
        currentMap.setMaxWireLength(jsonToMaxWireLength(jsonObject.getAsJsonPrimitive("maxWireLength")));
        sync();
        return currentMap;
    }

    private ArrayList<SystemNode> jsonToNodes(JsonArray jsonArray) {
        ArrayList<SystemNode> systemNodes = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            systemNodes.add(gson.fromJson(jsonElement, SystemNode.class));
        }
        return systemNodes;
    }

    private ArrayList<Packet> jsonToPackets(JsonArray jsonArray) {
        ArrayList<Packet> packets = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            packets.add(gson.fromJson(jsonElement, Packet.class));
        }
        return packets;
    }

    private ArrayList<Wire> jsonToWires(JsonArray jsonArray) {
        ArrayList<Wire> wires = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            wires.add(gson.fromJson(jsonElement, Wire.class));
        }
        return wires;
    }

    private double jsonToMaxWireLength(JsonPrimitive jsonPrimitive) {
        return jsonPrimitive.getAsDouble();
    }

    private void sync() {
        syncNodesAndWires();
        syncNodesAndPackets();
        syncPacketsAndWires();
    }

    private void syncNodesAndPackets() {
        for (Packet packet : currentMap.getPackets()) {
            for (SystemNode systemNode : currentMap.getSystemNodes()) {
                if (packet.getProtectorId() == systemNode.getId()) {
                    packet.setProtector(systemNode);
                    packet.setProtected(true);
                }
                if (packet.getCurrentSystemNodeId() == systemNode.getId()) {
                    packet.setCurrentSystemNode(systemNode);
                    systemNode.getPacketQueue().add(packet);
                    packet.setOnWire(false);
                }
            }
        }
    }

    private void syncNodesAndWires() {
        for (Wire wire : currentMap.getWires()) {
            for (SystemNode node : currentMap.getSystemNodes()) {
                for (Port port : node.getInputPorts()) {
                    if (wire.getDestinationPortId() == port.getId()) {
                        wire.setDestinationPort(port);
                        port.setConnectedWire(wire);
                        port.setOccupied(true);
                        break;
                    }
                }
                for (Port port : node.getOutputPorts()) {
                    if (wire.getSourcePortId() == port.getId()) {
                        wire.setSourcePort(port);
                        port.setConnectedWire(wire);
                        port.setOccupied(true);
                        break;
                    }
                }
            }
        }
    }

    private void syncPacketsAndWires() {
        for (Wire wire : currentMap.getWires()) {
            for (Packet packet : currentMap.getPackets()) {
                if (wire.getPacketOnWireId() == packet.getId()) {
                    wire.setPacketOnWire(packet);
                    packet.setCurrentWire(wire);
                    packet.setOnWire(true);
                    break;
                }
            }
        }
    }
}
