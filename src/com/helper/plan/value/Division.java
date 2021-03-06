package com.helper.plan.value;

import com.helper.plan.Memory;

public class Division extends Value {
    Value A;
    Value B;

    public Division(Value A, Value B) {
        this.A = A;
        this.B = B;
    }

    @Override
    public Value compute(Memory memory) {
        return new Division(A.compute(memory), B.compute(memory));
    }

    @Override
    public double getDouble(Memory memory) {
        return A.getDouble(memory) / B.getDouble(memory);
    }
}
