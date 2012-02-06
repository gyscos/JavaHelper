package com.helper.spline;

public class CatmullRomSpline extends Spline {

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
    double derivate(final double time, final int i) {

        double Tb = times[i + 1];
        double Ta = times[i];

        double p = (time - Ta) / (Tb - Ta);
        double result = coefs[1][i] + p * (2 * coefs[2][i] + p * (3 * coefs[3][i]));

        return result / (Tb - Ta);
    }

    @Override
    double interpolate(final double time, final int i) {

        double Tb = times[i + 1];
        double Ta = times[i];

        double p = (time - Ta) / (Tb - Ta);
        double result = coefs[0][i] + p * (coefs[1][i] + p * (coefs[2][i] + p * (coefs[3][i])));

        return result;
    }

    @Override
    double rederivate(double time, int i) {

        double Tb = times[i + 1];
        double Ta = times[i];

        double p = (time - Ta) / (Tb - Ta);
        double result = 2 * coefs[2][i] + p * (6 * coefs[3][i]);

        return result / (Tb - Ta) / (Tb - Ta);
    }

}
