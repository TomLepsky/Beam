package com.tomlepsky.beam;

import java.util.List;

public class HingedBearingBeam extends Beam {

    private double HMSCoord;
    private double HISCoord;

    private Stress hingedMovableSupport;
    private Stress hingedImmovableSupport;

    public HingedBearingBeam(List<Stress> stresses, double beamLength, double HISCoord, double HMSCoord) {
        super(stresses, beamLength);
        this.HISCoord = HISCoord;
        this.HMSCoord = HMSCoord;
    }

    public double[] initialDeflectionParameters() {
        double[][] a = {{1.0, HMSCoord}, {1.0 , HISCoord}};
        double[] b = {-neutralDeflectionCondition(HMSCoord), -neutralDeflectionCondition(HISCoord)};

        return Matrix.cramer(a, b);
    }

    private double neutralDeflectionCondition(double x) {
        double sum = 0.0;
        for (Stress stress : getStresses()) {
            if ((x - stress.getCoordinate()) > 0) {
                stress.calculate(x);
                sum += stress.getDeflection();
            }
        }
        return sum;
    }

    @Override
    public void calculateBeam() {
        Stress hingedImmovableSupport = calculateSupportingForce(HISCoord, HMSCoord);
        Stress hingedMovableSupport = calculateSupportingForce(HMSCoord, HISCoord);
        addStress(hingedImmovableSupport);
        addStress(hingedMovableSupport);

        createIntervals();
        double[] initialParams = initialDeflectionParameters();
        calculateStresses(initialParams[1], initialParams[0]);
    }

    public Stress getHingedMovableSupport() {
        return hingedMovableSupport;
    }

    public Stress getHingedImmovableSupport() {
        return hingedImmovableSupport;
    }

}
