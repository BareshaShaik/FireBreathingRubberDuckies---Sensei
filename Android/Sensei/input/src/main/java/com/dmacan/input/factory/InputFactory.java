package com.dmacan.input.factory;

import android.content.Context;
import android.hardware.Sensor;

import com.dmacan.input.entity.Condition;
import com.dmacan.input.entity.Input;
import com.dmacan.input.util.CameraUtil;
import com.dmacan.input.util.FitUtil;
import com.dmacan.input.util.LocationUtil;
import com.dmacan.input.util.NFCUtil;
import com.dmacan.input.util.PhoneUtil;
import com.dmacan.input.util.SensorUtil;

import java.util.ArrayList;
import java.util.List;

public class InputFactory {

    private static Context context;
    private static boolean isPhone;
    private static Condition.Type[] allConditions;
    private static List<Input> allInputs;
    private static List<Input.Type> allTypes;

    public static void initialize(Context context, boolean isPhone) {
        InputFactory.context = context;
        InputFactory.isPhone = isPhone;
        CameraUtil.initialize(context);
        PhoneUtil.initialize(context);
        NFCUtil.initialize(context);
        SensorUtil.initialize(context);
        LocationUtil.initialize(context);
        FitUtil.initialize(context);
    }

    public static Input createInput(Sensor sensor) {
        switch (sensor.getType()) {
            case Sensor.TYPE_LIGHT:
                return new Input(Input.Type.LIGHT, all());
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                return new Input(Input.Type.TEMPERATURE, all());
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return new Input(Input.Type.MOTION, Condition.Type.MANUAL, Condition.Type.VALUE);
            case Sensor.TYPE_STEP_COUNTER:
                return new Input(Input.Type.STEP, all());
            case Sensor.TYPE_HEART_RATE:
                return new Input(Input.Type.HEART_RATE, Condition.Type.MANUAL);
        }
        return null;
    }

    public static Input createNFCInput() {
        return new Input(Input.Type.NFC, Condition.Type.MANUAL, Condition.Type.VALUE);
    }

    public static Input createQRInput() {
        return new Input(Input.Type.QR, Condition.Type.MANUAL, Condition.Type.VALUE);
    }

    public static Input createLocationInput() {
        return new Input(Input.Type.LOCATION, all());
    }

    public static Input createPhoneCallInput() {
        return new Input(Input.Type.PHONE_CALL, Condition.Type.VALUE);
    }

    private static Condition.Type[] all() {
        if (allConditions == null) {
            allConditions = new Condition.Type[]{Condition.Type.MANUAL, Condition.Type.VALUE, Condition.Type.TIME};
        }
        return allConditions;
    }

    public static List<Input> availableInputs(Context context) {
        if (allInputs == null) {
            allInputs = new ArrayList<>();
            allTypes = new ArrayList<>();
            List<Sensor> sensors = SensorUtil.getCompatibleSensors();
            for (Sensor sensor : sensors) {
                addInput(createInput(sensor));
            }
            if (PhoneUtil.isPhoneAvailable()) {
                addInput(createPhoneCallInput());
            }
            if (NFCUtil.hasNFC()) {
                addInput(createNFCInput());
            }
            if (CameraUtil.isCameraAvailable()) {
                addInput(createQRInput());
            }
            if (LocationUtil.isLocationAvailable()) {
                addInput(createLocationInput());
            }
            if (FitUtil.isHeartRateAvailable(isPhone)) {
                addInput(createHeartRateInput());
            }
        }
        return allInputs;
    }

    public static Input createHeartRateInput() {
        return new Input(Input.Type.HEART_RATE, Condition.Type.MANUAL);
    }

    private static void addInput(Input input) {
        if (input != null && !allTypes.contains(input.getType())) {
            allInputs.add(input);
            allTypes.add(input.getType());
        }
    }

}