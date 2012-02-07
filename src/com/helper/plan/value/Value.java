package com.helper.plan.value;

import com.helper.plan.Memory;

public abstract class Value {

    public Value compute(Memory memory) {
        return this;
    }

    public double getDouble(Memory memory) {
        return 0;
    }

    public int getInt(Memory memory) {
        return (int) getDouble(memory);
    }

    public String getString(Memory mem) {
        return "";
    }
}
