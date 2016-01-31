package hr.foi.fbrd.sensei.mvp.interactor;

import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import hr.foi.fbrd.sensei.SenseiApp;
import hr.foi.fbrd.sensei.helpers.Preferences;
import hr.foi.fbrd.sensei.listeners.Listener;
import hr.foi.fbrd.sensei.models.BaseResponse;
import hr.foi.fbrd.sensei.models.DeviceModel;
import hr.foi.fbrd.sensei.network.ApiService;
import hr.foi.fbrd.sensei.network.BaseCallback;
import retrofit.Call;
import retrofit.Response;

public class DeviceInteractor {

    public static final String DEVICE_ID = "deviceId";
    private ApiService service;
    private Call<BaseResponse<DeviceModel>> call;
    private BaseCallback<BaseResponse<DeviceModel>> callback;

    public DeviceInteractor(ApiService apiService) {
        this.service = apiService;
    }

    public void pairDevice(Listener<DeviceModel> listener, String deviceId) {
        Preferences.loadToken().subscribe(
                s -> {
                    s = "Bearer " + s;
                    Log.e("TOKEN", String.valueOf(s));
                    String model = Build.MODEL;
                    String manufacturer = Build.MANUFACTURER;

                    String name = model + " " + manufacturer;
                    Log.e("NAME", name);
                    PreferenceManager.getDefaultSharedPreferences(SenseiApp.getInstance())
                            .edit().putString(deviceId, deviceId).apply();

                    call = service.sendDeviceData(s, deviceId, name, 2);

                    callback = new BaseCallback<BaseResponse<DeviceModel>>() {
                        @Override
                        public void onUnknownError(@Nullable String error) {
                            listener.onFailure(error);
                        }

                        @Override
                        public void onSuccess(BaseResponse<DeviceModel> body, Response<BaseResponse<DeviceModel>> response) {
                            listener.onSuccess(body.getMessage().get(0));
                        }
                    };
                    call.enqueue(callback);
                }
        );


    }

    public void cancel() {
        if (call != null && callback != null) {
            call.cancel();
            callback.cancel();
        }
    }
}