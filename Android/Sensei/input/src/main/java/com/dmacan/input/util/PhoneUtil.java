package com.dmacan.input.util;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.dmacan.input.listener.OnPhoneCallListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneUtil {

    private static TelephonyManager telephonyManager;
    private static Context context;
    private static PhoneStateListener phoneStateListener;
    private static Map<String, List<OnPhoneCallListener>> phoneCallListeners;

    public static void initialize(Context context) {
        PhoneUtil.context = context;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        phoneCallListeners = new HashMap<>();
    }

    public static boolean isPhoneAvailable() {
        return telephonyManager.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    public static void listenForPhoneCall(String number, OnPhoneCallListener onPhoneCallListener) {
        number = format(number);
        if (!phoneCallListeners.containsKey(number)) {
            phoneCallListeners.put(number, new ArrayList<OnPhoneCallListener>());
        }
        phoneCallListeners.get(number).add(onPhoneCallListener);
    }

    private static PhoneStateListener phoneStateListener() {
        if (phoneStateListener == null) {
            phoneStateListener = new PhoneCallListener();
        }
        return phoneStateListener;
    }

    private static class PhoneCallListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            incomingNumber = format(incomingNumber);
            if (state == TelephonyManager.CALL_STATE_RINGING && phoneCallListeners.containsKey(incomingNumber)) {
                for (OnPhoneCallListener listener : phoneCallListeners.get(incomingNumber)) {
                    if (listener != null) {
                        listener.onPhoneCall(incomingNumber);
                    }
                }
            }
        }
    }

    private static String format(String number) {
        return PhoneNumberUtils.formatNumber(number, "uk");
    }
}


