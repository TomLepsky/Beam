package com.tomlepsky.beam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Beam {

    private final static int INTERVAL_POINTS = 2;

    private List<Stress> stresses;
    private double beamLength;

    private double[][] intervals;

    private double[][] shear;
    private double[][] moment;
    private double[][] torsion;
    private double[][] deflection;

    public Beam(List<Stress> stresses, double beamLength) {
        this.stresses = new ArrayList<>(stresses);
        this.beamLength = beamLength;
    }

    public Force calculateSupportingForce(double relativeX, double x) {
        double val = 0d;
        for (Stress stress: stresses) {
            stress.calculate(x);
            val += stress.getMoment();
        }
        val /= -(x - relativeX);

        return new Force(relativeX, val);
    }

    public void createIntervals() {
        List<Double> listIntervals = new ArrayList<>();
        listIntervals.add(0d);
        listIntervals.add(beamLength);
        for (Stress stress: stresses) {
            if (!listIntervals.contains(stress.getCoordinate())) {
                listIntervals.add(stress.getCoordinate());
            }
        }

        Collections.sort(listIntervals);

        intervals = new double[listIntervals.size() - 1][];
        for (int i = 0; i < listIntervals.size() - 1; i++) {

            double delta = (listIntervals.get(i + 1) - listIntervals.get(i)) / INTERVAL_POINTS;
            intervals[i] = new double[INTERVAL_POINTS + 1];
            intervals[i][0] = listIntervals.get(i);

            for (int j = 1; j < intervals[i].length; j++) {
                intervals[i][j] = intervals[i][j - 1] + delta;
            }
        }
    }

    private void calculateStressPoint(Stress stress, int i, int j) {
        stress.calculate(intervals[i][j]);
        shear[i][j]         += stress.getShear();
        moment[i][j]        += stress.getMoment();
        torsion [i][j]      += stress.getTorsion();
        deflection[i][j]    += stress.getDeflection();
    }

    public void calculateStresses(double initialTorsion, double initialDeflection) {
        shear       = new double[intervals.length][];
        moment      = new double[intervals.length][];
        torsion     = new double[intervals.length][];
        deflection  = new double[intervals.length][];

        for (int i = 0; i < intervals.length; i++) {
            int len = intervals[i].length;
            shear[i]        = new double[len];
            moment[i]       = new double[len];
            torsion[i]      = new double[len];
            deflection[i]   = new double[len];

            for (int j = 0; j < intervals[i].length; j++) {
                for (Stress stress: stresses) {
                    if (j != (intervals[i].length - 1)) {
                        if ((intervals[i][j] - stress.getCoordinate()) >= 0) {
                            calculateStressPoint(stress, i, j);
                        }
                    } else {
                        if ((intervals[i][j] - stress.getCoordinate()) > 0) {
                            calculateStressPoint(stress, i, j);
                        }
                    }
                }
                torsion[i][j] += initialTorsion;
                deflection[i][j] += (initialTorsion * intervals[i][j] + initialDeflection);
            }
        }
    }

    public abstract void calculateBeam();

    public double[][] getIntervals() {
        return intervals;
    }

    public double[][] getShear() {
        return shear;
    }

    public double[][] getMoment() {
        return moment;
    }

    public double[][] getTorsion() {
        return torsion;
    }

    public double[][] getDeflection() {
        return deflection;
    }

    public boolean addStress(Stress stress) {
        return stresses.add(stress);
    }

    public List<Stress> getStresses() {
        return stresses;
    }

    public double getBeamLength() {
        return beamLength;
    }
}
