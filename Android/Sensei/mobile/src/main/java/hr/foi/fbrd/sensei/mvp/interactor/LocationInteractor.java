package hr.foi.fbrd.sensei.mvp.interactor;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.dmacan.input.Channel;

import hr.foi.fbrd.sensei.SenseiApp;
import hr.foi.fbrd.sensei.listeners.OnLocationChangeListener;
import hr.foi.fbrd.sensei.listeners.OnLocationSentListener;
import hr.foi.fbrd.sensei.models.BaseResponse;
import hr.foi.fbrd.sensei.models.LocationResponse;
import hr.foi.fbrd.sensei.network.ApiService;
import hr.foi.fbrd.sensei.network.BaseCallback;
import retrofit.Call;
import retrofit.Response;


public class LocationInteractor {

    private LocationManager manager;
    private OnLocationChangeListener listener;
    private ApiService apiService;
    private Call<BaseResponse<LocationResponse>> call;
    private BaseCallback<BaseResponse<LocationResponse>> callback;
    private OnLocationSentListener locationSentListener;
    private boolean isNative = true;

    public LocationInteractor(LocationManager manager, OnLocationChangeListener listener, OnLocationSentListener locationSentListener) {
        this.manager = manager;
        this.listener = listener;
        this.locationSentListener = locationSentListener;
        apiService = SenseiApp.getApiService();
    }

    //TODO posalji true ako ti treba samo lokacija, a false ako ti treba lokacija za neki drugi kurac (heartrate i sl.)
    public void getLocation(boolean isNative) {
        this.isNative = isNative;
        if (ActivityCompat.checkSelfPermission(SenseiApp.getInstance(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SenseiApp.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //treci argument u requestlocationupdates bi mogo raditi sranja
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, locationListener);
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (ActivityCompat.checkSelfPermission(SenseiApp.getInstance(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SenseiApp.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
            manager.removeUpdates(locationListener);
            listener.onLocationChange(location.getLatitude(), location.getLongitude());
            if (isNative) {
                sendLocationToApi(location);
            } else {
                locationSentListener.onLocationForwarded(location);
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void sendLocationToApi(Location location) {

        String deviceId = Settings.Secure.getString(SenseiApp.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        call = apiService.sendLocation(Channel.Id.LOCATION, location.getLatitude(), location.getLongitude(), deviceId);

        callback = new BaseCallback<BaseResponse<LocationResponse>>() {
            @Override
            public void onUnknownError(@Nullable String error) {
                locationSentListener.onFailedLocation(error);
            }

            @Override
            public void onSuccess(BaseResponse<LocationResponse> body, Response<BaseResponse<LocationResponse>> response) {
                locationSentListener.onLocationSent();
            }
        };

        call.enqueue(callback);
    }
}
