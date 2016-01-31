package com.dmacan.input;

import java.io.Serializable;

public class Channel implements Serializable{

    private String name;
    private int id;
    private Object image;
    private boolean joinable;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Channel(int id) {
        this.id = id;
        joinable = id == Id.LOCATION || id == Id.LIGHT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public boolean isJoinable() {
        return joinable;
    }

    public void setJoinable(boolean joinable) {
        this.joinable = joinable;
    }

    public static class Id {
        public static final int HEART_RATE = 1;
        public static final int LOCATION = 4;
        public static final int FRIDGE = 2;
        public static final int MOTION = 3;
        public static final int LIGHT = 5;
        public static final int WIFI_FRUSTRATION = 6;
    }
}
