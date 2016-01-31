package com.dmacan.input.manager;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.dmacan.input.entity.Input;
import com.dmacan.input.listener.OnValueListener;
import com.dmacan.input.util.SensorUtil;

public class LightInputManager extends InputManager<Double> {

    public LightInputManager(Input input) {
        super(input);
    }

    @Override
    public void getResult(OnValueListener<Double> listener) {
        SensorUtil.startSensor(Sensor.TYPE_LIGHT, new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                listener.onValue((double) event.values[0]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        });
    }
}
