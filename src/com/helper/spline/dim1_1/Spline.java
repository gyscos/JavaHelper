package com.helper.spline.dim1_1;

public abstract class Spline {
    protected double[] times;
    protected double[] values;

    protected int      nMin;

    int                k = 0;

    public Spline(final int nMin, final int n) {
        this.nMin = nMin;
        times = new double[n];
        values = new double[n];
    }

    public Spline addValue(final double time, final double value) {
        times[k] = time;
        values[k] = value;
        k++;
        return this;
    }

    /**
     * Pre-compute some coefficents for advanced type of splines (cubic,
     * natural, ...)
     */
    public void computeCoeffs() {
    }

    /**
     * Returns the derivative of the spline at the given time.
     */
    public double derivate(final double time) {
        int index = getPreviousIndex(time);
        if (index < 0)
            index = 0;
        return derivate(time, index);
    }

    protected abstract double derivate(double time, int index);;

    public double getDuration() {
        return times[times.length - nMin - 1];
    }

    /**
     * Returns the last index before the given time.
     * 
     * @param time
     * @return
     */
    public int getPreviousIndex(final double time) {
        for (int i = 0; i < times.length; i++) {
            if (times[i] > time)
                return i - 1;
        }
        return times.length - 1;
    }

    /**
     * Returns the interpolation by the spline at the given time.
     * 
     * @param time
     * @return
     */
    public double interpolate(final double time) {
        if (time <= times[nMin])
            return values[nMin];
        if (time >= times[times.length - 1 - nMin])
            return values[values.length - 1 - nMin];

        int index = getPreviousIndex(time);
        if (index < 0)
            index = 0;
        return interpolate(time, index);
    }

    protected abstract double interpolate(final double time, int index);

    public void print(double dt) {
        if (dt == 0)
            return;

        double t = 0;
        while (t <= getDuration()) {
            System.out.println("T = " + t + " :: " + interpolate(t));
            t += dt;
        }
    }

    public double rederivate(final double time) {
        int index = getPreviousIndex(time);
        if (index < 0)
            index = 0;
        return rederivate(time, index);
    }

    protected abstract double rederivate(double time, int index);
}
