package com.helper.plan.value;

import com.helper.plan.Memory;

public class Addition extends Value {
    Value[] values;

    public Addition(Value... values) {
        this.values = values;
    }

    @Override
    public Value compute(Memory memory) {

        Value[] nv = new Value[values.length];
        for (int i = 0; i < values.length; i++)
            nv[i] = values[i].compute(memory);
        return new Addition(nv);
    }

    @Override
    public double getDouble(Memory memory) {
        double sum = 0;
        for (Value value : values)
            sum += value.getDouble(memory);
        return sum;
    }
}
