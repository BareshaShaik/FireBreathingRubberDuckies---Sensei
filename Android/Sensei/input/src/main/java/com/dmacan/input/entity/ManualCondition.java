package com.dmacan.input.entity;

public class ManualCondition implements Condition {

    @Override
    public Type getType() {
        return Type.MANUAL;
    }
}
