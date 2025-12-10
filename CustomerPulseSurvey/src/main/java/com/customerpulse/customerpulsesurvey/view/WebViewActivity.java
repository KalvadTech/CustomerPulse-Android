package com.customerpulse.customerpulsesurvey.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.customerpulse.customerpulsesurvey.R;
import com.customerpulse.customerpulsesurvey.utils.Utils;

/**
 * Activity to display a full-screen survey in a WebView.
 */
public class WebViewActivity extends Activity {

    private static final String TAG = "WebViewActivity";

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

        WebView webView = findViewById(R.id.webView);
        Utils.loadUrl(webView, url, this, closingDelayInMs);
    }

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
}