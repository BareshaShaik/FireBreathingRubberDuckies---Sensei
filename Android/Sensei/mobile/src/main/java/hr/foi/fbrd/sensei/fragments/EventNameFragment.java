package hr.foi.fbrd.sensei.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.helpers.FactoryHelper;
import hr.foi.fbrd.sensei.helpers.hashtag.HashTagHelper;

public class EventNameFragment extends BaseFragment {

    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.etTags)
    EditText etTags;
    HashTagHelper hashTagHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_name, null, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hashTagHelper = FactoryHelper.getHashTagHelper(getActivity());
        hashTagHelper.handle(etTags);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
