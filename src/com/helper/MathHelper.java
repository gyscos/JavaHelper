package com.helper;

/**
 * @author Gyscos
 * 
 */
public class MathHelper {

    public static double degrees(double radians) {
        return 180 * radians / Math.PI;
    }

    /**
     * Returns the factorial of n = n!
     * 
     * @param n
     *            The value to return the factorial of.
     * @return The factorial of n = n!
     */
    public static int fact(int n) {
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static double interpolate(double from, double to, double progress) {
        return from + progress * (to - from);
    }

    public static float interpolate(float from, float to, float progress) {
        return from + progress * (to - from);
    }

    public static boolean isAinBC(double A, double B, double C) {
        if (A == B && B == C) {
            return true;
        } else if (A >= B && A <= C || A >= C && A <= B) {
            return true;
        }
        return false;
    }

    public static boolean isAinBsC(double A, double B, double C) {
        if (A == B && B == C) {
            return true;
        } else if (A >= B && A < C || A > C && A <= B) {
            return true;
        }
        return false;
    }

    public static boolean isAinBsCs(double A, double B, double C) {
        if (A == B && B == C) {
            return true;
        } else if (A > B && A < C || A > C && A < B) {
            return true;
        }
        return false;
    }

    public static double pow(double a, int b) {
        double result = 1;
        for (int i = 0; i < b; i++) {
            result *= a;
        }
        return result;
    }

    public static double radians(double degrees) {
        return Math.PI * degrees / 180;
    }

    /**
     * A sign function returning an integer. Math only has one for float/double.
     * 
     * @param v
     *            The value to check.
     * @return The value's sign : -1, +1 or 0.
     */
    public static int sign(double v) {
        if (v < 0)
            return -1;
        if (v > 0)
            return 1;
        return 0;
    }

    public static Double toDouble(Object o) {
        if (o instanceof Integer) {
            return Double.valueOf((Integer) o);
        } else if (o instanceof Double) {
            return (Double) o;
        }
        return new Double(0);
    }
}
