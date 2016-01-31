package com.dmacan.input.util;

import android.content.Context;
import android.location.LocationManager;

public class LocationUtil {

    private static Context context;
    private static LocationManager locationManager;

    public static void initialize(Context context) {
        LocationUtil.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static boolean isLocationAvailable() {
        return locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)
                && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}
