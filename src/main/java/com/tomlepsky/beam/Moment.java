package com.tomlepsky.beam;

public class Moment extends Stress {

    public Moment(double coordinate, double value) {
        super(coordinate, value);
    }

    @Override
    public void calculate(double x) {
        double[] result = new double[4];
        result[0] = 0;
        result[1] = getValue();
        for (int i = 2; i < result.length; i++) {
            result[i] = result[i - 1] * (x - getCoordinate()) / (i - 1);
        }
        setMagnitude(result);
    }
}
