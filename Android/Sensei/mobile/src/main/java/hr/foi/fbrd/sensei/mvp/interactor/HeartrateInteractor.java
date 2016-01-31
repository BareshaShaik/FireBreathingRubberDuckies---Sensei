package hr.foi.fbrd.sensei.mvp.interactor;

import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.dmacan.input.Channel;
import com.dmacan.input.HeartrateResponse;

import hr.foi.fbrd.sensei.SenseiApp;
import hr.foi.fbrd.sensei.listeners.OnHeartrateSentListener;
import hr.foi.fbrd.sensei.models.BaseResponse;
import hr.foi.fbrd.sensei.network.ApiService;
import hr.foi.fbrd.sensei.network.BaseCallback;
import retrofit.Call;
import retrofit.Response;


public class HeartrateInteractor {

    public static final String DEVICE_ID = "deviceId";
    private ApiService apiService;
    private Call<BaseResponse<HeartrateResponse>> call;
    private BaseCallback<BaseResponse<HeartrateResponse>> callback;
    private OnHeartrateSentListener heartrateSentListener;

    public HeartrateInteractor(ApiService apiService, OnHeartrateSentListener heartrateSentListener) {
        this.apiService = apiService;
        this.heartrateSentListener = heartrateSentListener;
    }

    public void sendHeartrate(int rate) {
        String deviceId = Settings.Secure.getString(SenseiApp.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        call = apiService.sendHeartrate(Channel.Id.HEART_RATE, rate, 52.2043513, 0.1174503,
                "Cambridge", deviceId);
        callback = new BaseCallback<BaseResponse<HeartrateResponse>>() {
            @Override
            public void onUnknownError(@Nullable String error) {
                heartrateSentListener.onFailedHeartrateSent(error);
            }

            @Override
            public void onSuccess(BaseResponse<HeartrateResponse> body, Response<BaseResponse<HeartrateResponse>> response) {
                heartrateSentListener.onHeartrateSent();
            }
        };
        call.enqueue(callback);
    }

}
