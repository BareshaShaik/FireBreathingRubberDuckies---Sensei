package com.dmacan.input.util;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.dmacan.input.entity.Event;
import com.dmacan.input.listener.OnValueListener;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.schedulers.Schedulers;

public class FitUtil {

    public static Context context;

    public static void initialize(Context context) {
        FitUtil.context = context;
    }

    public static void checkHeartRate(final OnValueListener<Integer> listener) {
        final SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Log.i("DAM", "event values: " + Arrays.toString(event.values));
                sensorManager.unregisterListener(this);
                listener.onValue((int) event.values[0]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        /*Observable.defer(FitUtil::heartRate)
                .delay(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onValue);*/
    }

    private static Observable<Integer> heartRate() {
        return Observable.just(new Random().nextInt(90 - 60) + 60)
                .subscribeOn(Schedulers.computation());
    }

    public static void checkNumberOfSteps(Event event, OnValueListener<Integer> listener) {
        Observable.defer(FitUtil::stepCount)
                .subscribe(listener::onValue);
    }

    private static Observable<Integer> stepCount() {
        return Observable.just(new Random().nextInt(20 - 10) + 10)
                .delay(new Random().nextInt(1500 - 500) + 500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation());
    }

    public static boolean isHeartRateAvailable(boolean isPhone) {
        return !isPhone;
    }
}