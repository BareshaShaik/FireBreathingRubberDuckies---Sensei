package com.dmacan.input.entity;

public class ValueCondition<T> implements Condition {

    private T minValue;
    private T maxValue;

    public ValueCondition(T minValue) {
        this.minValue = minValue;
        this.maxValue = minValue;
    }

    public ValueCondition(T minValue, T maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public T getMinValue() {
        return minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    @Override
    public Type getType() {
        return Type.VALUE;
    }
}
