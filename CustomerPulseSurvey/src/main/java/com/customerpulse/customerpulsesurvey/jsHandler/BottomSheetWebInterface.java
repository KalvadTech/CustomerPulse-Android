package com.customerpulse.customerpulsesurvey.jsHandler;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.android.material.bottomsheet.BottomSheetDialog;

/**
 * JavaScript interface for bottom sheet survey dialog.
 * Handles communication between WebView JavaScript and native Android code.
 */
public class BottomSheetWebInterface extends WebAppInterface {

    private static final String TAG = "BottomSheetWebInterface";
    private final BottomSheetDialog bottomSheetDialog;

    /**
     * Instantiate the interface and set the BottomSheet.
     *
     * @param context           reference to the activity context
     * @param bottomSheetDialog reference to the opened bottom sheet dialog
     * @param closeDelayInMs    time to wait before closing the survey after finish in milliseconds
     */
    public BottomSheetWebInterface(Context context, BottomSheetDialog bottomSheetDialog, int closeDelayInMs) {
        super(context, closeDelayInMs);
        this.bottomSheetDialog = bottomSheetDialog;
    }

    @Override
    void close() {
        if (bottomSheetDialog == null) {
            Log.e(TAG, "BottomSheetDialog is null, cannot close");
            return;
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (bottomSheetDialog.isShowing()) {
                bottomSheetDialog.dismiss();
            }
        }, closeDelayInMs);
    }
}
