package hr.foi.fbrd.sensei.ui.view;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.fbrd.sensei.R;

public class SensorItemView extends LinearLayout implements WearableListView.OnCenterProximityListener {

    @Bind(R.id.icon)
    ImageView imgIcon;
    @Bind(R.id.label)
    TextView txtLabel;

    public SensorItemView(Context context) {
        super(context);
    }

    public SensorItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SensorItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SensorItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    @Override
    public void onCenterPosition(boolean b) {

    }

    @Override
    public void onNonCenterPosition(boolean b) {

    }
}
