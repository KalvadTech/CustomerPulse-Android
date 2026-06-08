package com.customerpulse.customerpulsesurvey.jsHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import com.customerpulse.customerpulsesurvey.listener.CustomerPulseSurveyListener;

import org.junit.Test;

/**
 * JVM unit tests for {@link CustomerPulseCallbackRegistry}.
 *
 * <p>Covers register/resolve/remove, token uniqueness, and null-safety. The registry
 * is a plain process-level map with no Android dependencies, so it runs on the JVM
 * without Robolectric.</p>
 */
public class CustomerPulseCallbackRegistryTest {

    private static CustomerPulseSurveyListener emptyListener() {
        return new CustomerPulseSurveyListener() {
        };
    }

    @Test
    public void register_thenGet_returnsSameInstance() {
        CustomerPulseSurveyListener listener = emptyListener();

        long token = CustomerPulseCallbackRegistry.register(listener);

        assertSame(listener, CustomerPulseCallbackRegistry.get(token));
        CustomerPulseCallbackRegistry.remove(token);
    }

    @Test
    public void register_generatesDistinctTokens() {
        long t1 = CustomerPulseCallbackRegistry.register(emptyListener());
        long t2 = CustomerPulseCallbackRegistry.register(emptyListener());

        assertNotEquals(t1, t2);
        CustomerPulseCallbackRegistry.remove(t1);
        CustomerPulseCallbackRegistry.remove(t2);
    }

    @Test
    public void distinctTokens_resolveToTheirOwnListeners() {
        CustomerPulseSurveyListener a = emptyListener();
        CustomerPulseSurveyListener b = emptyListener();

        long ta = CustomerPulseCallbackRegistry.register(a);
        long tb = CustomerPulseCallbackRegistry.register(b);

        assertSame(a, CustomerPulseCallbackRegistry.get(ta));
        assertSame(b, CustomerPulseCallbackRegistry.get(tb));

        CustomerPulseCallbackRegistry.remove(ta);
        CustomerPulseCallbackRegistry.remove(tb);
    }

    @Test
    public void remove_deletesEntry() {
        long token = CustomerPulseCallbackRegistry.register(emptyListener());

        CustomerPulseCallbackRegistry.remove(token);

        assertNull(CustomerPulseCallbackRegistry.get(token));
    }

    @Test
    public void get_unknownToken_returnsNull() {
        assertNull(CustomerPulseCallbackRegistry.get(Long.MAX_VALUE));
    }

    @Test
    public void registerNull_returnsNoTokenSentinel() {
        long token = CustomerPulseCallbackRegistry.register(null);

        assertEquals(CustomerPulseCallbackRegistry.NO_TOKEN, token);
    }

    @Test
    public void get_noTokenSentinel_returnsNull() {
        assertNull(CustomerPulseCallbackRegistry.get(CustomerPulseCallbackRegistry.NO_TOKEN));
    }

    @Test
    public void remove_noTokenSentinel_isSafe() {
        // Should not throw.
        CustomerPulseCallbackRegistry.remove(CustomerPulseCallbackRegistry.NO_TOKEN);
    }

    /**
     * Regression for M1 (config-change listener loss).
     *
     * <p>{@code WebViewActivity.onDestroy()} only removes the registry entry when
     * {@code isFinishing()} is true. On a configuration-change recreation (e.g. rotation)
     * {@code isFinishing()} is false, so the entry must NOT be removed — the recreated
     * {@code onCreate} re-reads the same redelivered {@code callbackToken} extra and must
     * still be able to resolve the listener. This test models that lifecycle invariant at
     * the registry level: a token registered once stays resolvable across repeated
     * resolutions and is only gone after an explicit {@code remove} (the genuine-finish path).</p>
     */
    @Test
    public void token_survivesUntilExplicitRemove_modelsConfigChangeRecreation() {
        CustomerPulseSurveyListener listener = emptyListener();
        long token = CustomerPulseCallbackRegistry.register(listener);

        // First resolve: original Activity instance in onCreate.
        assertSame(listener, CustomerPulseCallbackRegistry.get(token));

        // Config-change recreation: old instance's onDestroy did NOT remove (isFinishing()
        // was false), so the recreated onCreate must still resolve the SAME listener from
        // the SAME redelivered token. Simulated by resolving again without an intervening
        // remove.
        assertSame("config-change recreation must still resolve the listener",
                listener, CustomerPulseCallbackRegistry.get(token));

        // Genuine teardown: onDestroy with isFinishing() == true removes the entry.
        CustomerPulseCallbackRegistry.remove(token);
        assertNull("after a genuine finish the listener must be gone (no leak)",
                CustomerPulseCallbackRegistry.get(token));
    }
}
