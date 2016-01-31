package hr.foi.fbrd.sensei.network;

import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit.Callback;
import retrofit.Retrofit;

public abstract class BaseCallback<Response> implements Callback<Response> {

    private volatile boolean isCanceled;

    @Override
    public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
        if (isCanceled) {
            return;
        }
        if (response.isSuccess()) {
            if (response.code() == HttpURLConnection.HTTP_NO_CONTENT) {
                onNoContent(response.body(), response);
            } else {
                onSuccess(response.body(), response);
            }
        } else {
            int statusCode = response.code();
            ResponseBody errorBody = response.errorBody();
            failure(errorBody, statusCode);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (isCanceled) {
            return;
        }
        onUnknownError(t != null ? t.getMessage() : null);
    }

    private void failure(ResponseBody cause, int statusCode) {
        if (isCanceled) {
            return;
        }
        String error = null;
        try {
            error = cause.string();
        } catch (IOException e) {
            Log.e(e.toString(), e.getMessage());
        }

        try {
            if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                onUnauthorized(error);
            } else if (statusCode == HttpURLConnection.HTTP_FORBIDDEN) {
                onForbidden(error);
            } else if (statusCode == HttpURLConnection.HTTP_NOT_FOUND) {
                onNotFound(error);
            } else {
                onUnknownError(error);
            }
        } catch (Exception e) {
            onUnknownError(error);
        }
    }

    public void cancel() {
        isCanceled = true;
    }

    public void reset() {
        isCanceled = false;
    }

    public abstract void onUnknownError(@Nullable String error);

    private void onNotFound(String error) {
        onUnknownError(error);
    }

    private void onNoContent(Response body, retrofit.Response<Response> response) {
        onSuccess(body, response);
    }

    private void onForbidden(String error) {
        onUnknownError(error);
    }

    private void onUnauthorized(String error) {
        onUnknownError(error);
    }

    public abstract void onSuccess(Response body, retrofit.Response<Response> response);
}
