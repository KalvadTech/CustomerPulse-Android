package com.customerpulse.customerpulsesurvey.jsHandler;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

public abstract class WebAppInterface {

    Context context;
    int closeDelayInMs;

    WebAppInterface(Context context, int closeDelayInMs) {
        this.context = context;
        this.closeDelayInMs = closeDelayInMs;
    }

    /**
     * handle events coming from the web
     */
    @JavascriptInterface
    public void jsEvent(String event) {
        Log.println(Log.INFO, "JsEvent Received:", event);
        switch (event) {
            case "so-widget-completed":
                close();
                break;
            case "open":
                break;
            default:
                Log.println(Log.ERROR, "JsEvent", "Event Case Not Implemented");
        }
    }

    /**
     * handle the so-widget-completed event
     */
    abstract void close();

}
