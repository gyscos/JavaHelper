package com.helper.plan.action;

import java.util.ArrayList;
import java.util.List;

import com.helper.plan.Memory;
import com.helper.plan.condition.Condition;

public class Branch implements Action {
    Condition    C;
    List<Action> A;
    List<Action> B;

    public Branch(Condition C, List<Action> A) {
        this(C, A, new ArrayList<Action>());
    }

    public Branch(Condition C, List<Action> A, List<Action> B) {
        this.C = C;
        this.B = B;
        this.A = A;
    }

    @Override
    public void act(Memory memory) {
        if (C.isTrue(memory))
            for (Action action : A)
                action.act(memory);
        else
            for (Action action : B)
                action.act(memory);
    }
}
