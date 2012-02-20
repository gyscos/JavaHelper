package com.helper.spline.dim1_1;

import com.helper.geometry.PointD;
import com.helper.spline.dim1_2.Spline2D;

/**
 * A linear spline, that interpolates values in a linear fashion.
 * 
 * @author gyscos
 */
public class LinearSpline extends Spline {

    public static LinearSpline regularInterpolate(double speed, double... values) {
        if (values.length < 2)
            return null;

        LinearSpline result = new LinearSpline(values.length);

        double t = 0;
        result.addValue(t, values[0]);
        for (int i = 1; i < values.length; i++) {
            t += Math.abs(values[i] - values[i - 1]) / speed;
            // System.out.println("Adding rotation step @ " + t);
            result.addValue(t, values[i]);
        }
        result.computeCoeffs();

        return result;
    }

    public static Spline2D regularInterpolate(double speed, PointD... values) {
        if (values.length < 2)
            return null;

        Spline2D result = new Spline2D(SplineType.LINEAR, SplineType.LINEAR, values.length);

        double t = 0;
        result.addPoint(t, values[0]);
        for (int i = 1; i < values.length; i++) {
            t += values[i].length(values[i - 1]) / speed;
            // System.out.println("Adding speed step @ " + t);
            result.addPoint(t, values[i]);
        }
        result.computeCoeffs();

        return result;
    }

    public LinearSpline(final int n) {
        super(0, n);
    }

    @Override
    protected double derivate(final double time, final int index) {
        double a = values[index];
        double b = values[index + 1];

        double duration = times[index + 1] - times[index];

        return (b - a) / duration;
    }

    @Override
    protected double interpolate(final double time, final int index) {
        if (index == values.length)
            return values[values.length - 1];

        double a = values[index];
        double b = values[index + 1];

        double duration = times[index + 1] - times[index];
        double ratio = (time - times[index]) / duration;

        return a + ratio * (b - a);
    }

    @Override
    protected double rederivate(double time, int index) {
        return 0;
    }

}
