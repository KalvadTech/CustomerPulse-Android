package com.customerpulse.customerpulseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.customerpulse.customerpulsesurvey.CustomerPulseSurvey;

import java.util.HashMap;

/**
 * Example Activity demonstrating CustomerPulseSurvey SDK integration.
 *
 * <p>This example shows how to:</p>
 * <ul>
 *   <li>Configure the SDK environment (sandbox/production)</li>
 *   <li>Display surveys in full-page mode</li>
 *   <li>Display surveys in bottom sheet mode</li>
 *   <li>Handle dismissible vs non-dismissible surveys</li>
 *   <li>Support multiple languages (English/Arabic)</li>
 * </ul>
 *
 * <h3>Environment Configuration</h3>
 * <p>Call {@link CustomerPulseSurvey#setEnvironment} before displaying any surveys:</p>
 * <pre>{@code
 * // For testing
 * CustomerPulseSurvey.setEnvironment(CustomerPulseSurvey.Environment.SANDBOX);
 *
 * // For production
 * CustomerPulseSurvey.setEnvironment(CustomerPulseSurvey.Environment.PRODUCTION);
 * }</pre>
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Your application ID provided by CustomerPulse.
     * Replace with your actual app ID in production.
     */
    private static final String APP_ID = "APP_ID";

    /**
     * Survey token or linking ID provided by CustomerPulse.
     * This identifies which survey to display.
     */
    private static final String TOKEN = "v2/F/t0/";

    /**
     * Delay in milliseconds before automatically closing the survey after completion.
     * Set to 0 for immediate close, or higher values to show completion message.
     */
    private static final int CLOSING_DELAY_MS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Configure SDK Environment
         * --------------------------
         * Set this BEFORE calling any survey display methods.
         *
         * SANDBOX  - Use for development and testing
         * PRODUCTION - Use for live/released apps
         */
        CustomerPulseSurvey.setEnvironment(CustomerPulseSurvey.Environment.SANDBOX);

        /*
         * Enable Debug Logging (Optional)
         * --------------------------------
         * When enabled, the SDK logs detailed information to Logcat.
         * Useful for debugging during development.
         * Disable in production builds.
         *
         * Logs include:
         * - Environment configuration
         * - Survey URL being loaded
         * - Dismissible state
         * - Closing delay
         */
        CustomerPulseSurvey.setDebugLogging(true);

        setupFullPageSurveyButtons();
        setupBottomSheetSurveyButtons();
    }

    /**
     * Setup buttons for full-page survey display.
     *
     * <p>Full-page surveys open in a new Activity that covers the entire screen.
     * Use this mode when you want the user to focus entirely on the survey.</p>
     */
    private void setupFullPageSurveyButtons() {
        // English full-page survey (non-dismissible)
        Button enPageButton = findViewById(R.id.en_page);
        enPageButton.setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("lang", "en");
            // dismissible=false: User cannot close until survey is complete
            CustomerPulseSurvey.showSurveyPage(this, APP_ID, TOKEN, params, false, CLOSING_DELAY_MS);
        });

        // Arabic full-page survey (dismissible by default)
        Button arPageButton = findViewById(R.id.ar_page);
        arPageButton.setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("lang", "ar");
            CustomerPulseSurvey.showSurveyPage(this, APP_ID, TOKEN, params, CLOSING_DELAY_MS);
        });
    }

    /**
     * Setup buttons for bottom sheet survey display.
     *
     * <p>Bottom sheet surveys slide up from the bottom of the screen.
     * Use this mode for less intrusive survey experiences.</p>
     *
     * <p>The {@code dismissible} parameter controls:</p>
     * <ul>
     *   <li>{@code true} - Shows close button, user can tap outside to dismiss</li>
     *   <li>{@code false} - Hides close button, user must complete survey</li>
     * </ul>
     */
    private void setupBottomSheetSurveyButtons() {
        // Arabic bottom sheet (non-dismissible)
        Button arBottomSheetButton = findViewById(R.id.ar_bottom_sheet);
        arBottomSheetButton.setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("lang", "ar");
            CustomerPulseSurvey.showSurveyBottomSheet(this, APP_ID, TOKEN, params, false, CLOSING_DELAY_MS);
        });

        // English bottom sheet (non-dismissible)
        Button enBottomSheetButton = findViewById(R.id.en_bottom_sheet);
        enBottomSheetButton.setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("lang", "en");
            CustomerPulseSurvey.showSurveyBottomSheet(this, APP_ID, TOKEN, params, false, CLOSING_DELAY_MS);
        });

        // Arabic bottom sheet (dismissible - user can close anytime)
        Button arDismissibleBottomSheetButton = findViewById(R.id.ar_dismissable_bottom_sheet);
        arDismissibleBottomSheetButton.setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("lang", "ar");
            CustomerPulseSurvey.showSurveyBottomSheet(this, APP_ID, TOKEN, params, true, CLOSING_DELAY_MS);
        });

        // English bottom sheet (dismissible - user can close anytime)
        Button enDismissibleBottomSheetButton = findViewById(R.id.en_dismissable_bottom_sheet);
        enDismissibleBottomSheetButton.setOnClickListener(v -> {
            HashMap<String, String> params = new HashMap<>();
            params.put("lang", "en");
            CustomerPulseSurvey.showSurveyBottomSheet(this, APP_ID, TOKEN, params, true, CLOSING_DELAY_MS);
        });
    }
}