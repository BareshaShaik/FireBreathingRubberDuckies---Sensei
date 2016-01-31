package hr.foi.fbrd.sensei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;

import com.dmacan.input.Channel;
import com.dmacan.input.util.FitUtil;
import com.dmacan.input.util.Mock;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.fbrd.sensei.ui.adapter.SensorListAdapter;

public class MainActivity extends Activity {

    private static final String PATH_SENSOR_PICKED = "/sensor/picked";

    @Bind(R.id.listSensors)
    WearableListView listSensors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FitUtil.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        startService(new Intent(this, DumbService.class));
        listSensors.setAdapter(new SensorListAdapter(this, Mock.wearCollection()));
        listSensors.setClickListener(new WearableListView.ClickListener() {
            @Override
            public void onClick(WearableListView.ViewHolder viewHolder) {
                Channel channel = (Channel) viewHolder.itemView.getTag();
                if (channel.getId() == Channel.Id.HEART_RATE) {
                    startActivity(new Intent(getBaseContext(), HeartRateActivity.class));
                }
                finish();
            }

            @Override
            public void onTopEmptyRegionClick() {

            }
        });
    }

}