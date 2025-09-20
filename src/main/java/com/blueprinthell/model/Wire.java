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

    public Wire() {}

    public Wire(Point2D startLocation, Point2D endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        id = lastId++;
    }

    public static int getPssedLargePacketLimit() {return PASSED_LARGE_PACKET_LIMIT;}

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

    public void setLength(double length) {this.length = length;}

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

    public Point2D interpolate(double percent) {
        double newX = getStartLocation().getX() + (getEndLocation().getX() - getStartLocation().getX()) * percent;
        double newY = getStartLocation().getY() + (getEndLocation().getY() - getStartLocation().getY()) * percent;

        return new Point2D(newX, newY);
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
