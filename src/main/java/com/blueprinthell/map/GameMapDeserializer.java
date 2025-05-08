package com.blueprinthell.map;

import com.blueprinthell.model.*;
import com.google.gson.*;
import javafx.geometry.Point2D;

import java.lang.reflect.Type;
import java.util.*;

public class GameMapDeserializer implements JsonDeserializer<GameMap> {

    @Override
    public GameMap deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject rootObject = jsonElement.getAsJsonObject();
        GameMap gameMap = new GameMap();

        double maxWireLength = rootObject.get("maxWireLength").getAsDouble();
        gameMap.setMaxWireLength(maxWireLength);

        ArrayList<SystemNode> systemNodes = new ArrayList<>();
        Map<Integer, ReferenceSystemNode> referenceNodesById = new HashMap<>();
        Map<Integer, Port> portIdMap = new HashMap<>();

        JsonArray nodesArray = rootObject.getAsJsonArray("systemNodes");
        for (JsonElement nodeElement : nodesArray) {
            JsonObject nodeObject = nodeElement.getAsJsonObject();
            String nodeType = nodeObject.get("type").getAsString();

            SystemNode node;
            if (nodeType.equals("ReferenceSystemNode")) {
                node = new ReferenceSystemNode();
                referenceNodesById.put(nodeObject.get("id").getAsInt(), (ReferenceSystemNode) node);
            } else {
                node = new SystemNode();
            }

            int id = nodeObject.get("id").getAsInt();
            node.setId(id);

            JsonObject locationObj = nodeObject.getAsJsonObject("location");
            Point2D location = new Point2D(locationObj.get("X").getAsDouble(), locationObj.get("Y").getAsDouble());
            node.setLocation(location);

            node.setActive(nodeObject.get("isActive").getAsBoolean());

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
                packet.setNoise(p.get("noise").getAsDouble());
                packet.setCurrentSpeed(p.get("currentSpeed").getAsDouble());
                packet.setDistanceOnWire(p.get("progressOnWire").getAsDouble());

                boolean onWire = p.get("onWire").getAsBoolean();
                packet.setOnWire(onWire);

                if (!onWire) {
                    packet.setCurrentWire(null);
                }

                int systemNodeId = p.get("locationSystemNodeId").getAsInt();
                ReferenceSystemNode node = referenceNodesById.get(systemNodeId);
                if (node != null) {
                    node.getPacketQueue().add(packet);
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
                wire.setId(wireId);
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
            String type = obj.get("type").getAsString();
            boolean isInput = obj.get("isInput").getAsBoolean();
            int parentSystemId = obj.get("parentSystemId").getAsInt();
            int id = obj.get("id").getAsInt();
            boolean occupied = obj.get("occupied").getAsBoolean();

            ShapeType shapeType = switch (type) {
                case "SquarePort" -> ShapeType.SQUARE;
                case "TrianglePort" -> ShapeType.TRIANGLE;
                default -> throw new JsonParseException("Unknown port type: " + type);
            };

            Port port = new Port(isInput, shapeType, parentSystemId);
            port.setId(id);
            port.setOccupied(occupied);
            portIdMap.put(id, port);
            ports.add(port);
        }
        return ports;
    }
}