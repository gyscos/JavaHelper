package com.helper.plan.condition;

import com.helper.plan.Memory;

public class And implements Condition {

    Condition A;
    Condition B;

    public And(Condition A, Condition B) {
        this.A = A;
        this.B = B;
    }

    @Override
    public boolean isTrue(Memory mem) {
        return A.isTrue(mem) && B.isTrue(mem);
    }

}
