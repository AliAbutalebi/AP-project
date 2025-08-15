package com.blueprinthell.map.adapters;

import com.blueprinthell.model.Wire;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
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

        out.name("packetOnWireId");
        if (wire.getPacketOnWire() != null) out.value(wire.getPacketOnWire().getId());
        else out.nullValue();

        out.name("controlPoints").beginArray();
        for (Point2D point : wire.getControlPoints()) {
            point2DAdapter.write(out, point);
        }
        out.endArray();
        out.name("passedLargePackets").value(wire.getPassedLargePackets());
        out.endObject();
    }

    @Override
    public Wire read(JsonReader in) throws IOException {
        Wire wire = new Wire();

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "id":
                    wire.setId(in.nextInt());
                    break;
                case "sourcePortId":
                    wire.setSourcePortId(in.nextInt());
                    break;
                case "destinationPortId":
                    wire.setDestinationPortId(in.nextInt());
                    break;
                case "packetOnWireId": {
                    if (in.peek() != JsonToken.NULL) wire.setPacketOnWireId(in.nextInt());
                    else in.nextNull();
                    break;
                }
                case "controlPoints": {
                    in.beginArray();
                    while (in.hasNext()) {
                        wire.getControlPoints().add(point2DAdapter.read(in));
                    }
                    in.endArray();
                    break;
                }
                case "passedLargePackets":
                    wire.setPassedLargePackets(in.nextInt());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return wire;
    }
}
