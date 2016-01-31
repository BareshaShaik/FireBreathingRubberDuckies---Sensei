package com.dmacan.input.entity;

public interface Condition {

    Type getType();

    enum Type {
        MANUAL,
        TIME,
        VALUE
    }

}
