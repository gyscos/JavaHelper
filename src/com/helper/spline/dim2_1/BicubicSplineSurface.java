package com.helper.spline.dim2_1;


public class BicubicSplineSurface extends SplineSurface {

    public BicubicSplineSurface(int nU, int nV) {
        super(nU, nV);
        // TODO Auto-generated constructor stub
    }

    @Override
    public double derivateByU(double u, double v, double uIndex, double vIndex) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double derivateByV(double u, double v, double uIndex, double vIndex) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double interpolate(double u, double v, int uIndex, int vIndex) {
        // TODO Auto-generated method stub
        return 0;
    }

}
