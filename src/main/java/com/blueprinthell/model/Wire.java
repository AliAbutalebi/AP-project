package com.blueprinthell.model;

import javafx.geometry.Point2D;

public class Wire {
    private int id;
    private Port sourcePort;
    private Port destinationPort;
    private ShapeType shapeType;
    private double length;
    private Packet packetOnWire;
    private Point2D startLocation;
    private Point2D endLocation;

    public Wire(Point2D startLocation, Point2D endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public double getSlope() {
        return (getEndLocation().getY() - getStartLocation().getY()) / (getEndLocation().getX() - getStartLocation().getX());
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

    public Point2D interpolate(double percent) {
        double newX = getStartLocation().getX() + (getEndLocation().getX() - getStartLocation().getX()) * percent;
        double newY = getStartLocation().getY() + (getEndLocation().getY() - getStartLocation().getY()) * percent;

        return new Point2D(newX, newY);
    }

}
