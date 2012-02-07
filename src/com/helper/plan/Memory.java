package com.helper.plan;

import java.util.HashMap;

import com.helper.plan.value.Value;

public class Memory {
    HashMap<String, Value> values = new HashMap<String, Value>();

    public Value getValue(String name) {
        return values.get(name);
    }

    public void setValue(String name, Value value) {
        values.put(name, value);
    }
}
