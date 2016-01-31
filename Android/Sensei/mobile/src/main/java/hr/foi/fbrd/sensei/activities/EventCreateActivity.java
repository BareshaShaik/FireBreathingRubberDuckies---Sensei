package hr.foi.fbrd.sensei.activities;

import android.hardware.Sensor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.adapters.WizardPagerAdapter;
import hr.foi.fbrd.sensei.fragments.EventConditionFragment;
import hr.foi.fbrd.sensei.fragments.EventNameFragment;
import hr.foi.fbrd.sensei.fragments.InputListFragment;
import hr.foi.fbrd.sensei.helpers.FactoryHelper;
import hr.foi.fbrd.sensei.helpers.NonSwipeableViewPager;
import hr.foi.fbrd.sensei.listeners.InputSelectedListener;
import hr.foi.fbrd.sensei.mvp.presenter.EventCreatePresenter;

public class EventCreateActivity extends BaseActivity implements ViewPager.OnPageChangeListener, InputSelectedListener {

    @Bind(R.id.btnBack)
    Button btnBack;
    @Bind(R.id.btnNext)
    Button btnNext;
    @Bind(R.id.wizardPager)
    NonSwipeableViewPager wizardPager;
    WizardPagerAdapter adapter;
    InputListFragment inputListFragment;
    EventConditionFragment eventConditionFragment;
    EventCreatePresenter presenter;
    EventNameFragment eventNameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);
        ButterKnife.bind(this);
        presenter = FactoryHelper.getEventCreatePresenter(this);
        initFragments();
        presenter.onCreate();
    }

    private void initFragments() {
        inputListFragment = new InputListFragment();
        inputListFragment.setInputSelectedListener(this);
        eventConditionFragment = new EventConditionFragment();
        eventNameFragment = new EventNameFragment();
    }

    public void setInputs(List<Sensor> inputs) {
        inputListFragment.setSensors(inputs);
    }

    public void showSteps() {
        adapter = FactoryHelper.getWizardPagerAdapter(this, Arrays.asList(
                inputListFragment,
                eventConditionFragment,
                eventNameFragment
        ));
        wizardPager.setOffscreenPageLimit(3);
        wizardPager.setAdapter(adapter);
        onPageSelected(0);
    }

    @OnClick(R.id.btnNext)
    protected void onNext() {
        presenter.onNext();
    }

    @OnClick(R.id.btnBack)
    protected void onBack() {
        presenter.onBack();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        btnBack.setText("Back");
        btnNext.setText("Next");
        btnBack.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.VISIBLE);
        if (position == 0) { // First item
            btnBack.setText("Cancel");
            btnNext.setVisibility(View.GONE);
            setTitle("Pick your onInput trigger");
        } else if (position == adapter.getCount() - 1) {// Last item
            setTitle("Name your event");
            btnNext.setText("Finish");
        } else {
            setTitle("Choose your terms");
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onInputSelected(Object input) {
        Toast.makeText(EventCreateActivity.this, ((Sensor) input).getName(), Toast.LENGTH_SHORT).show();
        presenter.onNext();
    }

    public void swipeNext() {
        if (wizardPager.getCurrentItem() < adapter.getCount() - 1) {
            int position = wizardPager.getCurrentItem() + 1;
            wizardPager.setCurrentItem(position);
            onPageSelected(position);
        }
    }

    public void swipePrevious() {
        if (wizardPager.getCurrentItem() > 0) {
            int position = wizardPager.getCurrentItem() - 1;
            wizardPager.setCurrentItem(position);
            onPageSelected(position);
        }
    }
}
