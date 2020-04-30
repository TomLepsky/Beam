package com.tomlepsky.beam;

public class UniformlyDistributedLoad extends DistributedLoad {

    public UniformlyDistributedLoad(double coordinate, double value) {
        super(coordinate, value);
    }

    /*
    @Override
    public void calculate(double x) {
        super.calculate(x);
        double[] result = getMagnitude();
        for (int i = 0; i < result.length; i++) {
            result[i] *= -1;
        }
        setMagnitude(result);
    }
     */
}
