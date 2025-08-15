package com.blueprinthell.map.adapters;

import com.blueprinthell.model.Port;
import com.blueprinthell.model.ShapeType;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class PortAdapter extends TypeAdapter<Port> {

    @Override
    public void write(JsonWriter out, Port port) throws IOException {
        out.beginObject();
        out.name("shapeType").value(port.getShapeType().name());
        out.name("id").value(port.getId());
        out.name("isInput").value(port.isInput());
        out.name("occupied").value(port.isOccupied());
        out.name("connectedWireId");
        if (port.isOccupied()) out.value(port.getConnectedWire().getId());
        else out.nullValue();
        out.name("parentSystemNodeId").value(port.getParentSystemNode().getId());
        out.endObject();
    }

    @Override
    public Port read(JsonReader in) throws IOException {
        Port port = new Port();

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "shapeType":
                    port.setShapeType(ShapeType.valueOf(in.nextString()));
                    break;
                case "id":
                    port.setId(in.nextInt());
                    break;
                case "isInput":
                    port.setInput(in.nextBoolean());
                    break;
                case "occupied":
                    port.setOccupied(in.nextBoolean());
                    break;
                case "connectedWireId": {
                    if (port.isOccupied()) port.setConnectedWireId(in.nextInt());
                    else in.nextNull();
                    break;
                }
                case "parentSystemNodeId":
                    port.setParentSystemNodeId(in.nextInt());
                    break;
                    default:
                        in.skipValue();
                        break;
            }
        }
        in.endObject();
        return port;
    }
}
