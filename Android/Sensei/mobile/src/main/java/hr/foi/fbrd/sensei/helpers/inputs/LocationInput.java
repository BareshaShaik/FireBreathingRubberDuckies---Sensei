package hr.foi.fbrd.sensei.helpers.inputs;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import hr.foi.fbrd.sensei.SenseiApp;


public class LocationInput extends BroadcastReceiver {

    private boolean manual = false;
    private long period;
    private OnResultListener listener;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private LocationManager manager;

    public LocationInput(OnResultListener listener) {
        this.listener = listener;
        manager = (LocationManager) SenseiApp.getInstance().getSystemService(Context.LOCATION_SERVICE);
    }

    public LocationInput() {
        //default constructor, required for broadcast reciever
    }

    public void setParams(boolean manual, long period) {
        this.manual = manual;
        this.period = period;
    }

    public void setLocationInput() {
        if(!manual){
            alarmManager = (AlarmManager) SenseiApp.getInstance().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(SenseiApp.getInstance(), LocationInput.class);

            pendingIntent = PendingIntent.getBroadcast(SenseiApp.getInstance(), 0, intent, 0);
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, period, period, pendingIntent);
        }
    }

    public void triggerLocationNow(){
        if(manual){
            getLocation();
        }
    }

    private void getLocation() {
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(SenseiApp.getInstance(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SenseiApp.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 5, locationListener);
                //treci argument u requestlocationupdates bi mogo raditi sranja
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(SenseiApp.getInstance(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SenseiApp.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200, 5, locationListener);
                //treci argument u requestlocationupdates bi mogo raditi sranja
            }
        }
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double[] coords = {location.getLongitude(), location.getLatitude()};

            listener.onInput(coords);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
