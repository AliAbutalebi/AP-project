package com.blueprinthell.map.adapters;

import com.blueprinthell.model.Port;
import com.blueprinthell.model.SystemNode;
import com.blueprinthell.model.SystemType;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

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
        SystemNode systemNode = new SystemNode();

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "systemType":
                    systemNode.setSystemType(SystemType.valueOf(in.nextString()));
                    break;
                case "id":
                    systemNode.setId(in.nextInt());
                    break;
                case "location":
                    systemNode.setLocation(point2DAdapter.read(in));
                    break;
                case "inputPorts": {
                    in.beginArray();
                    while (in.hasNext()) {
                        systemNode.getInputPorts().add(portAdapter.read(in));
                    }
                    in.endArray();
                    break;
                }
                case "outputPorts": {
                    in.beginArray();
                    while (in.hasNext()) {
                        systemNode.getOutputPorts().add(portAdapter.read(in));
                    }
                    in.endArray();
                    break;
                }
                case "isActive":
                    systemNode.setActive(in.nextBoolean());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return systemNode;
    }
}
