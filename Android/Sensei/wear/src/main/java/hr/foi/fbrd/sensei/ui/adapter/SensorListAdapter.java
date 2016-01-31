package hr.foi.fbrd.sensei.ui.adapter;

import android.content.Context;
import android.hardware.Sensor;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmacan.input.Channel;

import java.util.List;

import hr.foi.fbrd.sensei.R;

public final class SensorListAdapter extends WearableListView.Adapter {

    private final List<Channel> dataSet;
    private final Context context;
    private final LayoutInflater layoutInflater;

    public SensorListAdapter(Context context, List<Channel> dataSet) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
    }


    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(layoutInflater.inflate(R.layout.item_sensor, null));
    }

    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
        ItemViewHolder ivh = (ItemViewHolder) holder;
        Channel channel = dataSet.get(position);
        if (channel.getId() == Channel.Id.HEART_RATE) {
            ivh.imgIcon.setImageResource(R.drawable.heart);
        }
        ivh.txtLabel.setText(channel.getName());
        holder.itemView.setTag(channel);
    }

    private int pickIcon(int sensorType) {
        switch (sensorType) {
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                return R.drawable.ic_temperature;
            case Sensor.TYPE_GRAVITY:
                return R.drawable.ic_apple;
            case Sensor.TYPE_PRESSURE:
                return R.drawable.ic_pressure;
            case Sensor.TYPE_LIGHT:
                return R.drawable.ic_light;
            case Sensor.TYPE_SIGNIFICANT_MOTION:
                return R.drawable.ic_motion;
            case Sensor.TYPE_STEP_COUNTER:
            case Sensor.TYPE_STEP_DETECTOR:
                return R.drawable.ic_steps;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                return R.drawable.ic_humidity;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    // Provide a reference to the type of views you're using
    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView txtLabel;
        private ImageView imgIcon;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // find the text view within the custom item's layout
            imgIcon = (ImageView) itemView.findViewById(R.id.icon);
            txtLabel = (TextView) itemView.findViewById(R.id.label);
        }
    }
}
