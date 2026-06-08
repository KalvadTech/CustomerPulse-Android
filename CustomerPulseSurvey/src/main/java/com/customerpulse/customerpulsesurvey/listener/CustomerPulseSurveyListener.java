package com.customerpulse.customerpulsesurvey.listener;

/**
 * Listener for survey lifecycle events emitted by the CustomerPulse survey web widget.
 *
 * <p>Pass an implementation to one of the {@code showSurvey*} overloads on
 * {@code CustomerPulseSurvey} to be notified when the survey is completed, reports
 * an error, or is dismissed. The events mirror the web widget event vocabulary
 * ({@code so-widget-completed} / {@code so-widget-error} / {@code so-widget-closed}),
 * so the same semantics apply across the Web, Android, and iOS SDKs.</p>
 *
 * <h3>Selective implementation</h3>
 * <p>Every method has a {@code default} no-op body, so integrators only need to
 * override the events they care about:</p>
 * <pre>{@code
 * CustomerPulseSurvey.showSurveyPage(this, "APP_ID", "TOKEN", options,
 *     new CustomerPulseSurveyListener() {
 *         @Override
 *         public void onCompleted() {
 *             // user finished the survey
 *         }
 *     });
 * }</pre>
 *
 * <h3>Thread safety</h3>
 * <p>All callbacks are delivered on the <b>main (UI) thread</b>, so it is safe to
 * touch the UI directly from inside them.</p>
 *
 * @author CustomerPulse
 * @version 2.1.0
 * @see com.customerpulse.customerpulsesurvey.CustomerPulseSurvey
 */
public interface CustomerPulseSurveyListener {

    /**
     * Called when the user has successfully completed the survey
     * (web event {@code so-widget-completed}).
     *
     * <p>This fires <i>in addition to</i> the SDK's existing auto-close behavior;
     * the survey window is still dismissed automatically after the configured delay.</p>
     */
    default void onCompleted() {
    }

    /**
     * Called when the survey reports an error (web event {@code so-widget-error}).
     *
     * <p>The survey is <b>not</b> torn down by the SDK on this event; the web widget
     * decides whether to remain visible. Use this to log or surface the error to the user.</p>
     */
    default void onError() {
    }

    /**
     * Called when the survey is dismissed (web event {@code so-widget-closed}).
     *
     * <p>This is a notification only; the SDK does not itself dismiss the survey window
     * in response to this event (dialog/activity teardown is host/user-driven).</p>
     */
    default void onDismissed() {
    }
}
