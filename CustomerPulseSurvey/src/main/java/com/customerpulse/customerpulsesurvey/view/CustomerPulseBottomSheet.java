package com.customerpulse.customerpulsesurvey.view;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.customerpulse.customerpulsesurvey.R;
import com.customerpulse.customerpulsesurvey.utils.Utils;

/**
 * Handler for displaying surveys in a bottom sheet dialog.
 * Manages the bottom sheet lifecycle and WebView configuration.
 */
public class CustomerPulseBottomSheet {

    private static final String TAG = "CustomerPulseBottomSheet";
    private BottomSheetDialog bottomSheetDialog;

    private void initializeDialog(Context context, boolean dismissible) {
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setOnShowListener(this::onShow);
        if (dismissible) {
            handleOnClickOutside();
        }
    }

    private void handleOnClickOutside() {
        if (bottomSheetDialog.getWindow() == null) {
            return;
        }
        final View touchOutsideView = bottomSheetDialog.getWindow().getDecorView().findViewById(R.id.coordinatorLayout);
        if (touchOutsideView != null) {
            touchOutsideView.setOnClickListener(view -> bottomSheetDialog.dismiss());
        }
    }

    /**
     * Display a survey URL in a bottom sheet dialog.
     *
     * @param context          context of the current opened activity
     * @param url              the URL to load in the WebView
     * @param dismissible      whether the close button should be visible
     * @param closingDelayInMs time to wait before closing after survey completion in milliseconds
     */
    public void show(Context context, String url, boolean dismissible, int closingDelayInMs) {
        initializeDialog(context, dismissible);
        bottomSheetDialog.show();

        RoundedWebView webView = bottomSheetDialog.findViewById(R.id.bottom_web_view);
        Button closeBtn = bottomSheetDialog.findViewById(R.id.close_button);

        if (webView == null || closeBtn == null) {
            Log.e(TAG, "Failed to find required views in bottom sheet layout");
            bottomSheetDialog.dismiss();
            return;
        }

        if (!dismissible) {
            closeBtn.setVisibility(View.INVISIBLE);
        }

        closeBtn.setOnClickListener(view -> bottomSheetDialog.dismiss());

        Utils.loadUrl(webView, url, context, bottomSheetDialog, closingDelayInMs);
    }

    private void onShow(DialogInterface dialogInterface) {
        BottomSheetDialog d = (BottomSheetDialog) dialogInterface;
        CoordinatorLayout coordinatorLayout = d.findViewById(R.id.coordinatorLayout);
        RelativeLayout bottomSheetInternal = d.findViewById(R.id.bottom_sheet_layout);

        if (bottomSheetInternal == null || coordinatorLayout == null) {
            Log.e(TAG, "Failed to find layout views in onShow");
            return;
        }

        BottomSheetBehavior<RelativeLayout> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetInternal);
        BottomSheetBehavior.from((View) coordinatorLayout.getParent()).setPeekHeight(bottomSheetInternal.getHeight());
        bottomSheetBehavior.setPeekHeight(bottomSheetInternal.getHeight());
        bottomSheetBehavior.setDraggable(false);
        bottomSheetBehavior.setHideable(false);
        coordinatorLayout.getParent().requestLayout();
    }
}
