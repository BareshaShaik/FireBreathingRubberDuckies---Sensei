package hr.foi.fbrd.sensei.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.dmacan.input.Channel;
import com.dmacan.input.util.Mock;

import java.util.List;

import hr.foi.fbrd.sensei.helpers.FactoryHelper;
import hr.foi.fbrd.sensei.listeners.OnHeartrateSentListener;
import hr.foi.fbrd.sensei.listeners.OnLightSentListener;
import hr.foi.fbrd.sensei.listeners.OnLocationSentListener;
import hr.foi.fbrd.sensei.mvp.interactor.HeartrateInteractor;
import hr.foi.fbrd.sensei.mvp.interactor.LightInteractor;
import hr.foi.fbrd.sensei.mvp.interactor.LocationInteractor;
import me.denley.courier.Courier;
import me.denley.courier.ReceiveMessages;

public class MegaService extends Service implements OnLocationSentListener, OnHeartrateSentListener, OnLightSentListener {

    List<Channel> channels;
    HeartrateInteractor hrInteractor;
    LocationInteractor locationInteractor;
    LightInteractor lightInteractor;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Courier.startReceiving(this);
        channels = Mock.allCollections();
        locationInteractor = FactoryHelper.getLocationInteractor(getBaseContext(), this);
        hrInteractor = FactoryHelper.getHeartRateInteractor(getBaseContext(), this, locationInteractor);
        lightInteractor = FactoryHelper.getLightInteractor(getBaseContext(), locationInteractor, this);
    }

    @Override
    public void onDestroy() {
        Courier.stopReceiving(this);
        super.onDestroy();
    }

    @ReceiveMessages("HeartRate")
    protected void onMessage(String message) {
        try {
            Log.i("DAM", "Heart rate: " + message);
            hrInteractor.sendHeartrate(Integer.parseInt(message));
        } catch (Exception e) {
            Log.e("DAM", "Exception occured", e);
        }
//        Observable.defer(() -> Observable.just(message))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(msg -> {
//                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
//                });
    }

    @Override
    public void onHeartrateSent() {
        Log.i("DAM", "Heart rate sent");
        Toast.makeText(MegaService.this, "Heart rate updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailedHeartrateSent(String message) {
        Log.i("DAM", "HR fail: " + message);
    }

    @Override
    public void onSent() {

    }

    @Override
    public void onFail(String error) {

    }

    @Override
    public void onLocationSent() {

    }

    @Override
    public void onFailedLocation(String message) {

    }

    @Override
    public void onLocationForwarded(Location location) {
    }
}
