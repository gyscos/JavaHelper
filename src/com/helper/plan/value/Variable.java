package com.helper.plan.value;

import com.helper.plan.Memory;

public class Variable extends Value {

    String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public double getDouble(Memory memory) {
        return memory.getValue(name).getDouble(memory);
    }
}
