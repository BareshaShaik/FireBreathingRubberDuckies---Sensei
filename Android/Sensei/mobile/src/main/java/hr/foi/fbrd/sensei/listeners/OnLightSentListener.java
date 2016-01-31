package hr.foi.fbrd.sensei.listeners;


public interface OnLightSentListener {

    void onSent();
    void onFail(String error);
}
