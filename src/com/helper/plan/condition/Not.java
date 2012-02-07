package com.helper.plan.condition;

import com.helper.plan.Memory;

public class Not implements Condition {

    Condition C;

    public Not(Condition C) {
        this.C = C;
    }

    @Override
    public boolean isTrue(Memory mem) {
        return !C.isTrue(mem);
    }

}
