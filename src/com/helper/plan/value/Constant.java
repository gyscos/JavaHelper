package com.helper.plan.value;

import com.helper.plan.Memory;

public class Constant extends Value {
    double a;

    public Constant(double a) {
        this.a = a;
    }

    public Constant(int a) {
        this.a = a;
    }

    @Override
    public double getDouble(Memory memory) {
        return a;
    }
}
