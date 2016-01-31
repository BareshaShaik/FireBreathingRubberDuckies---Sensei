package hr.foi.fbrd.sensei.mvp.interactor;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import hr.foi.fbrd.sensei.listeners.Listener;
import hr.foi.fbrd.sensei.models.BaseResponse;
import hr.foi.fbrd.sensei.models.EventsResponse;
import hr.foi.fbrd.sensei.network.ApiService;
import hr.foi.fbrd.sensei.network.BaseCallback;
import retrofit.Call;
import retrofit.Response;


public class EventsInteractor {

    public static final String TOKEN = "token";
    private ApiService apiService;
    private Call<BaseResponse<EventsResponse>> call;
    private BaseCallback<BaseResponse<EventsResponse>> baseCallback;
    private Context context;

    public EventsInteractor(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
    }

    public void getAllEvents(Listener<ArrayList<EventsResponse>> listener){
        String token = PreferenceManager.getDefaultSharedPreferences(context).getString(TOKEN, "");
        call = apiService.getAllEvents(token);

        baseCallback = new BaseCallback<BaseResponse<EventsResponse>>() {
            @Override
            public void onUnknownError(@Nullable String error) {
                listener.onFailure(error);
            }

            @Override
            public void onSuccess(BaseResponse<EventsResponse> body, Response<BaseResponse<EventsResponse>> response) {
                listener.onSuccess(body.getMessage());
            }
        };

        call.enqueue(baseCallback);
    }
}
