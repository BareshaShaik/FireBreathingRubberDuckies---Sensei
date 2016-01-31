package hr.foi.fbrd.sensei.mvp.view;

import com.dmacan.input.ChannelJoinResponse;

import java.util.ArrayList;

public interface ChannelView extends BaseView {

    void onFailure(String message);

    void onSuccess(ArrayList<ChannelJoinResponse> response);

}