package hr.foi.fbrd.sensei.activities;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dmacan.input.Channel;
import com.dmacan.input.ChannelJoinResponse;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.SenseiApp;
import hr.foi.fbrd.sensei.adapters.ChannelsListAdapter;
import hr.foi.fbrd.sensei.helpers.FactoryHelper;
import hr.foi.fbrd.sensei.helpers.Preferences;
import hr.foi.fbrd.sensei.listeners.OnChannelClickListener;
import hr.foi.fbrd.sensei.listeners.OnChannelsClickListener;
import hr.foi.fbrd.sensei.listeners.OnDataSendListener;
import hr.foi.fbrd.sensei.listeners.OnLightSentListener;
import hr.foi.fbrd.sensei.listeners.OnLocationSentListener;
import hr.foi.fbrd.sensei.mvp.interactor.LightInteractor;
import hr.foi.fbrd.sensei.mvp.presenter.ChannelsPresenter;
import hr.foi.fbrd.sensei.mvp.view.ChannelView;
import hr.foi.fbrd.sensei.mvp.view.EventsView;
import hr.foi.fbrd.sensei.services.MegaService;
import me.denley.courier.Courier;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements EventsView, OnChannelsClickListener, ChannelView, OnDataSendListener, OnChannelClickListener {

    public static final String CHANNEL = "channel";
    @Bind(R.id.events_recycler)
    RecyclerView eventsRecycler;
    @Bind(R.id.loader)
    SimpleArcLoader loader;

    private ChannelsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        startService(new Intent(this, MegaService.class));
        presenter = FactoryHelper.getPresenter(this, this);
        eventsRecycler.setLayoutManager(new LinearLayoutManager(this));
        showProgress();
        presenter.getAllEvents();
    }

    private void handleNewEventClick() {
        startActivity(new Intent(this, EventCreateActivity.class));
    }

    @Override
    public void onFailure(String message) {
        hideProgress();
        Log.e("EVENTS", message);
    }

    @Override
    public void onSuccess(ArrayList<ChannelJoinResponse> response) {
        Toast.makeText(MainActivity.this, "Joined a channel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(List<Channel> channels) {
        hideProgress();
        ArrayList<Channel> joinableChannels = new ArrayList<>();
        if (channels != null) {
            eventsRecycler.setLayoutManager(new GridLayoutManager(this, 1));
            for (int i = 0; i < channels.size(); i++) {
                if (channels.get(i).getId() == 1 || channels.get(i).getId() == 4 || channels.get(i).getId() == 5) {
                    joinableChannels.add(channels.get(i));
                }
            }
            for (int i = 0; i < channels.size(); i++) {
                if(!(channels.get(i).getId() == 1 || channels.get(i).getId() == 4 || channels.get(i).getId() == 5)){
                    joinableChannels.add(channels.get(i));
                }
            }
            eventsRecycler.setAdapter(new ChannelsListAdapter(joinableChannels, this, this, this));
        }
    }

    @Override
    public void showProgress() {
        if (!loader.isRunning()) {
            loader.start();
            loader.setVisibility(View.VISIBLE);
            eventsRecycler.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        Observable.defer(() -> Observable.just(loader.isRunning()))
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(running -> {
                    if (running) {
                        loader.stop();
                        loader.setVisibility(View.GONE);
                        eventsRecycler.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, MegaService.class));
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Preferences.clear().subscribe(success -> {
                    finish();
                    startActivity(new Intent(getBaseContext(), SplashActivity.class));
                });
                return true;
            case R.id.light:
                updateLight();
                return true;
            case R.id.location:
                updateLocation();
                return true;
            case R.id.heart:
                updateHeartRate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateLight() {
        new LightInteractor(SenseiApp.getApiService(), new OnLightSentListener() {
            @Override
            public void onSent() {
                Toast.makeText(MainActivity.this, "Light updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String error) {

            }
        }).sendLight();
    }

    private void updateLocation() {
        Log.e("clickListener", "click");
        FactoryHelper.getLocationInteractor(this, new OnLocationSentListener() {
            @Override
            public void onLocationSent() {
                Log.e("clickListener", "click");
                Toast.makeText(MainActivity.this, "Location updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailedLocation(String message) {
                Log.e("clickListener", "click");
                Log.e("DAM", "Location fail: " + message);
            }

            @Override
            public void onLocationForwarded(Location location) {

            }
        }).getLocation(true);
    }

    private void updateHeartRate() {
        Courier.deliverMessage(this, "HeartMessage", "Kurac na sir");
    }

    @Override
    public void onSendDataClick(View view, Channel channel) {
        Log.e("clickListener", "click");
        if (channel.getId() == 1) {
            updateHeartRate();
        } else if (channel.getId() == 4) {
            updateLocation();
        } else if (channel.getId() == 5) {
            updateLight();
        }
    }

    @Override
    public void onClick(View v, Channel channel, boolean isJoined) {

        if (!isJoined) {
            presenter.joinAChannel(channel.getId());
        } else {
            onSendDataClick(v, channel);
        }
    }

    @Override
    public void onClick(View v, Channel channel) {
        String url = "http://cambridgetest.azurewebsites.net/#/event-graph/" + channel.getId();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
