package com.blueprinthell.map.adapters;

import com.blueprinthell.model.Packet;
import com.blueprinthell.model.ShapeType;
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

        out.name("parentLargePacketId");
        if (packet.getShapeType() == ShapeType.BIT_PACKET) out.value(packet.getParentLargePacket().getId());
        else out.nullValue();

        out.name("received").value(packet.isReceived());
        out.endObject();
    }

    @Override
    public Packet read(JsonReader in) throws IOException {
        Packet packet = new Packet();

        in.beginObject();

        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "id":
                    packet.setId(in.nextInt());
                    break;
                case "shapeType":
                    packet.setShapeType(ShapeType.valueOf(in.nextString()));
                    break;
                case "alive":
                    packet.setAlive(in.nextBoolean());
                    break;
                case "protected":
                    packet.setProtected(in.nextBoolean());
                    break;
                case "protectorId": {
                    if (packet.isProtected()) packet.setProtectorId(in.nextInt());
                    else in.nextNull();
                    break;
                }
                case "trojan":
                    packet.setTrojan(in.nextBoolean());
                    break;
                case "noise":
                    packet.setNoise(in.nextInt());
                    break;
                case "currentSpeed":
                    packet.setCurrentSpeed(in.nextDouble());
                    break;
                case "onWire":
                    packet.setOnWire(in.nextBoolean());
                    break;
                    case "currentWireId": {
                        if (packet.isOnWire()) packet.setCurrentWireId(in.nextInt());
                        else in.nextNull();
                        break;
                    }
                case "progressOnWire":
                    packet.setProgressOnWire(in.nextInt());
                    break;
                case "deviation":
                    packet.setDeviation(point2DAdapter.read(in));
                    break;
                case "currentSystemNodeId": {
                    if (!packet.isOnWire()) packet.setCurrentSystemNodeId(in.nextInt());
                    else in.nextNull();
                    break;
                }
                case "parentLargePacketId": {
                    if (packet.getShapeType() == ShapeType.BIT_PACKET) packet.setParentLargePacketId(in.nextInt());
                    else in.nextNull();
                    break;
                }
                case "received":
                    packet.setReceived(in.nextBoolean());
                    break;
                default: in.skipValue();
                break;
            }
        }
        in.endObject();
        return packet;
    }
}
