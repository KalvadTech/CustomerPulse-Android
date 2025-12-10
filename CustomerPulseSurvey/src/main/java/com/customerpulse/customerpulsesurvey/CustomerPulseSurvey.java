package com.customerpulse.customerpulsesurvey;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.customerpulse.customerpulsesurvey.utils.Utils;
import com.customerpulse.customerpulsesurvey.view.CustomerPulseBottomSheet;
import com.customerpulse.customerpulsesurvey.view.WebViewActivity;

import java.util.HashMap;

/**
 * CustomerPulseSurvey SDK - Display web-based surveys in your Android app.
 *
 * <p>This SDK provides two ways to display surveys:</p>
 * <ul>
 *   <li>{@link #showSurveyPage} - Full-screen survey in a new Activity</li>
 *   <li>{@link #showSurveyBottomSheet} - Survey in a Material Design bottom sheet</li>
 * </ul>
 *
 * <h3>Environment Configuration</h3>
 * <p>By default, the SDK uses the production environment. To switch to sandbox:</p>
 * <pre>{@code
 * CustomerPulseSurvey.setEnvironment(CustomerPulseSurvey.Environment.SANDBOX);
 * }</pre>
 *
 * @see Environment
 */
public class CustomerPulseSurvey {

    private static final String TAG = "CustomerPulseSurvey";

    /**
     * Debug logging flag. When enabled, logs SDK operations to Logcat.
     * Default is false (disabled).
     */
    private static boolean debugLogging = false;

    /**
     * Enable or disable debug logging.
     * When enabled, the SDK will log detailed information about its operations.
     *
     * @param enabled true to enable logging, false to disable
     */
    public static void setDebugLogging(boolean enabled) {
        debugLogging = enabled;
        if (enabled) {
            Log.d(TAG, "[CustomerPulse] Debug logging enabled");
        }
    }

    /**
     * Check if debug logging is enabled.
     *
     * @return true if debug logging is enabled
     */
    public static boolean isDebugLoggingEnabled() {
        return debugLogging;
    }

    /**
     * Log a debug message if debug logging is enabled.
     *
     * @param message the message to log
     */
    private static void log(String message) {
        if (debugLogging) {
            Log.d(TAG, "[CustomerPulse] " + message);
        }
    }

    /**
     * SDK Environment configuration.
     * Controls which server the surveys are loaded from.
     */
    public enum Environment {
        /**
         * Production environment - use for live/released apps.
         */
        PRODUCTION("https://survey.customerpulse.gov.ae"),

        /**
         * Sandbox environment - use for testing and development.
         */
        SANDBOX("https://sandboxsurvey.customerpulse.gov.ae");

        private final String baseUrl;

        Environment(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        /**
         * Get the base URL for this environment.
         *
         * @return the base URL string
         */
        public String getBaseUrl() {
            return baseUrl;
        }
    }

    /**
     * Current environment. Default is PRODUCTION.
     */
    private static Environment environment = Environment.PRODUCTION;

    /**
     * Set the SDK environment.
     * Call this before displaying any surveys.
     *
     * @param env the environment to use (PRODUCTION or SANDBOX)
     */
    public static void setEnvironment(Environment env) {
        if (env != null) {
            environment = env;
            log("Environment set to: " + env.name());
        }
    }

    /**
     * Get the current SDK environment.
     *
     * @return the current environment
     */
    public static Environment getEnvironment() {
        return environment;
    }

    /**
     * Get the base URL for the current environment.
     *
     * @return the base URL string
     */
    private static String getBaseUrl() {
        return environment.getBaseUrl();
    }

    /**
     * open new activity to show the customer pulse survey
     *
     * @param context  context to the current open activity
     * @param app_id  the application id
     * @param link_or_token   the token or the linking id
     * @param options   hashmap object holds all the params that need to be send to survey
     * @param closingDelayInMs   time to wait before closing the survey after finish in milli seconds
     */
    public static void showSurveyPage(Context context, String app_id, String link_or_token, HashMap<String, String> options, boolean dismissible, int closingDelayInMs) {
        try {
            Intent intent = new Intent(context, WebViewActivity.class);
            options.put("app_id", app_id);
            String url = getBaseUrl() + "/" + link_or_token + Utils.getParams(options);
            log("Loading survey page");
            log("URL: " + url);
            log("Dismissible: " + dismissible);
            log("Closing delay: " + closingDelayInMs + "ms");
            intent.putExtra("url", url);
            intent.putExtra("closingDelayInMs", closingDelayInMs);
            intent.putExtra("dismissible", dismissible);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Error showing survey page", e);
        }
    }

    public static void showSurveyPage(Context context, String app_id, String link_or_token, HashMap<String, String> options) {
        showSurveyPage(context, app_id, link_or_token,options, true, 2000);
    }

    public static void showSurveyPage(Context context, String app_id, String link_or_token, HashMap<String, String> options, boolean dismissible) {
        showSurveyPage(context, app_id, link_or_token,options, dismissible, 2000);
    }

    public static void showSurveyPage(Context context, String app_id, String link_or_token, HashMap<String, String> options, int closingDelayInMs) {
        showSurveyPage(context, app_id, link_or_token,options, true, closingDelayInMs);
    }

    /**
     * open Bottom Sheet Dialog to show the customer pulse survey
     *
     * @param context     context to the current open activity
     * @param app_id  the application id
     * @param link_or_token      the token or the linking id
     * @param options      hashmap object holds all the params that need to be send to survey
     * @param dismissible decide bottom sheet dialog can be dismissed or no
     * @param closingDelayInMs   time to wait before closing the survey after finish milli seconds
     */
    public static void showSurveyBottomSheet(Context context, String app_id, String link_or_token, HashMap<String, String> options, boolean dismissible, int closingDelayInMs){
        try {
            options.put("app_id", app_id);
            String url = getBaseUrl() + "/" + link_or_token + Utils.getParams(options);
            log("Loading survey bottom sheet");
            log("URL: " + url);
            log("Dismissible: " + dismissible);
            log("Closing delay: " + closingDelayInMs + "ms");
            new CustomerPulseBottomSheet().show(context, url, dismissible, closingDelayInMs);
        } catch (Exception e) {
            Log.e(TAG, "Error showing survey bottom sheet", e);
        }
    }

    public static void showSurveyBottomSheet(Context context, String app_id, String link_or_token, HashMap<String, String> options) {
        showSurveyBottomSheet(context, app_id,link_or_token,options,true,2000);
    }

    public static void showSurveyBottomSheet(Context context, String app_id, String link_or_token, HashMap<String, String> options, boolean dismissible) {
        showSurveyBottomSheet(context, app_id,link_or_token,options,dismissible,2000);
    }

    public static void showSurveyBottomSheet(Context context, String app_id, String link_or_token, HashMap<String, String> options, int closingDelayInMs) {
        showSurveyBottomSheet(context, app_id, link_or_token, options, true, closingDelayInMs);
    }




}
