package hr.foi.fbrd.sensei.listeners;

import android.location.Location;

public interface OnLocationSentListener {

    void onLocationSent();
    void onFailedLocation(String message);
    void onLocationForwarded(Location location);
}
