package com.blueprinthell.map.adapters;

import com.blueprinthell.model.Port;
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
        out.name("parentSystemNodeId").value(port.getParentSystemNode().getId());
        out.endObject();
    }

    @Override
    public Port read(JsonReader in) throws IOException {
        return null;
    }
}
