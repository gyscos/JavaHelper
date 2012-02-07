package com.helper.spline;

public class NaturalSpline extends Spline {

    double[] coefs;

    public NaturalSpline(final int n) {
        super(0, n);
        coefs = new double[n];
    }

    /**
     * I have to admit, I didn't understand everything... Grabbed it from
     * POV-Ray code source, backend/math/splines.cpp from http://povray.org
     */
    @Override
    public void computeCoeffs() {

        int size = values.length;

        coefs = new double[size];

        double[] h = new double[size];
        double[] b = new double[size];
        double[] u = new double[size];
        double[] v = new double[size];

        for (int i = 0; i < size - 1; i++) {
            // Duration
            h[i] = times[i + 1] - times[i];
            // Slope
            b[i] = (values[i + 1] - values[i]) / h[i];
        }

        u[1] = 2 * (h[0] + h[1]);
        v[1] = 6 * (b[1] - b[0]);

        for (int i = 2; i < size - 1; i++) {
            u[i] = 2 * (h[i] + h[i - 1]) - (h[i - 1] * h[i - 1]) / u[i - 1];
            v[i] = 6 * (b[i] - b[i - 1]) - (h[i - 1] * v[i - 1]) / u[i - 1];
        }

        coefs[size - 1] = 0;

        for (int i = size - 2; i > 0; i--) {
            coefs[i] = (v[i] - h[i] * coefs[i + 1]) / u[i];
        }

        coefs[0] = 0;

    }

    @Override
    double derivate(final double time, final int i) {

        double h = times[i + 1] - times[i];

        // Base equation is a.x^3 + b.x^2 + c.x + d

        h = times[i + 1] - times[i];

        double a = (coefs[i + 1] - coefs[i]) / (6.0 * h);
        double b = coefs[i] / 2.0;
        double c = -(h / 6.0) * (coefs[i + 1] + 2.0 * coefs[i]) + (values[i + 1] - values[i]) / h;
        double x = time - times[i];

        // So derivative is 3.a.x^2 + 2.b.x + c

        return c + (2 * b + 3 * a * x) * x;
    }

    @Override
    double interpolate(final double time, final int i) {

        // C-style !
        double h;

        // Basically, we're building a 3-rd degree
        // polynome in u = (time - times[i])
        h = times[i + 1] - times[i];

        // Base equation is a.x^3 + b.x^2 + c.x + d
        double a = (coefs[i + 1] - coefs[i]) / (6.0 * h);
        double b = coefs[i] / 2.0;
        double c = -(h / 6.0) * (coefs[i + 1] + 2.0 * coefs[i]) + (values[i + 1] - values[i]) / h;
        double d = values[i];
        double x = time - times[i];

        return d + (c + (b + (a * x)) * x) * x;
    }

    @Override
    double rederivate(double time, int i) {

        double h = times[i + 1] - times[i];

        // Base equation is a.x^3 + b.x^2 + c.x + d

        h = times[i + 1] - times[i];

        double a = (coefs[i + 1] - coefs[i]) / (6.0 * h);
        double b = coefs[i] / 2.0;
        double x = time - times[i];

        return 2 * b + 6 * a * x;
    }
}
