package com.tomlepsky.beam;

public class Force extends Stress {

    public Force(double coordinate, double value) {
        super(coordinate, value);
    }

    @Override
    public void calculate(double x) {
        double[] result = new double[4];
        result[0] = getValue();
        for (int i = 1; i < result.length; i++) {
            result[i] = result[i - 1] * (x - getCoordinate()) / i;
        }
        setMagnitude(result);
    }

}
