package hr.foi.fbrd.sensei;

import android.app.Application;

import com.facebook.FacebookSdk;

import hr.foi.fbrd.sensei.helpers.Preferences;
import hr.foi.fbrd.sensei.network.ApiManager;
import hr.foi.fbrd.sensei.network.ApiService;
import hr.foi.fbrd.sensors.SensorUtil;

public class SenseiApp extends Application {

    private static SenseiApp instance;
    protected ApiManager apiManager;

    public static SenseiApp getInstance() {
        return instance;
    }

    public void setInstance(SenseiApp instance) {
        SenseiApp.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
        Preferences.initialize(this);
        SensorUtil.initialize(this);
        init();
    }

    private void init() {
        setInstance(this);
        ApiManager.getInstance().setup();
        apiManager = ApiManager.getInstance();
    }

    public static ApiService getApiService() {
        return getInstance().apiManager.getService();
    }
}
