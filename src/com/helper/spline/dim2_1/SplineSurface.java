package com.helper.spline.dim2_1;

import com.helper.JavaHelper;
import com.helper.geometry.PointD;

public abstract class SplineSurface {

    PointD     workPos = new PointD();

    double[]   Us, Vs;
    double[][] values;

    int        currentU = -1, currentV = -1;

    public SplineSurface(int nU, int nV) {
        Us = new double[nU];
        Vs = new double[nV];
    }

    public void addOnU(double value, double u) {
        addU(u);
        addValue(value);
    }

    public void addOnV(double value, double v) {
        addV(v);
        addValue(value);
    }

    public void addU(double u) {
        currentU++;
        Us[currentU] = u;
    }

    public void addV(double v) {
        currentV++;
        Vs[currentV] = v;
    }

    public void addValue(double value) {
        values[currentU][currentV] = value;
    }

    public PointD derivate(double u, double v) {
        int uIndex = findUIndex(u);
        int vIndex = findVIndex(v);
        return derivate(u, v, uIndex, vIndex);
    }

    public PointD derivate(double u, double v, double uIndex, double vIndex) {
        double uDerivate = derivateByU(u, v, uIndex, vIndex);
        double vDerivate = derivateByV(u, v, uIndex, vIndex);
        return workPos.set(uDerivate, vDerivate);
    }

    public abstract double derivateByU(double u, double v, double uIndex, double vIndex);

    public abstract double derivateByV(double u, double v, double uIndex, double vIndex);

    /**
     * Returns i such that u is between Us[i] and Us[i+1]
     * 
     * @param u
     * @return
     */
    public int findUIndex(double u) {
        return JavaHelper.findInIncrease(u, Us) - 1;
    }

    /**
     * Returns i such that v is between Vs[i] and Vs[i+1]
     * 
     * @param v
     * @return
     */
    public int findVIndex(double v) {
        return JavaHelper.findInIncrease(v, Vs) - 1;
    }

    public double interpolate(double u, double v) {
        int uIndex = findUIndex(u);
        int vIndex = findVIndex(v);
        return interpolate(u, v, uIndex, vIndex);
    }

    public abstract double interpolate(double u, double v, int uIndex, int vIndex);

}
