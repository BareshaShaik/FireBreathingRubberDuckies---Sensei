package hr.foi.fbrd.sensei.helpers;

import android.hardware.Sensor;

import hr.foi.fbrd.sensei.R;


public class IconHelper {

    public static int pickIcon(Sensor sensor) {
        switch (sensor.getType()) {
            case Sensor.TYPE_GRAVITY:
                return R.drawable.ic_gravity;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return R.drawable.ic_humidity;
            case Sensor.TYPE_LIGHT:
                return R.drawable.ic_light;
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return R.drawable.ic_motion;
            case Sensor.TYPE_PRESSURE:
                return R.drawable.ic_pressure;
            case Sensor.TYPE_STEP_COUNTER:
            case Sensor.TYPE_STEP_DETECTOR:
                return R.drawable.ic_step;
        }
        return R.drawable.ic_sensor;
    }

}
