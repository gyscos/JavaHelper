package com.helper.plan.value;

import com.helper.plan.Memory;

public class Variable extends Value {

    String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Value compute(Memory memory) {
        // compute this variable ?
        // Nope, we only want to compute the first level, to avoid errors like
        // x <- x + 1
        return memory.getValue(name);
    }

    @Override
    public double getDouble(Memory memory) {
        return memory.getValue(name).getDouble(memory);
    }
}
