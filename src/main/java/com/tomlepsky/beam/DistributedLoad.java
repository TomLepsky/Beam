package com.tomlepsky.beam;

public abstract class DistributedLoad extends Stress {

    public DistributedLoad(double coordinate, double value) {
        super(coordinate, value);
    }

    @Override
    public void calculate(double x) {
        double[] result = new double[4];
        result[0] = getValue() * (x - getCoordinate());
        for (int i = 1; i < result.length; i++) {
            result[i] = result[i - 1] * (x - getCoordinate()) / (i + 1);
        }
        setMagnitude(result);
    }
}
