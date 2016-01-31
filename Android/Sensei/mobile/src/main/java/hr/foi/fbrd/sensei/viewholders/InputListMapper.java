package hr.foi.fbrd.sensei.viewholders;

import android.content.Context;
import android.hardware.Sensor;
import android.support.annotation.NonNull;

import io.nlopez.smartadapters.builders.DefaultBindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.views.BindableLayout;

public class InputListMapper extends DefaultBindableLayoutBuilder {

    private Context context;

    public InputListMapper(Context context) {
        this.context = context;
    }

    @Override
    public Class<? extends BindableLayout> viewType(@NonNull Object item, int position, @NonNull Mapper mapper) {
        if (item instanceof String) {
            return LabelItemViewHolder.class;
        } else if (item instanceof Sensor) {
            return SensorItemViewHolder.class;
        }
        return super.viewType(item, position, mapper);
    }

    @Override
    public boolean allowsMultimapping() {
        return true;
    }
}
