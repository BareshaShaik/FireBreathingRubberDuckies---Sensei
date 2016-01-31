package hr.foi.fbrd.sensei.viewholders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.nlopez.smartadapters.builders.BindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.views.BindableLayout;

public class EventListMapper implements BindableLayoutBuilder {


    public EventListMapper(Context context) {
    }

    @Override
    public View build(ViewGroup viewGroup, int i, Object o, Mapper mapper) {
        return null;
    }

    @Override
    public Class<? extends BindableLayout> viewType(Object o, int i, Mapper mapper) {
        return null;
    }

    @Override
    public boolean allowsMultimapping() {
        return false;
    }
}
