package hr.foi.fbrd.sensei.viewholders;

import android.content.Context;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.fbrd.sensei.R;
import io.nlopez.smartadapters.views.BindableFrameLayout;

public class LabelItemViewHolder extends BindableFrameLayout<String> {
    @Bind(R.id.lblItem)
    TextView lblItem;

    public LabelItemViewHolder(Context context) {
        super(context);
    }

    @Override
    public void onViewInflated() {
        super.onViewInflated();
        ButterKnife.bind(this, this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_label;
    }

    @Override
    public void bind(String s) {
        lblItem.setText(s);
    }
}
