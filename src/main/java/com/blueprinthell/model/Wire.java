package com.blueprinthell.model;

import javafx.geometry.Point2D;

import java.util.ArrayList;

public class Wire {
    private static final int PASSED_LARGE_PACKET_LIMIT = 3;
    private int id;
    private static int lastId = 0;
    private Port sourcePort;
    private int sourcePortId;
    private Port destinationPort;
    private int destinationPortId;
    private double length;
    private Packet packetOnWire;
    private int packetOnWireId;
    private Point2D startLocation;
    private Point2D endLocation;
    private ArrayList<Point2D> controlPoints = new ArrayList<>();
    private int passedLargePackets = 0;

    public Wire() {
    }

    public Wire(Point2D startLocation, Point2D endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        id = lastId++;
    }

    public static int getPssedLargePacketLimit() {
        return PASSED_LARGE_PACKET_LIMIT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSourcePort(Port sourcePort) {
        this.sourcePort = sourcePort;
    }

    public Port getSourcePort() {
        return sourcePort;
    }

    public void setDestinationPort(Port destinationPort) {
        this.destinationPort = destinationPort;
    }

    public Port getDestinationPort() {
        return destinationPort;
    }

    public double getLength() {
        return startLocation.distance(endLocation);
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setPacketOnWire(Packet packetOnWire) {
        this.packetOnWire = packetOnWire;
    }

    public Packet getPacketOnWire() {
        return packetOnWire;
    }

    public void setStartLocation(Point2D startLocation) {
        this.startLocation = startLocation;
    }

    public Point2D getStartLocation() {
        return startLocation;
    }

    public void setEndLocation(Point2D endLocation) {
        this.endLocation = endLocation;
    }

    public Point2D getEndLocation() {
        return endLocation;
    }

    public ShapeType getShapeType() {
        return getSourcePort().getShapeType();
    }

    public void addControlPoint() {
        int newCount = controlPoints.size() + 1;

        controlPoints = new ArrayList<>();
        for (int i = 0; i < newCount; i++) {
            controlPoints.add(makeControlPoint(i + 1, newCount));
        }
    }

    public void updateControlPoints() {
        for (int i = 0; i <= controlPoints.size(); i++) {
            controlPoints.set(i, makeControlPoint(i + 1, controlPoints.size()));
        }
    }

    public Point2D makeControlPoint(int i, int n) {
        double lengthX = endLocation.getX() - startLocation.getX();
        double lengthY = endLocation.getY() - startLocation.getY();
        double x = startLocation.getX() + lengthX / (n + 1) * i;
        double y = startLocation.getY() + lengthY / (n + 1) * i;
        return new Point2D(x, y);
    }


    public ArrayList<Point2D> getControlPoints() {
        return controlPoints;
    }

    public Point2D interpolate(double progress) {
        progress = clamp(progress, 0, 1);

        switch (controlPoints.size()) {
            case 0 -> {
                return lerp(startLocation, endLocation, progress);
            }
            case 1 -> {
                Point2D p1 = controlPoints.get(0);
                return quadBezier(startLocation, p1, endLocation, progress);
            }
            case 2 -> {
                Point2D p1 = controlPoints.get(0), p2 = controlPoints.get(1);
                return cubicBezier(startLocation, p1, p2, endLocation, progress);
            }
            default -> {
                double midX = (controlPoints.get(1).getX() + controlPoints.get(2).getX()) / 2;
                double midY = (controlPoints.get(1).getY() + controlPoints.get(2).getY()) / 2;
                Point2D mid = new Point2D(midX, midY);

                if (progress < 0.5) {
                    double t = progress * 2;
                    return cubicBezier(startLocation, controlPoints.get(0), controlPoints.get(1), mid, t);
                } else {
                    double t = (progress - 0.5) * 2;
                    Point2D mirror = new Point2D(2 * midX - controlPoints.get(1).getX(), 2 * midY - controlPoints.get(1).getY());
                    return cubicBezier(mid, mirror, controlPoints.get(2), endLocation, t);
                }
            }
        }
    }


private static Point2D lerp(Point2D a, Point2D b, double t) {
    return new Point2D(a.getX() + (b.getX() - a.getX()) * t,
            a.getY() + (b.getY() - a.getY()) * t);
}

private static Point2D quadBezier(Point2D startLocation, Point2D p1, Point2D p2, double t) {
    double u = 1 - t;
    double x = u * u * startLocation.getX() + 2 * u * t * p1.getX() + t * t * p2.getX();
    double y = u * u * startLocation.getY() + 2 * u * t * p1.getY() + t * t * p2.getY();
    return new Point2D(x, y);
}

private static Point2D cubicBezier(Point2D startLocation, Point2D p1, Point2D p2, Point2D p3, double t) {
    double u = 1 - t;
    double x = u * u * u * startLocation.getX() + 3 * u * u * t * p1.getX() + 3 * u * t * t * p2.getX() + t * t * t * p3.getX();
    double y = u * u * u * startLocation.getY() + 3 * u * u * t * p1.getY() + 3 * u * t * t * p2.getY() + t * t * t * p3.getY();
    return new Point2D(x, y);
}

private static double clamp(double v, double min, double max) {
    return v < min ? min : (v > max ? max : v);
}

public int getPassedLargePackets() {
    return passedLargePackets;
}

public void setPassedLargePackets(int passedLargePackets) {
    this.passedLargePackets = passedLargePackets;
}

public int getSourcePortId() {
    return sourcePortId;
}

public void setSourcePortId(int sourcePortId) {
    this.sourcePortId = sourcePortId;
}

public int getDestinationPortId() {
    return destinationPortId;
}

public void setDestinationPortId(int destinationPortId) {
    this.destinationPortId = destinationPortId;
}

public int getPacketOnWireId() {
    return packetOnWireId;
}

public void setPacketOnWireId(int packetOnWireId) {
    this.packetOnWireId = packetOnWireId;
}

public void reset() {
    setPassedLargePackets(0);
}

public boolean packetPassedPoint(Packet packet, Point2D point) {
    double packetProgress = startLocation.distance(packet.getLocation());
    double packetLastProgress = startLocation.distance(packet.getLastLocation());
    double pointProgress = startLocation.distance(point);
    return packetProgress > pointProgress && packetLastProgress < pointProgress;
}
}
