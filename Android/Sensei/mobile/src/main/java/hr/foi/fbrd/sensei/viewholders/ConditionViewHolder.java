package hr.foi.fbrd.sensei.viewholders;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.models.Condition;
import io.nlopez.smartadapters.views.BindableFrameLayout;

public class ConditionViewHolder extends BindableFrameLayout<Condition> {

    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.checkbox)
    CheckBox checkbox;
    @Bind(R.id.label)
    TextView label;

    public ConditionViewHolder(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_condition;
    }

    @Override
    public void onViewInflated() {
        super.onViewInflated();
        ButterKnife.bind(this, this);
    }

    @Override
    public void bind(Condition condition) {
        if (condition.getType() != Condition.Type.MANUAL) {
            label.setText(condition.getValue());
        } else {
            label.setText("Manual");
        }
    }
}
