package com.tomlepsky.beam;

import java.util.List;

public class AnchorageBeam extends Beam {

    private Stress restraintMoment;
    private Stress restraintSupportingForce;

    public AnchorageBeam(List<Stress> stresses, double beamLength) {
        super(stresses, beamLength);
    }

    public Stress calculateRestraintMoment() {
        double val = 0d;
        for (Stress stress: getStresses()) {
            stress.calculate(0);
            val += stress.getMoment();
        }
        return new Moment(0, -val);
    }

    @Override
    public void calculateBeam() {
        restraintMoment = calculateRestraintMoment();
        addStress(restraintMoment);
        restraintSupportingForce = calculateSupportingForce(0, getBeamLength());
        addStress(restraintSupportingForce);

        createIntervals();
        calculateStresses(0, 0);
    }

    public Stress getRestraintMoment() {
        return restraintMoment;
    }

    public Stress getRestraintSupportingForce() {
        return restraintSupportingForce;
    }
}
