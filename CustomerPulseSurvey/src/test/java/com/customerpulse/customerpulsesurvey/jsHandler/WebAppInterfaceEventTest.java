package com.customerpulse.customerpulsesurvey.jsHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.customerpulse.customerpulsesurvey.listener.CustomerPulseSurveyListener;

import org.junit.Before;
import org.junit.Test;

/**
 * JVM unit tests for {@link WebAppInterface#handleEvent(String)} routing.
 *
 * <p>The real {@link WebAppInterface#dispatchOnMain(Runnable)} posts to the main
 * {@code Looper}, which does not exist on a plain JVM. These tests use a test
 * subclass that overrides {@code dispatchOnMain} to run the runnable synchronously
 * (preserving the null-listener guard) and a no-op {@code close()}, so the routing
 * logic can be verified deterministically without Robolectric or a live WebView.</p>
 */
public class WebAppInterfaceEventTest {

    /**
     * Records which listener callbacks fired.
     */
    private static final class RecordingListener implements CustomerPulseSurveyListener {
        int completed = 0;
        int error = 0;
        int dismissed = 0;

        @Override
        public void onCompleted() {
            completed++;
        }

        @Override
        public void onError() {
            error++;
        }

        @Override
        public void onDismissed() {
            dismissed++;
        }
    }

    /**
     * Test double that runs dispatched callbacks synchronously (no Looper) and
     * counts {@code close()} invocations instead of touching real Activity/dialog state.
     */
    private static final class TestWebAppInterface extends WebAppInterface {
        int closeCount = 0;

        TestWebAppInterface(CustomerPulseSurveyListener listener) {
            super(null, 0, listener);
        }

        @Override
        void dispatchOnMain(Runnable r) {
            // Preserve the production null-listener guard, but run synchronously.
            if (listener == null) {
                return;
            }
            r.run();
        }

        @Override
        void close() {
            closeCount++;
        }
    }

    private RecordingListener listener;
    private TestWebAppInterface webInterface;

    @Before
    public void setUp() {
        listener = new RecordingListener();
        webInterface = new TestWebAppInterface(listener);
    }

    @Test
    public void completedEvent_closesAndNotifiesOnCompleted() {
        webInterface.handleEvent("so-widget-completed");

        assertEquals("close() should fire once on completion", 1, webInterface.closeCount);
        assertEquals(1, listener.completed);
        assertEquals(0, listener.error);
        assertEquals(0, listener.dismissed);
    }

    @Test
    public void errorEvent_notifiesOnErrorWithoutClosing() {
        webInterface.handleEvent("so-widget-error");

        assertEquals("error event must not tear down the survey", 0, webInterface.closeCount);
        assertEquals(0, listener.completed);
        assertEquals(1, listener.error);
        assertEquals(0, listener.dismissed);
    }

    @Test
    public void closedEvent_notifiesOnDismissedWithoutClosing() {
        webInterface.handleEvent("so-widget-closed");

        assertEquals("closed event must not tear down the survey", 0, webInterface.closeCount);
        assertEquals(0, listener.completed);
        assertEquals(0, listener.error);
        assertEquals(1, listener.dismissed);
    }

    @Test
    public void openEvent_isNoOp() {
        webInterface.handleEvent("open");

        assertEquals(0, webInterface.closeCount);
        assertEquals(0, listener.completed);
        assertEquals(0, listener.error);
        assertEquals(0, listener.dismissed);
    }

    @Test
    public void unknownEvent_isIgnoredAndDoesNotCrash() {
        webInterface.handleEvent("garbage-event");

        assertEquals(0, webInterface.closeCount);
        assertEquals(0, listener.completed);
        assertEquals(0, listener.error);
        assertEquals(0, listener.dismissed);
    }

    @Test
    public void nullListener_eventsDoNotCrashButStillClose() {
        TestWebAppInterface noListener = new TestWebAppInterface(null);

        // Should not throw despite the absent listener.
        noListener.handleEvent("so-widget-completed");
        noListener.handleEvent("so-widget-error");
        noListener.handleEvent("so-widget-closed");
        noListener.handleEvent("open");
        noListener.handleEvent("garbage-event");

        assertEquals("completion still auto-closes even without a listener", 1, noListener.closeCount);
    }

    @Test
    public void eachEvent_routesToExactlyOneCallback() {
        webInterface.handleEvent("so-widget-completed");
        webInterface.handleEvent("so-widget-error");
        webInterface.handleEvent("so-widget-closed");

        assertEquals(1, listener.completed);
        assertEquals(1, listener.error);
        assertEquals(1, listener.dismissed);
        assertTrue(webInterface.closeCount >= 1);
        assertFalse("no stray extra dispatches expected",
                listener.completed + listener.error + listener.dismissed != 3);
    }
}
