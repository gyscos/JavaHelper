package com.helper.plan.action;

import java.util.List;

import com.helper.plan.Memory;

public class Repeat implements Action {

    int          n;
    List<Action> A;

    public Repeat(int n, List<Action> A) {
        this.A = A;
        this.n = n;
    }

    @Override
    public void act(Memory memory) {
        for (int i = 0; i < n; i++)
            for (Action a : A)
                a.act(memory);
    }

}
