package hr.foi.fbrd.sensei.viewholders;

import android.content.Context;
import android.view.View;

import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.models.EventsResponse;
import io.nlopez.smartadapters.utils.ViewEventListener;
import io.nlopez.smartadapters.views.BindableRelativeLayout;

public class EventItemViewHolder extends BindableRelativeLayout<EventsResponse> implements View.OnClickListener {

    public static final int ACTION_SELECTED = 3003;

    public EventItemViewHolder(Context context) {
        super(context);
    }


    @Override
    public void setViewEventListener(ViewEventListener viewEventListener) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_event;
    }

    @Override
    public void bind(EventsResponse eventsResponse) {

    }

    @Override
    public ViewEventListener getViewEventListener() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}
