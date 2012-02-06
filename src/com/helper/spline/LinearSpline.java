package com.helper.spline;

/**
 * A linear spline, that interpolates values in a linear fashion.
 * 
 * @author gyscos
 */
public class LinearSpline extends Spline {

    public LinearSpline(final int n) {
        super(0, n);

    }

    @Override
    public double derivate(final double time, final int index) {
        double a = values[index];
        double b = values[index + 1];

        double duration = times[index + 1] - times[index];

        return (b - a) / duration;
    }

    @Override
    double interpolate(final double time, final int index) {
        if (index == values.length)
            return values[values.length - 1];

        double a = values[index];
        double b = values[index + 1];

        double duration = times[index + 1] - times[index];
        double ratio = (time - times[index]) / duration;

        return a + ratio * (b - a);
    }

    @Override
    double rederivate(double time, int index) {
        return 0;
    }

}
