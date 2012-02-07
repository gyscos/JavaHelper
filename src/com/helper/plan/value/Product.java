package com.helper.plan.value;

import com.helper.plan.Memory;

public class Product extends Value {
    Value[] values;

    public Product(Value... values) {
        this.values = values;
    }

    @Override
    public double getDouble(Memory memory) {
        double result = 1;
        for (Value value : values)
            result *= value.getDouble(memory);
        return result;
    }
}
