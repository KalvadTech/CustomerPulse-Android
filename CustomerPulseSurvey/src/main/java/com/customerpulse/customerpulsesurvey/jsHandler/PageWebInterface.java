package com.customerpulse.customerpulsesurvey.jsHandler;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

public class PageWebInterface extends WebAppInterface{

    /**
     * Instantiate the interface and set the BottomSheet
     *
     * @param context reference to opened activity
     * @param closeDelayInMs   time to wait before closing the survey after finish in milli seconds
     */
    public PageWebInterface(Context context, int closeDelayInMs) {
        super(context, closeDelayInMs);
    }


    @Override
    void close() {
        Handler handler = new Handler();
        handler.postDelayed(() -> ((Activity) context).runOnUiThread(() -> ((Activity) context).finish()), closeDelayInMs);
    }
}
