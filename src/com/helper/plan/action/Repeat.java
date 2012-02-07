package com.helper.plan.action;

import java.util.List;

import com.helper.plan.Memory;
import com.helper.plan.value.Value;

public class Repeat implements Action {

    Value        v;
    List<Action> A;

    public Repeat(Value v, List<Action> A) {
        this.A = A;
        this.v = v;
    }

    @Override
    public void act(Memory memory) {
        int n = v.getInt(memory);
        for (int i = 0; i < n; i++)
            for (Action a : A)
                a.act(memory);
    }

}
