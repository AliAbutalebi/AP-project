package com.blueprinthell.map.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import javafx.geometry.Point2D;

import java.io.IOException;

public class Point2DAdapter extends TypeAdapter<Point2D> {
    @Override
    public void write(JsonWriter out, Point2D value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.beginObject();
        out.name("X").value(value.getX());
        out.name("Y").value(value.getY());
        out.endObject();
    }

    @Override
    public Point2D read(JsonReader in) throws IOException {
        double x = 0, y = 0;
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "X" -> x = in.nextDouble();
                case "Y" -> y = in.nextDouble();
            }
        }
        in.endObject();
        return new Point2D(x, y);
    }
}
