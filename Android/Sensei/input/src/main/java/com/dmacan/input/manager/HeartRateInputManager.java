package com.dmacan.input.manager;

import com.dmacan.input.entity.Input;
import com.dmacan.input.listener.OnValueListener;
import com.dmacan.input.util.FitUtil;

public class HeartRateInputManager extends InputManager<Integer> {

    public HeartRateInputManager(Input input) {
        super(input);
    }

    @Override
    public void getResult(OnValueListener<Integer> listener) {
        FitUtil.checkHeartRate(listener::onValue);
    }
}
