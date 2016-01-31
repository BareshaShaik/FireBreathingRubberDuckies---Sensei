package com.dmacan.input.entity;

public class TimeCondition implements Condition {

    private long minutes;

    public TimeCondition(long minutes) {
        this.minutes = minutes;
    }

    public long getMinutes() {
        return minutes;
    }


    @Override
    public Type getType() {
        return Type.TIME;
    }
}
