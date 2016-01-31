package hr.foi.fbrd.sensei.viewholders;

import android.content.Context;
import android.hardware.Sensor;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.helpers.IconHelper;
import io.nlopez.smartadapters.views.BindableFrameLayout;

public class SensorItemViewHolder extends BindableFrameLayout<Sensor> implements View.OnClickListener {

    public static final int ACTION_SELECTED = 3003;

    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.label)
    TextView label;
    @Bind(R.id.container)
    View layout;

    public SensorItemViewHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_sensor;
    }

    @Override
    public void onViewInflated() {
        super.onViewInflated();
        ButterKnife.bind(this, this);
    }

    @Override
    public void bind(Sensor sensor) {
        setOnClickListener(v -> {
            notifyItemAction(ACTION_SELECTED, sensor, this);
        });
        label.setText(sensor.getName());
        icon.setImageResource(IconHelper.pickIcon(sensor));
    }

    @Override
    public void onClick(View v) {
        Log.i("DAM", "Click");
        notifyItemAction(ACTION_SELECTED);
    }
}
