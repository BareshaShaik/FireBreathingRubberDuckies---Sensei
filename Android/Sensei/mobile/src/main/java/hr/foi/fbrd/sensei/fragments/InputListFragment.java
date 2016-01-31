package hr.foi.fbrd.sensei.fragments;

import android.hardware.Sensor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.helpers.FactoryHelper;
import hr.foi.fbrd.sensei.listeners.InputSelectedListener;
import hr.foi.fbrd.sensei.viewholders.SensorItemViewHolder;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;


public class InputListFragment extends BaseFragment {

    @Bind(R.id.listSensors)
    RecyclerView listSensors;
    RecyclerMultiAdapter adapter;
    List<Sensor> sensors;
    InputSelectedListener inputSelectedListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_list, null, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = FactoryHelper.getSensorListAdapter(getActivity());
        listSensors.setLayoutManager(new LinearLayoutManager(getActivity()));
        listSensors.setAdapter(adapter);
        adapter.setViewEventListener((i, o, i1, view1) -> {
            Log.i("DAM", "View Event");
            if (inputSelectedListener != null && i == SensorItemViewHolder.ACTION_SELECTED) {
                inputSelectedListener.onInputSelected(o);
            }
        });
        showSensors();
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    private void showSensors() {
        adapter.addItem("Sensors");
        adapter.addItems(sensors);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setInputSelectedListener(InputSelectedListener inputSelectedListener) {
        this.inputSelectedListener = inputSelectedListener;
    }
}
