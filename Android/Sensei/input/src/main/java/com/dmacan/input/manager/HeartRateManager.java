package com.dmacan.input.manager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.dmacan.input.listener.OnValueListener;
import com.dmacan.input.util.FitUtil;

public class HeartRateManager {

    private Sensor heartRate;
    private Context context;
    private SensorManager sensorManager;

    public HeartRateManager(Context context) {
        this.context = context;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void checkHeartRate(OnValueListener<Integer> listener) {
        FitUtil.checkHeartRate(listener::onValue);
    }


}
