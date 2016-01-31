package hr.foi.fbrd.sensei.mvp.presenter;

import hr.foi.fbrd.sensei.activities.EventCreateActivity;
import hr.foi.fbrd.sensors.SensorUtil;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by david on 24.1.2016..
 */
public class EventCreatePresenter implements BasePresenter {

    private EventCreateActivity view;

    public EventCreatePresenter(EventCreateActivity view) {
        this.view = view;
    }

    public void onCreate() {
        Observable.defer(() -> Observable.just(SensorUtil.getCompatibleSensors()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sensors -> {
                    view.setInputs(sensors);
                    view.showSteps();
                });
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onBack() {
        view.swipePrevious();
    }

    public void onNext() {
        view.swipeNext();
    }
}
