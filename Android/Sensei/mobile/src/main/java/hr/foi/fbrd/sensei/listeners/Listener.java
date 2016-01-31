package hr.foi.fbrd.sensei.listeners;


public interface Listener<Type> {

    void onSuccess(Type type);

    void onFailure(String error);

    //applies to the cases where internet is not accessible or not turned on
    //or for some hardware reasons connection to the internet could not be established
    void onConnectionFailure(String message);
}
