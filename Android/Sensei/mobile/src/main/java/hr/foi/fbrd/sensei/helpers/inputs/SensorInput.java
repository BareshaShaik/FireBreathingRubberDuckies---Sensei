package hr.foi.fbrd.sensei.helpers.inputs;

public class SensorInput {

    private boolean manual = false;
    private long period;
    private int typeId;
    private int data;

    public SensorInput(int typeId) {
        this.typeId = typeId;
    }
    
    public void setParams(boolean manual, long period){
        this.manual = manual;
        this.period = period;
    }
}
