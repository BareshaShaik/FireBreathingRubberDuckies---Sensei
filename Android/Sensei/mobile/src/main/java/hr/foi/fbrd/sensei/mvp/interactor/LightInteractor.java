package hr.foi.fbrd.sensei.mvp.interactor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.dmacan.input.Channel;
import com.dmacan.input.LightResponse;

import hr.foi.fbrd.sensei.SenseiApp;
import hr.foi.fbrd.sensei.listeners.OnLightSentListener;
import hr.foi.fbrd.sensei.models.BaseResponse;
import hr.foi.fbrd.sensei.network.ApiService;
import hr.foi.fbrd.sensei.network.BaseCallback;
import hr.foi.fbrd.sensors.SensorUtil;
import retrofit.Call;
import retrofit.Response;

public class LightInteractor implements SensorEventListener {

    public static final String DEVICE_ID = "deviceId";
    private ApiService service;
    private Call<BaseResponse<LightResponse>> call;
    private BaseCallback<BaseResponse<LightResponse>> callback;
    private OnLightSentListener lightSentListener;
    private float rate;

    public LightInteractor(ApiService apiService, OnLightSentListener lightSentListener) {
        this.service = apiService;
        this.lightSentListener = lightSentListener;
    }

    public void sendLight() {
        SensorUtil.startSensor(Sensor.TYPE_LIGHT, this);
    }


    public void cancel() {
        if (call != null && callback != null) {
            call.cancel();
            callback.cancel();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            SensorUtil.stopSensor(this, Sensor.TYPE_LIGHT);
            String deviceId = Settings.Secure.getString(SenseiApp.getInstance().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            rate = event.values[0];
            call = service.sendLight(rate, Channel.Id.LIGHT, 52.2043513, 0.1174503, "Cambridge", deviceId);
            callback = new BaseCallback<BaseResponse<LightResponse>>() {
                @Override
                public void onUnknownError(@Nullable String error) {
                    lightSentListener.onFail(error);
                }

                @Override
                public void onSuccess(BaseResponse<LightResponse> body, Response<BaseResponse<LightResponse>> response) {
                    lightSentListener.onSent();
                }
            };
            call.enqueue(callback);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}