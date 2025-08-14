package com.blueprinthell.map.adapters;

import com.blueprinthell.model.Packet;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class PacketAdapter extends TypeAdapter<Packet> {
    private Point2DAdapter point2DAdapter = new Point2DAdapter();

    @Override
    public void write(JsonWriter out, Packet packet) throws IOException {
        out.beginObject();
        out.name("id").value(packet.getId());
        out.name("shapeType").value(packet.getShapeType().name());
        out.name("alive").value(packet.isAlive());
        out.name("protected").value(packet.isProtected());

        out.name("protectorId");
        if (packet.isProtected()) out.value(packet.getProtector().getId());
        else out.nullValue();

        out.name("trojan").value(packet.isTrojan());
        out.name("noise").value(packet.getNoise());
        out.name("currentSpeed").value(packet.getCurrentSpeed());
        out.name("onWire").value(packet.isOnWire());
        out.name("progressOnWire").value(packet.getProgressOnWire());

        out.name("currentWireId");
        if (packet.isOnWire()) out.value(packet.getCurrentWire().getId());
        else out.nullValue();

        out.name("deviation");
        point2DAdapter.write(out, packet.getDeviation());

        out.name("currentSystemNodeId");
        if (!packet.isOnWire()) out.value(packet.getCurrentSystemNode().getId());
        else out.nullValue();

        out.name("received").value(packet.isReceived());
        out.endObject();
    }

    @Override
    public Packet read(JsonReader in) throws IOException {
        return null;
    }
}
