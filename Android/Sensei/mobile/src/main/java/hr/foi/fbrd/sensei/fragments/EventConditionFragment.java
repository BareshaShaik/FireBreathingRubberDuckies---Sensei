package hr.foi.fbrd.sensei.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.helpers.FactoryHelper;
import hr.foi.fbrd.sensei.models.Condition;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;


public class EventConditionFragment extends BaseFragment {

    @Bind(R.id.listConditions)
    RecyclerView listConditions;
    @Bind(R.id.fabMenu)
    FloatingActionMenu fabMenu;
    RecyclerMultiAdapter adapter;
    MaterialDialog intervalDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_condition, null, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = FactoryHelper.getConditionsAdapter();
        listConditions.setLayoutManager(new LinearLayoutManager(getActivity()));
        listConditions.setAdapter(adapter);
        intervalDialog = FactoryHelper.getInputTimeDialog(getActivity(), (dialog, which) -> {
            String hours = ((EditText) dialog.findViewById(R.id.etHours)).getText().toString();
            String minutes = ((EditText) dialog.findViewById(R.id.etMinutes)).getText().toString();
            String seconds = ((EditText) dialog.findViewById(R.id.etSeconds)).getText().toString();
            String time = hours + ":" + minutes + ":" + seconds;
            Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT)
                    .show();
            Condition condition = new Condition(Condition.Type.INTERVAL);
            condition.setBaseValue(time);
            adapter.addItem(condition);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.fabInterval)
    protected void onIntervalSelected() {
        fabMenu.close(true);
        intervalDialog.show();
    }

    @OnClick(R.id.fabManual)
    protected void onManualSelected() {
        fabMenu.close(true);
        adapter.addItem(new Condition(Condition.Type.MANUAL));
    }

    @OnClick(R.id.fabValue)
    protected void onValueSelected() {
        fabMenu.close(true);
    }
}
