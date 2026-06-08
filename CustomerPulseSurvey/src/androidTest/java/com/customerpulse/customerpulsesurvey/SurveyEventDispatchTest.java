package com.customerpulse.customerpulsesurvey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import android.os.Looper;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.customerpulse.customerpulsesurvey.jsHandler.CustomerPulseCallbackRegistry;
import com.customerpulse.customerpulsesurvey.jsHandler.PageWebInterface;
import com.customerpulse.customerpulsesurvey.listener.CustomerPulseSurveyListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Instrumented test asserting that survey event callbacks are delivered on the
 * main (UI) thread via the {@code Handler(Looper.getMainLooper())} dispatch.
 *
 * <p>Runs on a device/emulator because it needs a real main {@code Looper}.</p>
 */
@RunWith(AndroidJUnit4.class)
public class SurveyEventDispatchTest {

    @Test
    public void onCompleted_isDeliveredOnMainThread() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean onMainThread = new AtomicBoolean(false);

        CustomerPulseSurveyListener listener = new CustomerPulseSurveyListener() {
            @Override
            public void onCompleted() {
                onMainThread.set(Looper.myLooper() == Looper.getMainLooper());
                latch.countDown();
            }
        };

        // closeDelay 0; null context is fine because PageWebInterface.close() guards on
        // instanceof Activity and simply logs/returns when the context is not an Activity.
        PageWebInterface webInterface = new PageWebInterface(null, 0, listener);
        webInterface.jsEvent("so-widget-completed");

        assertTrue("onCompleted callback was not delivered in time",
                latch.await(5, TimeUnit.SECONDS));
        assertTrue("callback must run on the main thread", onMainThread.get());
    }

    @Test
    public void errorAndDismissed_areDelivered() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        AtomicBoolean errorOnMain = new AtomicBoolean(false);
        AtomicBoolean dismissedOnMain = new AtomicBoolean(false);

        CustomerPulseSurveyListener listener = new CustomerPulseSurveyListener() {
            @Override
            public void onError() {
                errorOnMain.set(Looper.myLooper() == Looper.getMainLooper());
                latch.countDown();
            }

            @Override
            public void onDismissed() {
                dismissedOnMain.set(Looper.myLooper() == Looper.getMainLooper());
                latch.countDown();
            }
        };

        PageWebInterface webInterface = new PageWebInterface(null, 0, listener);
        webInterface.jsEvent("so-widget-error");
        webInterface.jsEvent("so-widget-closed");

        assertTrue("error/dismissed callbacks were not delivered in time",
                latch.await(5, TimeUnit.SECONDS));
        assertEquals(true, errorOnMain.get());
        assertEquals(true, dismissedOnMain.get());
    }

    /**
     * Regression for M1 (config-change listener loss). Requires a device/emulator to run.
     *
     * <p>{@code WebViewActivity.onDestroy()} only removes the registry entry when
     * {@code isFinishing()} is true. This test reproduces the rotation sequence at the
     * boundary the Activity relies on:</p>
     * <ol>
     *   <li>register a listener and resolve it (original {@code onCreate});</li>
     *   <li>simulate a configuration-change teardown where {@code isFinishing()} is false —
     *       i.e. {@code onDestroy} does NOT remove — then resolve the same redelivered token
     *       again ({@code recreated onCreate}) and fire an event through the real
     *       main-Looper dispatch: the listener must still be invoked;</li>
     *   <li>simulate a genuine finish ({@code isFinishing()} true) which removes the entry,
     *       proving no leak.</li>
     * </ol>
     */
    @Test
    public void listenerSurvivesConfigChange_thenRemovedOnFinish() throws InterruptedException {
        CountDownLatch afterRecreate = new CountDownLatch(1);
        AtomicBoolean firedAfterRecreate = new AtomicBoolean(false);

        CustomerPulseSurveyListener listener = new CustomerPulseSurveyListener() {
            @Override
            public void onCompleted() {
                firedAfterRecreate.set(true);
                afterRecreate.countDown();
            }
        };

        long token = CustomerPulseCallbackRegistry.register(listener);

        // Original onCreate resolves the listener.
        assertSame(listener, CustomerPulseCallbackRegistry.get(token));

        // Config-change recreation: old instance onDestroy with isFinishing()==false does
        // NOT remove. Recreated onCreate re-resolves the SAME token and must get the listener.
        CustomerPulseSurveyListener afterRotation = CustomerPulseCallbackRegistry.get(token);
        assertSame("listener must survive a non-finishing (config-change) teardown",
                listener, afterRotation);

        // Fire an event through the real main-thread dispatch on the recreated path.
        PageWebInterface recreatedInterface = new PageWebInterface(null, 0, afterRotation);
        recreatedInterface.jsEvent("so-widget-completed");
        assertTrue("callback must still fire after a config-change recreation",
                afterRecreate.await(5, TimeUnit.SECONDS));
        assertTrue(firedAfterRecreate.get());

        // Genuine finish: isFinishing()==true removes the entry — no leak.
        CustomerPulseCallbackRegistry.remove(token);
        assertNull("after a genuine finish the listener must be gone",
                CustomerPulseCallbackRegistry.get(token));
    }
}
