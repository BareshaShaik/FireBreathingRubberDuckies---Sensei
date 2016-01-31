package hr.foi.fbrd.sensei.mvp.interactor;

import android.support.annotation.Nullable;

import com.dmacan.input.ChannelJoinResponse;

import java.util.ArrayList;

import hr.foi.fbrd.sensei.helpers.Preferences;
import hr.foi.fbrd.sensei.listeners.Listener;
import hr.foi.fbrd.sensei.models.BaseResponse;
import hr.foi.fbrd.sensei.network.ApiService;
import hr.foi.fbrd.sensei.network.BaseCallback;
import retrofit.Call;
import retrofit.Response;

public class ChannelsInteractor {

    private ApiService service;
    private Call<BaseResponse<ChannelJoinResponse>> call;
    private BaseCallback<BaseResponse<ChannelJoinResponse>> callback;

    public ChannelsInteractor(ApiService apiService) {
        this.service = apiService;

    }

    public void cancel() {
        if (call != null && callback != null) {
            call.cancel();
            callback.cancel();
        }
    }

    public void joinAChannel(int id, Listener<ArrayList<ChannelJoinResponse>> listener) {
        String token = Preferences.loadTokenSync();
        token = "Bearer " + token;

        call = service.joinChannel(token, id, 0, 0);

        callback = new BaseCallback<BaseResponse<ChannelJoinResponse>>() {
            @Override
            public void onUnknownError(@Nullable String error) {
                listener.onFailure(error);
            }

            @Override
            public void onSuccess(BaseResponse<ChannelJoinResponse> body, Response<BaseResponse<ChannelJoinResponse>> response) {
                listener.onSuccess(body.getMessage());
            }
        };
        call.enqueue(callback);
    }
}