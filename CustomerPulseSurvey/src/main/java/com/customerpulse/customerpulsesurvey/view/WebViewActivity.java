package com.customerpulse.customerpulsesurvey.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.customerpulse.customerpulsesurvey.R;
import com.customerpulse.customerpulsesurvey.jsHandler.CustomerPulseCallbackRegistry;
import com.customerpulse.customerpulsesurvey.listener.CustomerPulseSurveyListener;
import com.customerpulse.customerpulsesurvey.utils.Utils;

/**
 * Activity to display a full-screen survey in a WebView.
 */
public class WebViewActivity extends Activity {

    private static final String TAG = "WebViewActivity";

    /**
     * Token referencing the survey event listener held in {@link CustomerPulseCallbackRegistry}.
     * Defaults to {@link CustomerPulseCallbackRegistry#NO_TOKEN} when no listener was supplied.
     */
    private long callbackToken = CustomerPulseCallbackRegistry.NO_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.e(TAG, "WebViewActivity started without extras");
            finish();
            return;
        }

        String url = extras.getString("url");
        if (url == null || url.isEmpty()) {
            Log.e(TAG, "WebViewActivity started with invalid URL");
            finish();
            return;
        }

        int closingDelayInMs = extras.getInt("closingDelayInMs", 0);

        callbackToken = extras.getLong("callbackToken", CustomerPulseCallbackRegistry.NO_TOKEN);
        CustomerPulseSurveyListener listener = CustomerPulseCallbackRegistry.get(callbackToken);

        WebView webView = findViewById(R.id.webView);
        Utils.loadUrl(webView, url, this, closingDelayInMs, listener);
    }

    // Intentional: this class extends raw android.app.Activity (minSdk 21), where the
    // OnBackPressedDispatcher API is unavailable, so overriding the deprecated
    // onBackPressed() is the correct mechanism here. Behavior is unchanged.
    @SuppressWarnings("deprecation")
    @Override
    public void onBackPressed() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            super.onBackPressed();
            return;
        }
        boolean dismissable = extras.getBoolean("dismissible", false);
        if (dismissable) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        // Only remove on a genuine teardown (completion/back/dismiss). On a
        // configuration-change recreation isFinishing() is false, so the entry is
        // retained and the recreated onCreate can re-resolve the same redelivered
        // callbackToken — otherwise callbacks would silently stop after a rotation.
        if (isFinishing()) {
            CustomerPulseCallbackRegistry.remove(callbackToken);
        }
        super.onDestroy();
    }
}
