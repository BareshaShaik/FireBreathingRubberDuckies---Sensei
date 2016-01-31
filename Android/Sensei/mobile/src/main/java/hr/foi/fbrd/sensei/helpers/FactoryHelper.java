package hr.foi.fbrd.sensei.helpers;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Sensor;
import android.location.LocationManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.loginmodule.interactor.impl.LoginInteractorFacebookImpl;
import com.example.loginmodule.service.app.AppServiceImpl;
import com.facebook.CallbackManager;
import com.facebook.internal.CallbackManagerImpl;

import java.util.List;

import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.SenseiApp;
import hr.foi.fbrd.sensei.activities.EventCreateActivity;
import hr.foi.fbrd.sensei.activities.SplashActivity;
import hr.foi.fbrd.sensei.adapters.WizardPagerAdapter;
import hr.foi.fbrd.sensei.helpers.hashtag.HashTagHelper;
import hr.foi.fbrd.sensei.listeners.OnHeartrateSentListener;
import hr.foi.fbrd.sensei.listeners.OnLightSentListener;
import hr.foi.fbrd.sensei.listeners.OnLocationSentListener;
import hr.foi.fbrd.sensei.models.Condition;
import hr.foi.fbrd.sensei.models.EventsResponse;
import hr.foi.fbrd.sensei.mvp.interactor.ChannelDetailsInteractor;
import hr.foi.fbrd.sensei.mvp.interactor.ChannelsInteractor;
import hr.foi.fbrd.sensei.mvp.interactor.DeviceInteractor;
import hr.foi.fbrd.sensei.mvp.interactor.HeartrateInteractor;
import hr.foi.fbrd.sensei.mvp.interactor.LightInteractor;
import hr.foi.fbrd.sensei.mvp.interactor.LocationInteractor;
import hr.foi.fbrd.sensei.mvp.presenter.ChannelDetailsPresenter;
import hr.foi.fbrd.sensei.mvp.presenter.ChannelsPresenter;
import hr.foi.fbrd.sensei.mvp.presenter.EventCreatePresenter;
import hr.foi.fbrd.sensei.mvp.presenter.LoginPresenter;
import hr.foi.fbrd.sensei.mvp.view.ChannelDetailsView;
import hr.foi.fbrd.sensei.mvp.view.ChannelView;
import hr.foi.fbrd.sensei.mvp.view.EventsView;
import hr.foi.fbrd.sensei.viewholders.ConditionViewHolder;
import hr.foi.fbrd.sensei.viewholders.EventItemViewHolder;
import hr.foi.fbrd.sensei.viewholders.EventListMapper;
import hr.foi.fbrd.sensei.viewholders.InputListMapper;
import hr.foi.fbrd.sensei.viewholders.LabelItemViewHolder;
import hr.foi.fbrd.sensei.viewholders.SensorItemViewHolder;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;


public class FactoryHelper {

    private static CallbackManager cb;
    private static AppServiceImpl service;
    private static final String ENDPOINT = "http://cambridgetest.azurewebsites.net/";
//    private static EventDAO eventDAO;

    public static AppServiceImpl getAppServiceImpl() {
        if (service == null) {
            service = new AppServiceImpl(ENDPOINT);
        }
        return service;
    }

    public static CallbackManager getCallbackManager() {
        if (cb == null) {
            cb = new CallbackManagerImpl();
        }
        return cb;
    }

    public static LoginPresenter getPresenter(SplashActivity view) {
        return new LoginPresenter(view,
                new LoginInteractorFacebookImpl(view, getCallbackManager(),
                        getAppServiceImpl().getAppService()), new DeviceInteractor(SenseiApp.getApiService()));
    }

    public static RecyclerMultiAdapter getSensorListAdapter(Context context) {
        return SmartAdapter.empty()
                .map(Sensor.class, SensorItemViewHolder.class)
                .map(String.class, LabelItemViewHolder.class)
                .builder(new InputListMapper(context))
                .recyclerAdapter();
    }

    public static WizardPagerAdapter getWizardPagerAdapter(EventCreateActivity activity,
                                                           List<Fragment> fragmentList) {
        return new WizardPagerAdapter(activity.getFragmentManager(), fragmentList);
    }

    public static EventCreatePresenter getEventCreatePresenter(EventCreateActivity activity) {
        return new EventCreatePresenter(activity);
    }

    public static HashTagHelper getHashTagHelper(Context context) {
        return HashTagHelper.Creator
                .create(context.getResources().getColor(R.color.colorPrimary), hashTag -> {
                });
    }

    public static RecyclerMultiAdapter getConditionsAdapter() {
        return SmartAdapter.empty()
                .map(Condition.class, ConditionViewHolder.class)
                .recyclerAdapter();
    }

    public static MaterialDialog getInputTimeDialog(Activity activity, MaterialDialog.SingleButtonCallback onSubmit) {
        return new MaterialDialog.Builder(activity)
                .title("How frequent?")
                .customView(R.layout.dialog_interval, false)
                .onPositive(onSubmit)
                .positiveText("OK")
                .negativeText("Cancel")
                .build();
    }

    public static MaterialDialog getInputValueDialog(Activity activity) {
        return null;
    }

    public static ChannelsPresenter getPresenter(EventsView view, ChannelView channelView) {
        return new ChannelsPresenter(view, channelView, new ChannelsInteractor(SenseiApp.getApiService()));
    }

    public static RecyclerMultiAdapter getEventsListAdapter(Context context) {
        return SmartAdapter.empty()
                .map(EventsResponse.class, EventItemViewHolder.class)
                .builder(new EventListMapper(context))
                .recyclerAdapter();
    }

    public static ChannelDetailsPresenter getPresenter(ChannelDetailsView view) {
        return new ChannelDetailsPresenter(view, new ChannelDetailsInteractor(SenseiApp.getApiService()));
    }


    public static HeartrateInteractor getHeartRateInteractor(Context baseContext, OnHeartrateSentListener listener, LocationInteractor locationInteractor) {
        return new HeartrateInteractor(
                SenseiApp.getApiService(),
                listener
        );
    }

    public static LocationInteractor getLocationInteractor(Context baseContext, OnLocationSentListener listener) {
        LocationManager manager = (LocationManager) baseContext.getSystemService(Context.LOCATION_SERVICE);
        return new LocationInteractor(
                manager,
                (latitude, longitude) -> {
                },
                listener
        );
    }

    public static LightInteractor getLightInteractor(Context context, LocationInteractor locationInteractor, OnLightSentListener onLightSentListener) {
        return new LightInteractor(
                SenseiApp.getApiService(),
                onLightSentListener
        );
    }
}
