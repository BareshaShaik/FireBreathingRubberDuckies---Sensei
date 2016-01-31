package com.dmacan.input.util;

import android.content.Context;
import android.content.pm.PackageManager;

public class CameraUtil {

    private static Context context;

    public static void initialize(Context context) {
        CameraUtil.context = context;
    }

    public static boolean isCameraAvailable() {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
}
