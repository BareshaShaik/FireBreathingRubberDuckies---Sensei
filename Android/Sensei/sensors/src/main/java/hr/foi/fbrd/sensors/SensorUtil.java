package hr.foi.fbrd.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

public class SensorUtil {

    private static SensorManager sensorManager;
    private static Context context;

    public static void initialize(Context context) {
        SensorUtil.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public static SensorManager getSensorManager() {
        return sensorManager;
    }

    public static List<Sensor> getAllSensors() {
        return sensorManager
                .getSensorList(Sensor.TYPE_ALL);
    }

    public static List<Sensor> getCompatibleSensors() {
        List<Sensor> compatible = new ArrayList<>();
        List<Sensor> all = getAllSensors();
        for (Sensor sensor : all) {
            if (isSensorCompatible(sensor)) {
                compatible.add(sensor);
            }
        }
        return compatible;
    }

    public static boolean isSensorCompatible(Sensor sensor) {
        return sensor.getType() == Sensor.TYPE_STEP_COUNTER
                || sensor.getType() == Sensor.TYPE_STEP_DETECTOR
                || sensor.getType() == Sensor.TYPE_LIGHT
                || sensor.getType() == Sensor.TYPE_PRESSURE
                || sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY
                || sensor.getType() == Sensor.TYPE_SIGNIFICANT_MOTION
                || sensor.getType() == Sensor.TYPE_GRAVITY;
    }

    public static boolean startHeartMonitor(SensorEventListener listener, int delay) {
        Sensor heartSensor = getSensor(Sensor.TYPE_HEART_RATE);
        if (heartSensor != null) {
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, heartSensor, delay);
        }
        return false;
    }

    public static boolean stopHeartRateMonitor(SensorEventListener listener) {
        try {
            sensorManager.unregisterListener(listener);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Sensor getSensor(int type) {
        return sensorManager.getDefaultSensor(type);
    }

    public static boolean startSensor(int sensorType, SensorEventListener listener) {
        Sensor sensor = getSensor(sensorType);
        return sensor != null && sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public static boolean stopSensor(SensorEventListener listener, int sensorType) {
        Sensor sensor = getSensor(sensorType);
        if (listener != null && sensor != null) {
            sensorManager.unregisterListener(listener, sensor);
            return true;
        }
        return false;
    }
}
