package com.customerpulse.customerpulsesurvey.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.customerpulse.customerpulsesurvey.jsHandler.BottomSheetWebInterface;
import com.customerpulse.customerpulsesurvey.jsHandler.PageWebInterface;
import com.customerpulse.customerpulsesurvey.view.RoundedWebView;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import java.util.HashMap;

/**
 * Class Utils for helper methods
 */
public class Utils {


    /**
     * load url to web view inside a bottom sheet dialog
     *
     * @param webView  the webView widget
     * @param url      the url to load to webView
     * @param bottomSheetDialog instance of the webView
     * @param closingDelayInMs   time to wait before closing the survey after finish in milli seconds
     */
    static public void loadUrl(RoundedWebView webView, String url, Context context, BottomSheetDialog bottomSheetDialog, int closingDelayInMs) {
        initializeWebView(webView, url);
        webView.addJavascriptInterface(new BottomSheetWebInterface(context, bottomSheetDialog, closingDelayInMs), "Android");
    }

    /**
     * load url to web view inside an activity
     *
     * @param webView  the webView widget
     * @param url      the url to load to webView
     * @param context instance of the current activity context
     * @param closingDelayInMs   time to wait before closing the survey after finish in milli seconds
     */
    static public void loadUrl(WebView webView, String url, Context context, int closingDelayInMs) {
        initializeWebView(webView, url);
        webView.addJavascriptInterface(new PageWebInterface(context, closingDelayInMs), "Android");
    }

    /**
     * initialize the webView with the right settings
     *
     * @param webView  the webView widget
     * @param url      the url to load to webView
     */
    private static void initializeWebView(WebView webView, String url) {
        WebSettings webSettings = webView.getSettings();
        generateDefaultSettings(webSettings);
        webView.loadUrl(url);
    }

    /**
     * set the default settings needed to run the CustomerPulse page
     *
     * @param webSettings  instance to the webView settings
     */
    @SuppressLint("SetJavaScriptEnabled")
    private static void generateDefaultSettings(WebSettings webSettings) {
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setUseWideViewPort(true);
    }

    /**
     * convert params from hashmap to string
     *
     * @param hashMap map representation of the params
     */
    public static String getParams(HashMap<String, String> hashMap) {
        StringBuilder params = new StringBuilder("?");
        for (String i : hashMap.keySet()) {
            params.append(i).append("=").append(hashMap.get(i));
        }
        return params.toString();
    }


    /**
     * convert dp to pixels
     *
     * @param context reference to activity context
     * @param dp int holds the dp value
     */
    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
