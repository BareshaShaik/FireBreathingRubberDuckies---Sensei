package com.dmacan.input.manager;

import android.content.Context;

import com.dmacan.input.entity.Input;
import com.dmacan.input.factory.InputFactory;
import com.dmacan.input.listener.OnValueListener;
import com.dmacan.input.util.SensorUtil;

import java.util.List;

public abstract class InputManager<T> {

    private static List<Input> availableInputs;

    protected Input input;

    public InputManager(Input input) {
        this.input = input;
    }

    public abstract void getResult(OnValueListener<T> listener);

    public static List<Input> getAllAvailableInputs(Context context, boolean isPhone) {
        InputFactory.initialize(context, isPhone);
        if (availableInputs == null) {
            SensorUtil.initialize(context);
            availableInputs = InputFactory.availableInputs(context);
        }
        return availableInputs;
    }

}
