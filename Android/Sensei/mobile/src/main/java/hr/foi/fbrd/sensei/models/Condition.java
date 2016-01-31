package hr.foi.fbrd.sensei.models;


public class Condition {

    private String baseValue;
    private String secondValue;
    private int type;

    public Condition(int type) {
        this.type = type;
    }

    public String getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(String baseValue) {
        this.baseValue = baseValue;
    }

    public String getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(String secondValue) {
        this.secondValue = secondValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        if (secondValue == null) {
            return baseValue;
        } else {
            return baseValue + "," + secondValue;
        }
    }

    public static class Type {
        public static final int MANUAL = 1;
        public static final int INTERVAL = 2;
        public static final int VALUE = 3;
    }

}
