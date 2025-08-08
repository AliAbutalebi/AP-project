package com.blueprinthell.polygonization;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Polygonizer {
    private static final Polygonizer instance = new Polygonizer();

    private static final double TRESHOLD = 0.1;

    public static Polygonizer getInstance() {
        return instance;
    }

    private Polygonizer() {

    }

    public Polygon polygonize(Image image) {
        Polygon polygon = new Polygon();
        ArrayList<double[]> hullPoints = convexHull(extractImagePoints(image));
        for (double[] p : hullPoints) {
            polygon.getPoints().addAll(p[0], p[1]);
        }
        return polygon;
    }

    private ArrayList<double[]> extractImagePoints(Image image) {
        PixelReader reader = image.getPixelReader();
        ArrayList<double[]> points = new ArrayList<>();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = reader.getColor(x, y);
                if (color.getOpacity() > TRESHOLD) {
                    points.add(new double[]{x - (image.getWidth() / 2), y - (image.getHeight() / 2)});
                }
            }
        }
        return points;
    }


    public static ArrayList<double[]> convexHull(ArrayList<double[]> points) {
        points.sort((a, b) ->
                a[0] == b[0] ? Double.compare(a[1], b[1]) : Double.compare(a[0], b[0])
        );

        ArrayList<double[]> lower = new ArrayList<>();
        for (double[] p : points) {
            while (lower.size() >= 2 && cross(lower.get(lower.size() - 2), lower.get(lower.size() - 1), p) <= 0) {
                lower.remove(lower.size() - 1);
            }
            lower.add(p);
        }

        ArrayList<double[]> upper = new ArrayList<>();
        for (int i = points.size() - 1; i >= 0; i--) {
            double[] p = points.get(i);
            while (upper.size() >= 2 && cross(upper.get(upper.size() - 2), upper.get(upper.size() - 1), p) <= 0) {
                upper.remove(upper.size() - 1);
            }
            upper.add(p);
        }

        lower.remove(lower.size() - 1);
        upper.remove(upper.size() - 1);
        lower.addAll(upper);
        return lower;
    }

    private static double cross(double[] o, double[] a, double[] b) {
        return (a[0] - o[0]) * (b[1] - o[1]) - (a[1] - o[1]) * (b[0] - o[0]);
    }


}
