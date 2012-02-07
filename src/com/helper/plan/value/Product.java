package com.helper.plan.value;

import com.helper.plan.Memory;

public class Product extends Value {
    Value[] values;

    public Product(Value... values) {
        this.values = values;
    }

    @Override
    public Value compute(Memory memory) {
        Value[] nv = new Value[values.length];
        for (int i = 0; i < values.length; i++)
            nv[i] = values[i].compute(memory);
        return new Product(nv);
    }

    @Override
    public double getDouble(Memory memory) {
        double result = 1;
        for (Value value : values)
            result *= value.getDouble(memory);
        return result;
    }
}
