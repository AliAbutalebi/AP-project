package com.blueprinthell.map.adapters;

import com.blueprinthell.model.Wire;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import javafx.geometry.Point2D;

import java.io.IOException;

public class WireAdapter extends TypeAdapter<Wire> {
    Point2DAdapter point2DAdapter = new Point2DAdapter();

    @Override
    public void write(JsonWriter out, Wire wire) throws IOException {
        out.beginObject();
        out.name("id").value(wire.getId());
        out.name("sourcePortId").value(wire.getSourcePort().getId());
        out.name("destinationPortId").value(wire.getDestinationPort().getId());
        out.name("packetOnWireId").value(wire.getPacketOnWire().getId());
        out.name("controlPoints").beginArray();
        for (Point2D point : wire.getControlPoints()) {
            point2DAdapter.write(out, point);
        }
        out.endArray();
        out.name("passedLargePackets").value(wire.getPassedLargePackets());
    }

    @Override
    public Wire read(JsonReader in) throws IOException {
        return null;
    }
}
