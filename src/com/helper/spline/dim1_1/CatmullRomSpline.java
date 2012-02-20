package com.helper.spline.dim1_1;

import com.helper.geometry.PointD;
import com.helper.spline.dim1_2.Spline2D;

public class CatmullRomSpline extends Spline {

    public static CatmullRomSpline regularInterpolate(double dt, double... values) {
        if (values.length < 2)
            return null;

        CatmullRomSpline result = new CatmullRomSpline(values.length + 2);

        result.addValue(-dt, values[1]);
        for (int i = 0; i < values.length; i++)
            result.addValue(dt * i, values[i]);
        result.addValue(dt * values.length, values[values.length - 2]);
        result.computeCoeffs();

        return result;
    }

    public static Spline2D regularInterpolate(double dt, PointD... values) {
        if (values.length < 2)
            return null;

        Spline2D result = new Spline2D(SplineType.CATMULLROM, SplineType.CATMULLROM, values.length + 2);

        result.addPoint(-dt, values[1]);
        for (int i = 0; i < values.length; i++)
            result.addPoint(dt * i, values[i]);
        result.addPoint(dt * values.length, values[values.length - 2]);
        result.computeCoeffs();

        return result;
    }

    double[][] coefs;

    CatmullRomSpline(final int n) {
        super(1, n);
    }

    @Override
    public void computeCoeffs() {
        // TODO Auto-generated method stub
        int size = values.length;

        coefs = new double[4][size];

        // Log.d(tag, "Computing...");
        for (int i = 1; i < size - 2; i++) {

            double Tm1 = times[i - 1];
            double Tp0 = times[i];
            double Tp1 = times[i + 1];
            double Tp2 = times[i + 2];

            for (int k = 0; k <= 1; k++) {

                double Pm1 = values[i - 1];
                double Pp0 = values[i];
                double Pp1 = values[i + 1];
                double Pp2 = values[i + 2];

                double dt = Tp1 - Tp0;
                double dp0 = ((Pp0 - Pm1) / (Tp0 - Tm1) + (Pp1 - Pp0)
                        / (Tp1 - Tp0))
                        / 2.0 * dt;
                double dp1 = ((Pp2 - Pp1) / (Tp2 - Tp1) + (Pp1 - Pp0)
                        / (Tp1 - Tp0))
                        / 2.0 * dt;

                coefs[0][i] = Pp0;
                coefs[1][i] = dp0;
                coefs[2][i] = 3 * (Pp1 - Pp0) - 2 * dp0 - dp1;
                coefs[3][i] = 2 * (Pp0 - Pp1) + dp0 + dp1;
            }
        }
    }

    @Override
    protected double derivate(final double time, final int i) {

        double Tb = times[i + 1];
        double Ta = times[i];

        double p = (time - Ta) / (Tb - Ta);
        double result = coefs[1][i] + p * (2 * coefs[2][i] + p * (3 * coefs[3][i]));

        return result / (Tb - Ta);
    }

    @Override
    protected double interpolate(final double time, final int i) {

        double Tb = times[i + 1];
        double Ta = times[i];

        double p = (time - Ta) / (Tb - Ta);
        double result = coefs[0][i] + p * (coefs[1][i] + p * (coefs[2][i] + p * (coefs[3][i])));

        return result;
    }

    @Override
    protected double rederivate(double time, int i) {

        double Tb = times[i + 1];
        double Ta = times[i];

        double p = (time - Ta) / (Tb - Ta);
        double result = 2 * coefs[2][i] + p * (6 * coefs[3][i]);

        return result / (Tb - Ta) / (Tb - Ta);
    }

}
