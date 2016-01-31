package hr.foi.fbrd.sensei.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.loginmodule.model.entity.User;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class Preferences {

    private static final String DB = "com.fbrd.jedno";
    private static final String KEY_USER = "user";
    private static final String KEY_TOKEN = "token";

    private static Context context;
    private static SharedPreferences preferences;

    public static void initialize(Context context) {
        Preferences.context = context;
        preferences = context.getSharedPreferences(DB, Context.MODE_PRIVATE);
    }

    public static Observable<Boolean> storeUser(User user) {
        return Observable.defer(() -> Observable.just(Serializator.serialize(user)))
                .map(data -> preferences.edit().putString(KEY_USER, data).commit())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Boolean> storeToken(String token) {
        return Observable.defer(() -> Observable.just(token))
                .map(data -> preferences.edit().putString(KEY_TOKEN, data).commit())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<User> loadUser() {
        return Observable.defer(() -> Observable.just(
                Serializator.deserialize(preferences.getString(KEY_USER, null), User.class)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<String> loadToken() {
        return Observable.defer(() -> Observable.just(preferences.getString(KEY_TOKEN, null)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Boolean> isUserStored() {
        return loadUser()
                .map(data -> data != null && data.getEmail() != null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void storeUserSync(User user) {
        String json = Serializator.serialize(user);
        preferences.edit().putString(KEY_USER, json).commit();
    }

    public static void storeTokenSync(String token) {
        preferences.edit().putString(KEY_TOKEN, token).commit();
    }

    public static Observable<Boolean> clear() {
        return Observable.defer(() -> Observable.just(preferences.edit().clear().commit()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static String loadTokenSync(){
        return preferences.getString("token", null);
    }
}
