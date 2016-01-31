package hr.foi.fbrd.sensei.mvp.presenter;

import com.dmacan.input.ChannelJoinResponse;
import com.dmacan.input.util.Mock;

import java.util.ArrayList;

import hr.foi.fbrd.sensei.listeners.Listener;
import hr.foi.fbrd.sensei.mvp.interactor.ChannelsInteractor;
import hr.foi.fbrd.sensei.mvp.view.ChannelView;
import hr.foi.fbrd.sensei.mvp.view.EventsView;


public class ChannelsPresenter {

    private EventsView view;
    private ChannelView channelView;
    private ChannelsInteractor interactor;

    public ChannelsPresenter(EventsView view, ChannelView channelView, ChannelsInteractor eventsInteractor) {
        this.view = view;
        this.channelView = channelView;
        this.interactor = eventsInteractor;
    }

    public void getAllEvents() {
        view.onSuccess(Mock.allCollections());
    }

    public void joinAChannel(int id){
        interactor.joinAChannel(id, listener);
    }

    private Listener<ArrayList<ChannelJoinResponse>> listener = new Listener<ArrayList<ChannelJoinResponse>>() {
        @Override
        public void onSuccess(ArrayList<ChannelJoinResponse> channelJoinResponses) {
            channelView.onSuccess(channelJoinResponses);
        }

        @Override
        public void onFailure(String error) {
            channelView.onFailure(error);
        }

        @Override
        public void onConnectionFailure(String message) {

        }
    };
}
