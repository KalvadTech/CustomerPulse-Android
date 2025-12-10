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
 * <h3>Quick Start</h3>
 * <pre>{@code
 * // 1. Configure environment (optional, defaults to PRODUCTION)
 * CustomerPulseSurvey.setEnvironment(CustomerPulseSurvey.Environment.SANDBOX);
 *
 * // 2. Enable debug logging (optional, for development)
 * CustomerPulseSurvey.setDebugLogging(true);
 *
 * // 3. Display a survey
 * HashMap<String, String> options = new HashMap<>();
 * options.put("lang", "en");
 * CustomerPulseSurvey.showSurveyPage(this, "APP_ID", "TOKEN", options);
 * }</pre>
 *
 * <h3>Environment Configuration</h3>
 * <p>By default, the SDK uses the production environment. To switch to sandbox:</p>
 * <pre>{@code
 * CustomerPulseSurvey.setEnvironment(CustomerPulseSurvey.Environment.SANDBOX);
 * }</pre>
 *
 * <h3>Important: Context Requirement</h3>
 * <p>All survey display methods require an <b>Activity context</b>, not an Application context.
 * Using Application context will result in errors.</p>
 *
 * <h3>Thread Safety</h3>
 * <p>All methods must be called from the main (UI) thread.</p>
 *
 * @author CustomerPulse
 * @version 2.0.0
 * @see Environment
 * @see #showSurveyPage(Context, String, String, HashMap, boolean, int)
 * @see #showSurveyBottomSheet(Context, String, String, HashMap, boolean, int)
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
     * Displays a survey in a full-screen Activity.
     *
     * <p>Opens a new Activity containing a WebView that loads the survey.
     * The survey will be loaded from the configured environment
     * (PRODUCTION or SANDBOX) based on {@link #setEnvironment}.</p>
     *
     * <h4>Example:</h4>
     * <pre>{@code
     * HashMap<String, String> options = new HashMap<>();
     * options.put("lang", "en");
     * CustomerPulseSurvey.showSurveyPage(this, "APP_ID", "TOKEN", options, true, 3000);
     * }</pre>
     *
     * @param context          Activity context (required). Must be an Activity, not Application context.
     * @param app_id           Application ID provided by CustomerPulse
     * @param link_or_token    Survey token or linking ID provided by CustomerPulse
     * @param options          Additional parameters to pass to the survey (e.g., "lang" for language)
     * @param dismissible      If true, user can dismiss the survey; if false, must complete it
     * @param closingDelayInMs Delay in milliseconds before auto-closing after survey completion
     *
     * @see #showSurveyPage(Context, String, String, HashMap)
     * @see #showSurveyBottomSheet(Context, String, String, HashMap, boolean, int)
     * @see #setEnvironment(Environment)
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

    /**
     * Displays a survey in a full-screen Activity with default settings.
     *
     * <p>Uses default values: dismissible = true, closingDelayInMs = 2000</p>
     *
     * @param context       Activity context (required)
     * @param app_id        Application ID provided by CustomerPulse
     * @param link_or_token Survey token or linking ID
     * @param options       Additional parameters (e.g., "lang")
     *
     * @see #showSurveyPage(Context, String, String, HashMap, boolean, int)
     */
    public static void showSurveyPage(Context context, String app_id, String link_or_token, HashMap<String, String> options) {
        showSurveyPage(context, app_id, link_or_token, options, true, 2000);
    }

    /**
     * Displays a survey in a full-screen Activity with custom dismissible setting.
     *
     * <p>Uses default closing delay of 2000ms.</p>
     *
     * @param context       Activity context (required)
     * @param app_id        Application ID provided by CustomerPulse
     * @param link_or_token Survey token or linking ID
     * @param options       Additional parameters (e.g., "lang")
     * @param dismissible   If true, user can dismiss; if false, must complete
     *
     * @see #showSurveyPage(Context, String, String, HashMap, boolean, int)
     */
    public static void showSurveyPage(Context context, String app_id, String link_or_token, HashMap<String, String> options, boolean dismissible) {
        showSurveyPage(context, app_id, link_or_token, options, dismissible, 2000);
    }

    /**
     * Displays a survey in a full-screen Activity with custom closing delay.
     *
     * <p>Uses default dismissible = true.</p>
     *
     * @param context          Activity context (required)
     * @param app_id           Application ID provided by CustomerPulse
     * @param link_or_token    Survey token or linking ID
     * @param options          Additional parameters (e.g., "lang")
     * @param closingDelayInMs Delay in milliseconds before auto-closing
     *
     * @see #showSurveyPage(Context, String, String, HashMap, boolean, int)
     */
    public static void showSurveyPage(Context context, String app_id, String link_or_token, HashMap<String, String> options, int closingDelayInMs) {
        showSurveyPage(context, app_id, link_or_token, options, true, closingDelayInMs);
    }

    /**
     * Displays a survey in a Material Design bottom sheet dialog.
     *
     * <p>Shows a bottom sheet that slides up from the bottom of the screen,
     * providing a less intrusive survey experience. The survey will be loaded
     * from the configured environment based on {@link #setEnvironment}.</p>
     *
     * <h4>Example:</h4>
     * <pre>{@code
     * HashMap<String, String> options = new HashMap<>();
     * options.put("lang", "ar");
     * CustomerPulseSurvey.showSurveyBottomSheet(this, "APP_ID", "TOKEN", options, true, 3000);
     * }</pre>
     *
     * <h4>Dismissible Behavior:</h4>
     * <ul>
     *   <li>{@code true} - Shows close button, user can tap outside to dismiss</li>
     *   <li>{@code false} - Hides close button, user must complete the survey</li>
     * </ul>
     *
     * @param context          Activity context (required). Must be an Activity, not Application context.
     * @param app_id           Application ID provided by CustomerPulse
     * @param link_or_token    Survey token or linking ID provided by CustomerPulse
     * @param options          Additional parameters to pass to the survey (e.g., "lang" for language)
     * @param dismissible      If true, shows close button and allows dismissal; if false, must complete
     * @param closingDelayInMs Delay in milliseconds before auto-closing after survey completion
     *
     * @see #showSurveyBottomSheet(Context, String, String, HashMap)
     * @see #showSurveyPage(Context, String, String, HashMap, boolean, int)
     * @see #setEnvironment(Environment)
     */
    public static void showSurveyBottomSheet(Context context, String app_id, String link_or_token, HashMap<String, String> options, boolean dismissible, int closingDelayInMs) {
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

    /**
     * Displays a survey in a bottom sheet with default settings.
     *
     * <p>Uses default values: dismissible = true, closingDelayInMs = 2000</p>
     *
     * @param context       Activity context (required)
     * @param app_id        Application ID provided by CustomerPulse
     * @param link_or_token Survey token or linking ID
     * @param options       Additional parameters (e.g., "lang")
     *
     * @see #showSurveyBottomSheet(Context, String, String, HashMap, boolean, int)
     */
    public static void showSurveyBottomSheet(Context context, String app_id, String link_or_token, HashMap<String, String> options) {
        showSurveyBottomSheet(context, app_id, link_or_token, options, true, 2000);
    }

    /**
     * Displays a survey in a bottom sheet with custom dismissible setting.
     *
     * <p>Uses default closing delay of 2000ms.</p>
     *
     * @param context       Activity context (required)
     * @param app_id        Application ID provided by CustomerPulse
     * @param link_or_token Survey token or linking ID
     * @param options       Additional parameters (e.g., "lang")
     * @param dismissible   If true, shows close button; if false, must complete
     *
     * @see #showSurveyBottomSheet(Context, String, String, HashMap, boolean, int)
     */
    public static void showSurveyBottomSheet(Context context, String app_id, String link_or_token, HashMap<String, String> options, boolean dismissible) {
        showSurveyBottomSheet(context, app_id, link_or_token, options, dismissible, 2000);
    }

    /**
     * Displays a survey in a bottom sheet with custom closing delay.
     *
     * <p>Uses default dismissible = true.</p>
     *
     * @param context          Activity context (required)
     * @param app_id           Application ID provided by CustomerPulse
     * @param link_or_token    Survey token or linking ID
     * @param options          Additional parameters (e.g., "lang")
     * @param closingDelayInMs Delay in milliseconds before auto-closing
     *
     * @see #showSurveyBottomSheet(Context, String, String, HashMap, boolean, int)
     */
    public static void showSurveyBottomSheet(Context context, String app_id, String link_or_token, HashMap<String, String> options, int closingDelayInMs) {
        showSurveyBottomSheet(context, app_id, link_or_token, options, true, closingDelayInMs);
    }




}
