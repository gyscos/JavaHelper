package com.helper.spline.dim1_1;


public enum SplineType {
    NATURAL, CATMULLROM, LINEAR;

    public Spline createSpline(int n) {
        switch (this) {
            case LINEAR:
                return new LinearSpline(n);
            case NATURAL:
                return new NaturalSpline(n);
            case CATMULLROM:
                return new CatmullRomSpline(n);
            default:
                return null;
        }
    }
}
