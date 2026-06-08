package com.customerpulse.customerpulsesurvey.jsHandler;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.customerpulse.customerpulsesurvey.listener.CustomerPulseSurveyListener;

/**
 * JavaScript interface for full-page survey Activity.
 * Handles communication between WebView JavaScript and native Android code.
 */
public class PageWebInterface extends WebAppInterface {

    private static final String TAG = "PageWebInterface";

    /**
     * Instantiate the interface and set the context.
     *
     * @param context        reference to opened activity (must be Activity, not Application context)
     * @param closeDelayInMs time to wait before closing the survey after finish in milliseconds
     */
    public PageWebInterface(Context context, int closeDelayInMs) {
        super(context, closeDelayInMs);
    }

    /**
     * Instantiate the interface with a survey event listener.
     *
     * @param context        reference to opened activity (must be Activity, not Application context)
     * @param closeDelayInMs time to wait before closing the survey after finish in milliseconds
     * @param listener       listener notified of survey events on the main thread (may be {@code null})
     */
    public PageWebInterface(Context context, int closeDelayInMs, CustomerPulseSurveyListener listener) {
        super(context, closeDelayInMs, listener);
    }

    @Override
    void close() {
        if (!(context instanceof Activity)) {
            Log.e(TAG, "Context is not an Activity, cannot close");
            return;
        }

        Activity activity = (Activity) context;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (!activity.isFinishing() && !activity.isDestroyed()) {
                activity.finish();
            }
        }, closeDelayInMs);
    }
}
