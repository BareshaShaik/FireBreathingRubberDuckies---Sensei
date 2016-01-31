package com.dmacan.input.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Input {

    private final Type type;
    private final List<Condition.Type> applicableConditions;

    public Input(Type type, List<Condition.Type> applicableConditions) {
        this.type = type;
        this.applicableConditions = applicableConditions;
    }

    public Input(Type type, Condition.Type... applicableConditions) {
        this.type = type;
        this.applicableConditions = new ArrayList<>(Arrays.asList(applicableConditions));
    }

    public List<Condition.Type> getApplicableConditions() {
        return applicableConditions;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        String value = type.toString();
        value += "\t";
        for (Condition.Type condition : applicableConditions) {
            value += condition.toString() + ", ";
        }
        return value;
    }

    public enum Type {
        STEP,
        HEART_RATE,
        PHONE_CALL,
        LOCATION,
        TEMPERATURE,
        LIGHT,
        NFC,
        QR,
        MOTION
    }

}
