package hr.foi.fbrd.sensei.mvp.view;

import com.dmacan.input.ChannelDetails;

import java.util.ArrayList;



public interface ChannelDetailsView {

    void onSuccess(ArrayList<ChannelDetails> channelDetails);
    void onError(String error);
}
