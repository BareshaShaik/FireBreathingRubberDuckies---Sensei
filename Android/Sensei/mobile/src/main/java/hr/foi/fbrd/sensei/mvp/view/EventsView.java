package hr.foi.fbrd.sensei.mvp.view;

import com.dmacan.input.Channel;

import java.util.List;


public interface EventsView {

    void onFailure(String message);

    void onSuccess(List<Channel> channels);
}
