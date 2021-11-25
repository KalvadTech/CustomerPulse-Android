package com.customerpulse.customerpulsesurvey.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.customerpulse.customerpulsesurvey.R;
import com.customerpulse.customerpulsesurvey.utils.Utils;

public class WebViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Bundle extras = getIntent().getExtras();
        String url = extras.getString("url");
        int closingDelayInMs = extras.getInt("closingDelayInMs");
        setContentView(R.layout.activity_web_view);
        WebView webView = findViewById(R.id.webView);
        Utils.loadUrl(webView, url, this, closingDelayInMs);
    }
}