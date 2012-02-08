package com.helper.spline;

import com.helper.geometry.PointD;

public class Spline2D<T extends Spline> {
    T        splineX;
    T         splineY;

    private PointD workPoint = new PointD();

    public Spline2D(final T splineX, final T splineY) {
        this.splineX = splineX;
        this.splineY = splineY;
    }

    public Spline2D<T> addPoint(final double time, final double x, final double y) {
        splineX.addValue(time, x);
        splineY.addValue(time, y);
        return this;
    }

    public Spline2D<T> addPoint(final double time, final PointD value) {
        return addPoint(time, value.x, value.y);
    }

    public PointD derivate(final double time) {
        workPoint.x = splineX.derivate(time);
        workPoint.y = splineY.derivate(time);
        return workPoint;
    }

    public double getDuration() {
        return Math.max(splineX.getDuration(), splineY.getDuration());
    }

    public PointD interpolate(final double time) {
        workPoint.x = splineX.interpolate(time);
        workPoint.y = splineY.interpolate(time);
        return workPoint;
    }

    public PointD rederivate(final double time) {
        workPoint.x = splineX.rederivate(time);
        workPoint.y = splineY.rederivate(time);
        return workPoint;
    }
}
