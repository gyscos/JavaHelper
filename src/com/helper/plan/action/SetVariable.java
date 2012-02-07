package com.helper.plan.action;

import com.helper.plan.Memory;
import com.helper.plan.value.Value;

public class SetVariable implements Action {

    String name;
    Value  v;

    public SetVariable(String name, Value v) {
        this.name = name;
        this.v = v;
    }

    @Override
    public void act(Memory memory) {
        memory.setValue(name, v);
    }
}
