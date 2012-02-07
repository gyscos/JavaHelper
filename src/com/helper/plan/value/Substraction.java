package com.helper.plan.value;

import com.helper.plan.Memory;

public class Substraction extends Value {
    Value A;
    Value B;

    public Substraction(Value A, Value B) {
        this.A = A;
        this.B = B;
    }

    @Override
    public Value compute(Memory memory) {
        return new Substraction(A.compute(memory), B.compute(memory));
    }

    @Override
    public double getDouble(Memory memory) {
        return A.getDouble(memory) - B.getDouble(memory);
    }
}
