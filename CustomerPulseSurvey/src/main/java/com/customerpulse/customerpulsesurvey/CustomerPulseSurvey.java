package com.customerpulse.customerpulsesurvey;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.customerpulse.customerpulsesurvey.utils.Utils;
import com.customerpulse.customerpulsesurvey.view.CustomerPulseBottomSheet;
import com.customerpulse.customerpulsesurvey.view.WebViewActivity;

import java.util.HashMap;

/**
 * Class to call the module methods
 */
public class CustomerPulseSurvey {

    private static final String BASE_URL = "https://survey.customerpulse.gov.ae/";

    /**
     * open new activity to show the customer pulse survey
     *
     * @param context  context to the current open activity
     * @param link_or_token   the token or the linking id
     * @param options   hashmap object holds all the params that need to be send to survey
     * @param closingDelayInMs   time to wait before closing the survey after finish in milli seconds
     */
    public static void showSurveyPage(Context context, String link_or_token, HashMap<String, String> options, boolean dismissible, int closingDelayInMs) {
        try {
            Intent intent = new Intent(context, WebViewActivity.class);
            String url = BASE_URL + link_or_token + "/" + Utils.getParams(options);
            intent.putExtra("url", url);
            intent.putExtra("closingDelayInMs", closingDelayInMs);
            intent.putExtra("dismissible", dismissible);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("showSurveyPageError", e.getMessage());
        }
    }

    public static void showSurveyPage(Context context, String link_or_token, HashMap<String, String> options) {
        showSurveyPage(context, link_or_token,options, true, 2000);
    }

    public static void showSurveyPage(Context context, String link_or_token, HashMap<String, String> options, boolean dismissible) {
        showSurveyPage(context, link_or_token,options, dismissible, 2000);
    }

    public static void showSurveyPage(Context context, String link_or_token, HashMap<String, String> options, int closingDelayInMs) {
        showSurveyPage(context, link_or_token,options, true, closingDelayInMs);
    }

    /**
     * open Bottom Sheet Dialog to show the customer pulse survey
     *
     * @param context     context to the current open activity
     * @param link_or_token      the token or the linking id
     * @param options      hashmap object holds all the params that need to be send to survey
     * @param dismissible decide bottom sheet dialog can be dismissed or no
     * @param closingDelayInMs   time to wait before closing the survey after finish milli seconds
     */
    public static void showSurveyBottomSheet(Context context, String link_or_token, HashMap<String, String> options, boolean dismissible, int closingDelayInMs){
        try {
            String url = BASE_URL + link_or_token + "/" + Utils.getParams(options);
            new CustomerPulseBottomSheet().show(context, url, dismissible, closingDelayInMs);
        } catch (Exception e) {
            Log.e("showBottomSheetError", e.getMessage());
        }
    }

    public static void showSurveyBottomSheet(Context context, String link_or_token, HashMap<String, String> options) {
        showSurveyBottomSheet(context,link_or_token,options,true,2000);
    }

    public static void showSurveyBottomSheet(Context context, String link_or_token, HashMap<String, String> options, boolean dismissible) {
        showSurveyBottomSheet(context,link_or_token,options,dismissible,2000);
    }

    public static void showSurveyBottomSheet(Context context, String link_or_token, HashMap<String, String> options, int closingDelayInMs) {
        showSurveyBottomSheet(context, link_or_token, options, true, closingDelayInMs);
    }




}
