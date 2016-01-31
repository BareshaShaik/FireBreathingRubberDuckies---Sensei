package hr.foi.fbrd.sensei.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmacan.input.ChannelDetails;

import java.util.ArrayList;

import butterknife.ButterKnife;
import hr.foi.fbrd.sensei.R;
import hr.foi.fbrd.sensei.listeners.OnChannelDetailsClickListener;

public class ChannelDetailsAdapter extends RecyclerView.Adapter<ChannelDetailsAdapter.ViewHolder> {


    private ArrayList<ChannelDetails> list;
    private OnChannelDetailsClickListener listener;

    public ChannelDetailsAdapter(ArrayList<ChannelDetails> list, OnChannelDetailsClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_details_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}