package com.customerpulse.customerpulsesurvey;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.customerpulse.customerpulsesurvey.utils.Utils;
import com.customerpulse.customerpulsesurvey.view.BottomSheetHandler;
import com.customerpulse.customerpulsesurvey.view.WebViewActivity;

import java.util.HashMap;

/**
 * Class to call the module methods
 */
public class CustomerPulseSurvey {

    private static final String BASE_URL = "https://hm.stg.pmo.gov.ae/";

    /**
     * open new activity to show the customer pulse survey
     *
     * @param context  context to the current open activity
     * @param option   the token or the linking id
     * @param params   hashmap object holds all the params that need to be send to survey
     * @param closingDelayInMs   time to wait before closing the survey after finish in milli seconds
     */
    public static void showSurveyPage(Context context, String option, HashMap<String, String> params, int closingDelayInMs) {
        try {
            Intent intent = new Intent(context, WebViewActivity.class);
            String url = BASE_URL + option + "/" + Utils.getParams(params);
            intent.putExtra("url", url);
            intent.putExtra("closingDelayInMs", closingDelayInMs);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("showSurveyPageError", e.getMessage());
        }
    }

    /**
     * open Bottom Sheet Dialog to show the customer pulse survey
     *
     * @param context     context to the current open activity
     * @param option      the token or the linking id
     * @param params      hashmap object holds all the params that need to be send to survey
     * @param dismissible decide bottom sheet dialog can be dismissed or no
     * @param closingDelayInMs   time to wait before closing the survey after finish milli seconds
     */
    public static void showSurveyBottomSheet(Context context, String option, HashMap<String, String> params, boolean dismissible, int closingDelayInMs) {
        try {
            String url = BASE_URL + option + "/" + Utils.getParams(params);
            BottomSheetHandler.show(context, url, dismissible, closingDelayInMs);
        } catch (Exception e) {
            Log.e("showBottomSheetError", e.getMessage());
        }
    }


}
