package com.customerpulse.customerpulsesurvey.jsHandler;

import com.customerpulse.customerpulsesurvey.listener.CustomerPulseSurveyListener;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Process-level registry that bridges a {@link CustomerPulseSurveyListener} across the
 * {@code startActivity} boundary for the full-page survey path.
 *
 * <p>A {@link CustomerPulseSurveyListener} cannot be placed in an {@code Intent}
 * (it is neither {@code Parcelable} nor reliably {@code Serializable}), so
 * {@code CustomerPulseSurvey.showSurveyPage(...)} instead {@link #register(CustomerPulseSurveyListener)}s
 * the listener and puts only the returned {@code long} token in the Intent extra.
 * {@code WebViewActivity} resolves the listener with {@link #get(long)} on create and
 * removes it with {@link #remove(long)} in {@code onDestroy} to avoid leaking it.</p>
 *
 * <p>Backed by a {@link ConcurrentHashMap} keyed by a monotonically increasing
 * {@link AtomicLong} token, so concurrent or sequential survey launches each get a
 * unique token and never clobber one another (unlike a bare static field).</p>
 *
 * <p>This is an internal helper and is not part of the public SDK API.</p>
 *
 * @author CustomerPulse
 * @version 2.1.0
 */
public final class CustomerPulseCallbackRegistry {

    private static final String TAG = "CustomerPulseCallbackRegistry";

    /**
     * Sentinel token returned when there is no listener to register.
     */
    public static final long NO_TOKEN = -1L;

    private static final ConcurrentHashMap<Long, CustomerPulseSurveyListener> LISTENERS = new ConcurrentHashMap<>();
    private static final AtomicLong TOKEN_GENERATOR = new AtomicLong(0L);

    private CustomerPulseCallbackRegistry() {
        // no instances
    }

    /**
     * Register a listener and obtain a unique token referencing it.
     *
     * @param listener the listener to store; if {@code null}, nothing is stored
     * @return a unique token to resolve the listener later, or {@link #NO_TOKEN} if {@code listener} was {@code null}
     */
    public static long register(CustomerPulseSurveyListener listener) {
        if (listener == null) {
            return NO_TOKEN;
        }
        long token = TOKEN_GENERATOR.incrementAndGet();
        LISTENERS.put(token, listener);
        return token;
    }

    /**
     * Resolve the listener previously stored under the given token.
     *
     * @param token the token returned by {@link #register(CustomerPulseSurveyListener)}
     * @return the listener, or {@code null} if the token is unknown or {@link #NO_TOKEN}
     */
    public static CustomerPulseSurveyListener get(long token) {
        if (token == NO_TOKEN) {
            return null;
        }
        return LISTENERS.get(token);
    }

    /**
     * Remove the listener stored under the given token, if any.
     *
     * <p>Safe to call with {@link #NO_TOKEN} or an unknown token (no-op).</p>
     *
     * @param token the token whose listener should be removed
     */
    public static void remove(long token) {
        if (token == NO_TOKEN) {
            return;
        }
        LISTENERS.remove(token);
    }
}
