package com.blueprinthell.map.adapters;

import com.blueprinthell.model.Port;
import com.blueprinthell.model.SystemNode;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import

public class SystemNodeAdapter extends TypeAdapter<SystemNode> {

    Point2DAdapter point2DAdapter = new Point2DAdapter();
    PortAdapter portAdapter = new PortAdapter();

    @Override
    public void write(JsonWriter out, SystemNode node) throws IOException {
        out.beginObject();
        out.name("systemType").value(node.getSystemType().name());
        out.name("id").value(node.getId());
        out.name("location");
        point2DAdapter.write(out, node.getLocation());

        out.name("inputPorts").beginArray();
        for (Port port : node.getInputPorts()) {
            portAdapter.write(out, port);
        }
        out.endArray();

        out.name("outputPorts").beginArray();
        for (Port port : node.getOutputPorts()) {
            portAdapter.write(out, port);
        }
        out.endArray();

        out.name("isActive").value(node.isActive());
        out.endObject();
    }

    @Override
    public SystemNode read(JsonReader in) throws IOException {
        return null;
    }
}
