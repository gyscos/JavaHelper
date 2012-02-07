package com.helper.plan.action;

import java.util.List;

import com.helper.plan.Memory;
import com.helper.plan.condition.Condition;

public class Loop implements Action {

    Condition    C;
    List<Action> A;

    public Loop(Condition C, List<Action> A) {
        this.C = C;
        this.A = A;
    }

    @Override
    public void act(Memory memory) {
        while (C.isTrue(memory))
            for (Action action : A)
                action.act(memory);
    }

}
