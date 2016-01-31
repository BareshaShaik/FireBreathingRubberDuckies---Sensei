package hr.foi.fbrd.sensei;

import android.content.Intent;

import com.google.android.gms.wearable.WearableListenerService;

import me.denley.courier.Courier;
import me.denley.courier.ReceiveMessages;

public class DumbService extends WearableListenerService {

    public DumbService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Courier.startReceiving(this);
    }

    @Override
    public void onDestroy() {
        Courier.stopReceiving(this);
        super.onDestroy();
    }

    @ReceiveMessages("HeartMessage")
    public void onHeartMessage(String msg) {
        Intent heartRate = new Intent(this, HeartRateActivity.class);
        heartRate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(heartRate);
    }
}
