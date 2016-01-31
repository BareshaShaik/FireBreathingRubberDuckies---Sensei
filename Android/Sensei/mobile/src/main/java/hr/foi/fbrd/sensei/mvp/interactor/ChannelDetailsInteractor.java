package hr.foi.fbrd.sensei.mvp.interactor;

import android.support.annotation.Nullable;

import com.dmacan.input.Channel;
import com.dmacan.input.ChannelDetails;

import java.util.ArrayList;

import hr.foi.fbrd.sensei.helpers.Preferences;
import hr.foi.fbrd.sensei.listeners.Listener;
import hr.foi.fbrd.sensei.models.BaseResponse;
import hr.foi.fbrd.sensei.network.ApiService;
import hr.foi.fbrd.sensei.network.BaseCallback;
import retrofit.Call;
import retrofit.Response;

public class ChannelDetailsInteractor {

    private ApiService service;
    private Call<BaseResponse<ChannelDetails>> call;
    private BaseCallback<BaseResponse<ChannelDetails>> callback;

    public ChannelDetailsInteractor(ApiService apiService) {
        this.service = apiService;
    }

    public void getChannelData(final Listener<ArrayList<ChannelDetails>> listener, Channel channel) {

        String token = Preferences.loadTokenSync();
        token = "Bearer " + token;
        call = service.getMyChannels(token);

        callback = new BaseCallback<BaseResponse<ChannelDetails>>() {
            @Override
            public void onUnknownError(@Nullable String error) {
                listener.onFailure(error);
            }

            @Override
            public void onSuccess(BaseResponse<ChannelDetails> body, Response<BaseResponse<ChannelDetails>> response) {
                listener.onSuccess(body.getMessage());
            }
        };

        call.enqueue(callback);
    }

    public void cancel() {
        if (call != null && callback != null) {
            call.cancel();
            callback.cancel();
        }
    }
}