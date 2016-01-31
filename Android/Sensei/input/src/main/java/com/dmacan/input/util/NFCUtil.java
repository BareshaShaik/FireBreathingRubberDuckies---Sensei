package com.dmacan.input.util;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;

public class NFCUtil {

    private static Context context;
    private static NfcManager nfcManager;
    private static NfcAdapter adapter;

    public static void initialize(Context context) {
        NFCUtil.context = context;
        nfcManager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        adapter = nfcManager.getDefaultAdapter();
    }

    public static boolean hasNFC() {
        return adapter != null && adapter.isEnabled();
    }

}
