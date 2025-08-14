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
        out.name("protectorId").value(packet.getProtector().getId());
        out.name("trojan").value(packet.isTrojan());
        out.name("noise").value(packet.getNoise());
        out.name("currentSpeed").value(packet.getCurrentSpeed());
        out.name("onWire").value(packet.isOnWire());
        out.name("progressOnWire").value(packet.getProgressOnWire());
        out.name("currentWireId").value(packet.getCurrentWire().getId());
        out.name("deviation");
        point2DAdapter.write(out, packet.getDeviation());
        out.name("currentSystemNodeId").value(packet.getCurrentSystemNode().getId());
        out.name("received").value(packet.isReceived());


    }

    @Override
    public Packet read(JsonReader in) throws IOException {
        return null;
    }
}
