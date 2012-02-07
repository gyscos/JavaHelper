package com.helper.plan.condition;

import com.helper.plan.Memory;
import com.helper.plan.value.Value;

public class BiggerThan implements Condition {

    Value A;
    Value B;

    public BiggerThan(Value A, Value B) {
        this.A = A;
        this.B = B;
    }

    @Override
    public boolean isTrue(Memory mem) {
        return A.getDouble(mem) > B.getDouble(mem);
    }

}
