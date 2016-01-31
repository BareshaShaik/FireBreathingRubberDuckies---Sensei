package hr.foi.fbrd.sensei;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.TextView;

import com.dmacan.input.listener.OnValueListener;
import com.dmacan.input.manager.HeartRateManager;
import com.leo.simplearcloader.SimpleArcLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.denley.courier.Courier;

public class HeartRateActivity extends WearableActivity implements OnValueListener<Integer> {

    @Bind(R.id.loader)
    SimpleArcLoader loader;
    @Bind(R.id.container)
    BoxInsetLayout container;
    @Bind(R.id.label)
    TextView label;
    @Bind(R.id.value)
    TextView txtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);
        ButterKnife.bind(this);
        HeartRateManager heartRateManager = new HeartRateManager(this);
        heartRateManager.checkHeartRate(this);
    }


    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
    }

    @Override
    public void onExitAmbient() {
        super.onExitAmbient();
    }

    @Override
    public void onValue(Integer value) {
        txtValue.setText("" + value);
        txtValue.setVisibility(View.VISIBLE);
        loader.stop();
        loader.setVisibility(View.INVISIBLE);
        label.setVisibility(View.INVISIBLE);
        Courier.deliverMessage(this, "HeartRate", "" + value);
    }
}
