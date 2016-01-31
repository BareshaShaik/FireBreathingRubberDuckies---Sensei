package hr.foi.fbrd.sensei.listeners;

import android.view.View;

import com.dmacan.input.Channel;


public interface OnChannelsClickListener {

    void onClick(View v, Channel channel, boolean isJoined);
}
