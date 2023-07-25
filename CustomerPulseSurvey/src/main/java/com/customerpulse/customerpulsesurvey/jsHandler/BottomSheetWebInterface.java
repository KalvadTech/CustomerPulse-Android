package com.customerpulse.customerpulsesurvey.jsHandler;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetWebInterface extends WebAppInterface {
    BottomSheetDialog bottomSheetDialog;

    /**
     * Instantiate the interface and set the BottomSheet
     *
     * @param bottomSheetDialog reference to the opened bottom sheet dialog
     * @param closeDelayInMs   time to wait before closing the survey after finish in milli seconds
     */
    public BottomSheetWebInterface(Context context, BottomSheetDialog bottomSheetDialog, int closeDelayInMs) {
        super(context, closeDelayInMs);
        this.bottomSheetDialog = bottomSheetDialog;
    }


    @Override
    void close() {
        Handler handler = new Handler();
        handler.postDelayed(() -> ((Activity) context).runOnUiThread(() -> bottomSheetDialog.hide()), closeDelayInMs);
    }
}
