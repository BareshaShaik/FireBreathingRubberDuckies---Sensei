package hr.foi.fbrd.sensei.adapters;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmacan.input.Channel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.SenseiApp;
import hr.foi.fbrd.sensei.listeners.OnChannelClickListener;
import hr.foi.fbrd.sensei.listeners.OnChannelsClickListener;


public class ChannelsListAdapter extends RecyclerView.Adapter<ChannelsListAdapter.ViewHolder> {


    private List<Channel> list;
    private OnChannelsClickListener listener;
    private OnChannelClickListener onChannelClickListener;
    private Context context;

    public ChannelsListAdapter(List<Channel> list, OnChannelsClickListener listener, OnChannelClickListener OnChannelClickListener, Context context) {
        this.list = list;
        this.listener = listener;
        this.onChannelClickListener = OnChannelClickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.channels_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChannelClickListener.onClick(v, list.get(position));
            }
        });

        holder.textView.setText(list.get(position).getDesc());

        if (list.get(position).getImage() instanceof String) {
            Glide.with(context)
                    .load(list.get(position).getImage().toString())
                    .into(holder.logo);
        } else {
            Glide.with(context)
                    .load(list.get(position).getImage())
                    .into(holder.logo);
        }

        holder.name.setText(list.get(position).getName());

        boolean isJoined = PreferenceManager.getDefaultSharedPreferences(SenseiApp.getInstance())
                .getBoolean(list.get(position).getName(), false);

        Log.e("JOINED", String.valueOf(isJoined));

        if (isJoined) {
            holder.joinChannel.setText("Send data");
        }
        if (!(list.get(position).getId() == 1 || list.get(position).getId() == 4 || list.get(position).getId() == 5)) {
            holder.joinChannel.setVisibility(View.GONE);
        } else {
            holder.joinChannel.setVisibility(View.VISIBLE);
            holder.joinChannel.setOnClickListener(v -> {
                if (holder.joinChannel.getText().toString().toLowerCase().equals("send data")) {
                    listener.onClick(v, list.get(position), true);
                } else {
                    PreferenceManager.getDefaultSharedPreferences(SenseiApp.getInstance()).edit()
                            .putBoolean(list.get(position).getName(), true).apply();
                    holder.joinChannel.setText("Send data");
                    listener.onClick(v, list.get(position), false);
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.channel_logo)
        ImageView logo;

        @Bind(R.id.channel_title)
        TextView name;

        @Bind(R.id.join_channel_button)
        Button joinChannel;

        @Bind(R.id.description)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}