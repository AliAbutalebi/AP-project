package com.blueprinthell.map;

import com.blueprinthell.model.*;
import com.google.gson.*;
import javafx.geometry.Point2D;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GameMapDeserializer implements JsonDeserializer<GameMap> {

    @Override
    public GameMap deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject rootObject = jsonElement.getAsJsonObject();
        GameMap gameMap = new GameMap();

        double maxWireLength = rootObject.get("maxWireLength").getAsDouble();
        gameMap.setMaxWireLength(maxWireLength);

        ArrayList<SystemNode> systemNodes = new ArrayList<>();
        JsonArray nodesArray = rootObject.getAsJsonArray("systemNodes");
        for (JsonElement nodeElement : nodesArray) {
            JsonObject nodeObject = nodeElement.getAsJsonObject();
            String nodeType = nodeObject.get("type").getAsString();
            SystemNode node;

            if (nodeType.equals("ReferenceSystemNode")) {
                node = new ReferenceSystemNode();
            } else {
                node = new SystemNode() {};
            }

            int id = nodeObject.get("id").getAsInt();
            node.setId(id);

            JsonObject locationObj = nodeObject.getAsJsonObject("location");
            double width = locationObj.get("X").getAsDouble();
            double height = locationObj.get("Y").getAsDouble();
            node.setLocation(new Point2D(width, height));

            node.setActive(nodeObject.get("isActive").getAsBoolean());

            ArrayList<Port> inputPorts = new ArrayList<>();
            JsonArray inputPortsArray = nodeObject.getAsJsonArray("inputPorts");
            for (JsonElement portElement : inputPortsArray) {
                inputPorts.add(deserializePort(portElement.getAsJsonObject()));
            }
            node.setInputPorts(inputPorts);

            ArrayList<Port> outputPorts = new ArrayList<>();
            JsonArray outputPortsArray = nodeObject.getAsJsonArray("outputPorts");
            for (JsonElement portElement : outputPortsArray) {
                outputPorts.add(deserializePort(portElement.getAsJsonObject()));
            }
            node.setOutputPorts(outputPorts);

            systemNodes.add(node);
        }

        gameMap.setSystemNodes(systemNodes);
        return gameMap;
    }

    private Port deserializePort(JsonObject portObject) {
        String portType = portObject.get("type").getAsString();
        boolean isInput = portObject.get("isInput").getAsBoolean();
        int parentSystemId = portObject.get("parentSystemId").getAsInt();

        Port port;
        if (portType.equals("SquarePort")) {
            port = new SquarePort(isInput, parentSystemId);
        } else if (portType.equals("TrianglePort")) {
            port = new TrianglePort(isInput, parentSystemId);
        } else {
            throw new JsonParseException("Unknown port type: " + portType);
        }

        port.setId(portObject.get("id").getAsInt());
        port.setOccupied(portObject.get("occupied").getAsBoolean());
        return port;
    }
}
