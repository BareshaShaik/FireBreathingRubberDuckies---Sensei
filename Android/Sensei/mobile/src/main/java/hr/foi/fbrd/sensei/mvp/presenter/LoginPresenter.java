package hr.foi.fbrd.sensei.mvp.presenter;

import android.preference.PreferenceManager;
import android.provider.Settings;

import com.example.loginmodule.interactor.LoginInteractorFacebook;
import com.example.loginmodule.model.bus.ZET;
import com.example.loginmodule.model.event.LoginSuccessEvent;
import com.example.loginmodule.model.event.error.LoginErrorEvent;

import de.halfbit.tinybus.Subscribe;
import hr.foi.fbrd.sensei.SenseiApp;
import hr.foi.fbrd.sensei.activities.SplashActivity;
import hr.foi.fbrd.sensei.helpers.Preferences;
import hr.foi.fbrd.sensei.listeners.Listener;
import hr.foi.fbrd.sensei.models.DeviceModel;
import hr.foi.fbrd.sensei.mvp.interactor.DeviceInteractor;


public class LoginPresenter implements BasePresenter {

    private SplashActivity view;
    private LoginInteractorFacebook interactorFacebook;
    private DeviceInteractor deviceInteractor;

    public LoginPresenter(SplashActivity view, LoginInteractorFacebook loginInteractor, DeviceInteractor deviceInteractor) {
        this.view = view;
        this.interactorFacebook = loginInteractor;
        this.deviceInteractor = deviceInteractor;
    }

    public void facebookLogin() {
        view.showProgress();
        interactorFacebook.login("");
    }

    public void pairDevices() {
        String deviceId = Settings.Secure.getString(SenseiApp.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String lastDevice = PreferenceManager.getDefaultSharedPreferences(SenseiApp.getInstance())
                .getString(deviceId, "");
        if (lastDevice.equals("")) {
            deviceInteractor.pairDevice(listener, deviceId);
        } else {
            view.onDeviceSuccess();
        }

    }

    @Subscribe
    public void onLoginSuccess(LoginSuccessEvent event) {
        Preferences.storeUserSync(event.getUser());
        Preferences.storeTokenSync(event.getToken());
        view.onSuccess("Login success");
       /* Preferences.storeUser(event.getUser())
                .flatMap(result -> Preferences.storeToken(event.getToken()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.i("SenseiApp", "Login success");
                    view.onSuccess("Login success");
                });*/
    }

    @Subscribe
    public void onLoginFailure(LoginErrorEvent event) {
        view.onFailure(String.valueOf(event.getCode()));
    }

    public void onViewResume() {
        ZET.register(this);
    }

    public void onViewPause() {
        ZET.unregister(this);
    }

    private Listener<DeviceModel> listener = new Listener<DeviceModel>() {
        @Override
        public void onSuccess(DeviceModel deviceModel) {
            view.onDeviceSuccess();
        }

        @Override
        public void onFailure(String error) {
            view.onDeviceFailure(error);
        }

        @Override
        public void onConnectionFailure(String message) {

        }
    };

}

