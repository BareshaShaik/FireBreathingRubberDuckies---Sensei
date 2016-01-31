package hr.foi.fbrd.sensei.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dmacan.input.Channel;
import com.dmacan.input.ChannelDetails;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.adapters.ChannelDetailsAdapter;
import hr.foi.fbrd.sensei.helpers.FactoryHelper;
import hr.foi.fbrd.sensei.listeners.OnChannelDetailsClickListener;
import hr.foi.fbrd.sensei.mvp.presenter.ChannelDetailsPresenter;
import hr.foi.fbrd.sensei.mvp.view.ChannelDetailsView;

public class ChannelDetailsActivity extends BaseActivity implements ChannelDetailsView, OnChannelDetailsClickListener {

    @Bind(R.id.graph_view)
    TextView graphView;
    @Bind(R.id.channel_details_recycler)
    RecyclerView channelDetailsRecycler;
    private ChannelDetailsPresenter presenter;
    private ArrayList<ChannelDetails> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Channel channel = (Channel) intent.getSerializableExtra("channel");
        presenter = FactoryHelper.getPresenter(this);
        channelDetailsRecycler.setLayoutManager(new LinearLayoutManager(this));

        presenter.getChannelData(channel);
    }

    @Override
    public void onSuccess(ArrayList<ChannelDetails> channelDetails) {
        list = channelDetails;
        Toast.makeText(ChannelDetailsActivity.this, "OnSuccess", Toast.LENGTH_SHORT).show();
        channelDetailsRecycler.setAdapter(new ChannelDetailsAdapter(channelDetails, this));
    }

    @Override
    public void onError(String error) {
        Toast.makeText(ChannelDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view, int position) {
        Toast.makeText(ChannelDetailsActivity.this, list.get(position).toString() , Toast.LENGTH_SHORT).show();
    }
}
