package com.tomlepsky.beam;

public abstract class Stress {

    private double coordinate;
    private double value;
    private double[] magnitude;

    public Stress(double coordinate, double value) {
        this.coordinate = coordinate;
        this.value = value;
    }

    public double getCoordinate() {
        return coordinate;
    }

    public double getValue() {
        return value;
    }

    public double getShear() { return magnitude[0]; }

    public double getMoment() {
        return magnitude[1];
    }

    public double getTorsion() {
        return magnitude[2];
    }

    public double getDeflection() {
        return magnitude[3];
    }

    public void setMagnitude(double[] magnitude) {
        this.magnitude = magnitude;
    }

    @Override
    public String toString() {
        return "coordinate = " + coordinate +
                ", value = " + value +
                ", class = " + this.getClass();
    }

    public abstract void calculate(double x);

}
