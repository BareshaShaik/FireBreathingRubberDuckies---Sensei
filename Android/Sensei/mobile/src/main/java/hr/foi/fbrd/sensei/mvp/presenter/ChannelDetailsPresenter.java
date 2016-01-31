package hr.foi.fbrd.sensei.mvp.presenter;

import com.dmacan.input.Channel;
import com.dmacan.input.ChannelDetails;

import java.util.ArrayList;

import hr.foi.fbrd.sensei.listeners.Listener;
import hr.foi.fbrd.sensei.mvp.interactor.ChannelDetailsInteractor;
import hr.foi.fbrd.sensei.mvp.view.ChannelDetailsView;

public class ChannelDetailsPresenter {

    private ChannelDetailsView view;
    private ChannelDetailsInteractor interactor;

    public ChannelDetailsPresenter(ChannelDetailsView view, ChannelDetailsInteractor interactor) {
        this.interactor = interactor;
        this.view = view;
    }

    public void getChannelData(Channel channel){
        interactor.getChannelData(listener, channel);
    }

    private Listener<ArrayList<ChannelDetails>> listener = new Listener<ArrayList<ChannelDetails>>() {
        @Override
        public void onSuccess(ArrayList<ChannelDetails> channelDetails) {
            view.onSuccess(channelDetails);
        }

        @Override
        public void onFailure(String message) {
            view.onError(message);
        }

        @Override
        public void onConnectionFailure(String message) {

        }
    };

    public void cancel() {
        interactor.cancel();
    }

}