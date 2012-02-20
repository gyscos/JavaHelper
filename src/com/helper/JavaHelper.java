package com.helper;

public class JavaHelper {

    /**
     * Returns the first index i such that values[i] < target
     * 
     * @param target
     * @param values
     * @return
     */
    public static int findInDecrease(double target, double... values) {
        for (int i = 0; i < values.length; i++)
            if (target > values[i])
                return i;
        return values.length;
    }

    /**
     * Returns the first index i such that values[i] > target
     * 
     * @param target
     * @param values
     * @return
     */
    public static int findInIncrease(double target, double... values) {
        for (int i = 0; i < values.length; i++)
            if (target < values[i])
                return i;
        return values.length;
    }
}
