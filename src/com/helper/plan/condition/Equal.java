package com.helper.plan.condition;

import com.helper.plan.Memory;
import com.helper.plan.value.Value;

public class Equal implements Condition {

    Value A;
    Value B;

    public Equal(Value A, Value B) {
        this.A = A;
        this.B = B;
    }

    @Override
    public boolean isTrue(Memory mem) {
        return A.getDouble(mem) == B.getDouble(mem);
    }

}
