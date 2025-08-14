package com.blueprinthell.map;

import com.blueprinthell.model.*;
import com.google.gson.*;
import javafx.geometry.Point2D;

import java.lang.reflect.Type;
import java.util.*;

public class GameMapDeserializer implements JsonDeserializer<GameMap> {

    private static final ScreenDimensions screenDimensions = ScreenDimensions.getInstance();

    @Override
    public GameMap deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject rootObject = jsonElement.getAsJsonObject();
        GameMap gameMap = new GameMap();

        double maxWireLength = rootObject.get("maxWireLength").getAsDouble();
        gameMap.setMaxWireLength(maxWireLength);

        ArrayList<SystemNode> systemNodes = new ArrayList<>();
        Map<Integer, SystemNode> idToSystemNode = new HashMap<>();
        Map<Integer, Port> portIdMap = new HashMap<>();

        JsonArray nodesArray = rootObject.getAsJsonArray("systemNodes");
        for (JsonElement nodeElement : nodesArray) {
            JsonObject nodeObject = nodeElement.getAsJsonObject();

            SystemNode node = new SystemNode();

            int id = nodeObject.get("id").getAsInt();
            node.setId(id);

            JsonObject locationObj = nodeObject.getAsJsonObject("location");
            Point2D location = new Point2D(locationObj.get("X").getAsDouble() / 1920 * screenDimensions.getWidth(), locationObj.get("Y").getAsDouble() / 1080 * (screenDimensions.getHeight() - screenDimensions.getHeight() / 8) + screenDimensions.getHeight() / 8);
            node.setLocation(location);

            SystemType type = SystemType.valueOf(nodeObject.get("systemType").getAsString());
            node.setSystemType(type);

            ArrayList<Port> inputPorts = deserializePorts(nodeObject.getAsJsonArray("inputPorts"), portIdMap);
            ArrayList<Port> outputPorts = deserializePorts(nodeObject.getAsJsonArray("outputPorts"), portIdMap);
            for (Port inputPort : inputPorts) {
                inputPort.setParentSystemNode(node);
            }
            for (Port outputPort : outputPorts) {
                outputPort.setParentSystemNode(node);
            }
            node.setInputPorts(inputPorts);
            node.setOutputPorts(outputPorts);

            systemNodes.add(node);
            idToSystemNode.put(id, node);
        }

        gameMap.setSystemNodes(systemNodes);

        if (rootObject.has("packets")) {
            JsonArray packetArray = rootObject.getAsJsonArray("packets");
            ArrayList<Packet> activePackets = new ArrayList<>();

            for (JsonElement packetElement : packetArray) {
                JsonObject p = packetElement.getAsJsonObject();

                ShapeType shape = ShapeType.valueOf(p.get("shapeType").getAsString());
                Packet packet = new Packet(shape);
                packet.setId(p.get("id").getAsInt());
                packet.setAlive(p.get("alive").getAsBoolean());
                packet.setProtected(p.get("protected").getAsBoolean());
                packet.setNoise(p.get("noise").getAsInt());
                packet.setCurrentSpeed(p.get("currentSpeed").getAsDouble());
                packet.setProgressOnWire(p.get("progressOnWire").getAsDouble());

                boolean onWire = p.get("onWire").getAsBoolean();
                packet.setOnWire(onWire);

                if (!onWire) {
                    packet.setCurrentWire(null);
                }

                int systemNodeId = p.get("currentSystemNodeId").getAsInt();
                SystemNode node = idToSystemNode.get(systemNodeId);
                if (node != null) {
                    node.getPacketQueue().add(packet);
                    packet.setCurrentSystemNode(node);
                }

                activePackets.add(packet);
            }

            gameMap.setActivePackets(activePackets);
        }

        if (rootObject.has("wires")) {
            JsonArray wireArray = rootObject.getAsJsonArray("wires");
            ArrayList<Wire> wires = new ArrayList<>();

            for (JsonElement wireElement : wireArray) {
                JsonObject w = wireElement.getAsJsonObject();

                int wireId = w.get("id").getAsInt();
                int sourcePortId = w.get("sourcePortId").getAsInt();
                int destinationPortId = w.get("destinationPortId").getAsInt();

                JsonObject startObj = w.getAsJsonObject("start");
                JsonObject endObj = w.getAsJsonObject("end");

                Point2D start = new Point2D(startObj.get("X").getAsDouble(), startObj.get("Y").getAsDouble());
                Point2D end = new Point2D(endObj.get("X").getAsDouble(), endObj.get("Y").getAsDouble());

                Port sourcePort = portIdMap.get(sourcePortId);
                Port destinationPort = portIdMap.get(destinationPortId);

                if (sourcePort == null || destinationPort == null) {
                    throw new JsonParseException("Invalid port ID: " + wireId);
                }

                Wire wire = new Wire(start, end);
                // wire.setId(wireId);
                wire.setSourcePort(sourcePort);
                wire.setDestinationPort(destinationPort);

                sourcePort.setConnectedWire(wire);
                sourcePort.setOccupied(true);
                destinationPort.setConnectedWire(wire);
                destinationPort.setOccupied(true);

                wires.add(wire);
            }

            gameMap.setWires(wires);
        }

        return gameMap;
    }

    private ArrayList<Port> deserializePorts(JsonArray portArray, Map<Integer, Port> portIdMap) {
        ArrayList<Port> ports = new ArrayList<>();
        for (JsonElement portElement : portArray) {
            JsonObject obj = portElement.getAsJsonObject();
            boolean isInput = obj.get("isInput").getAsBoolean();
            int parentSystemId = obj.get("parentSystemNodeId").getAsInt();
            int id = obj.get("id").getAsInt();
            boolean occupied = obj.get("occupied").getAsBoolean();

            ShapeType shapeType = ShapeType.valueOf(obj.get("shapeType").getAsString());

            Port port = new Port(isInput, shapeType, parentSystemId);
            port.setId(id);
            port.setOccupied(occupied);
            portIdMap.put(id, port);
            ports.add(port);
        }
        return ports;
    }
}