package com.customerpulse.customerpulsesurvey.jsHandler;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.customerpulse.customerpulsesurvey.listener.CustomerPulseSurveyListener;

public abstract class WebAppInterface {

    Context context;
    int closeDelayInMs;
    CustomerPulseSurveyListener listener;

    WebAppInterface(Context context, int closeDelayInMs) {
        this(context, closeDelayInMs, null);
    }

    WebAppInterface(Context context, int closeDelayInMs, CustomerPulseSurveyListener listener) {
        this.context = context;
        this.closeDelayInMs = closeDelayInMs;
        this.listener = listener;
    }

    /**
     * handle events coming from the web
     */
    @JavascriptInterface
    public void jsEvent(String event) {
        Log.println(Log.INFO, "JsEvent Received:", event);
        handleEvent(event);
    }

    /**
     * Route a survey event string to the appropriate teardown and listener callback.
     *
     * <p>Extracted from {@link #jsEvent(String)} so the routing logic can be unit-tested
     * without a live WebView or {@code @JavascriptInterface} plumbing. The event strings
     * are the shared web widget vocabulary ({@code so-widget-*}); keep them identical to
     * the Web contract.</p>
     *
     * <ul>
     *   <li>{@code so-widget-completed} - auto-close the survey window <b>and</b> notify
     *       {@link CustomerPulseSurveyListener#onCompleted()}.</li>
     *   <li>{@code so-widget-error} - notify {@link CustomerPulseSurveyListener#onError()}
     *       only; no teardown.</li>
     *   <li>{@code so-widget-closed} - notify {@link CustomerPulseSurveyListener#onDismissed()}
     *       only; no teardown (the web widget closed itself).</li>
     *   <li>{@code open} - no-op.</li>
     *   <li>anything else - logged as not implemented.</li>
     * </ul>
     *
     * @param event the web widget event string
     */
    void handleEvent(String event) {
        switch (event) {
            case "so-widget-completed":
                close();
                dispatchOnMain(() -> listener.onCompleted());
                break;
            case "so-widget-error":
                dispatchOnMain(() -> listener.onError());
                break;
            case "so-widget-closed":
                dispatchOnMain(() -> listener.onDismissed());
                break;
            case "open":
                break;
            default:
                Log.println(Log.ERROR, "JsEvent", "Event Case Not Implemented");
        }
    }

    /**
     * Deliver a listener callback on the main (UI) thread, mirroring the {@code close()}
     * dispatch pattern. Does nothing when no listener was provided.
     *
     * @param r the callback to run on the main thread
     */
    void dispatchOnMain(Runnable r) {
        if (listener == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(r);
    }

    /**
     * handle the so-widget-completed event
     */
    abstract void close();

}
