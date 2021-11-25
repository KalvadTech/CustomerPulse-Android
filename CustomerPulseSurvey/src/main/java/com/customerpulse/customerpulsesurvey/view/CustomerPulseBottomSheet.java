package com.customerpulse.customerpulsesurvey.view;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import android.widget.RelativeLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.customerpulse.customerpulsesurvey.R;
import com.customerpulse.customerpulsesurvey.utils.Utils;

/**
 * Class to show bottom sheet with a web view containing CustomerPulse web page
 */
public class CustomerPulseBottomSheet {

    BottomSheetDialog bottomSheetDialog;

    private void initializeDialog(Context context, boolean dismissible){
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setOnShowListener(this::onShow);
        if(dismissible) handleOcClickOutSide();
    }

    private void handleOcClickOutSide(){
        final View touchOutsideView = bottomSheetDialog.getWindow().getDecorView().findViewById(R.id.touch_outside);
        touchOutsideView.setOnClickListener(view -> bottomSheetDialog.dismiss());
    }


    /**
     * load url to web view inside a bottom sheet dialog
     *
     * @param context     context of the current opened activity
     * @param url         the url to load to webView
     * @param dismissible boolean to decide if the bottom sheet should be dismissible or no
     * @param closingDelayInMs   time to wait before closing the survey after finish milli seconds
     */
    public void show(Context context, String url, boolean dismissible, int closingDelayInMs) {
        initializeDialog(context, dismissible);
        bottomSheetDialog.show();
        WebView webView = bottomSheetDialog.findViewById(R.id.bottom_web_view);
        Button closeBtn = bottomSheetDialog.findViewById(R.id.close_button);
        assert closeBtn != null;
        if (!dismissible)
            closeBtn.setVisibility(View.INVISIBLE);
        closeBtn.setOnClickListener(view -> bottomSheetDialog.hide());
        assert webView != null;
        Utils.loadUrl(webView, url, context, bottomSheetDialog, closingDelayInMs);
    }

    private void onShow (DialogInterface dialogInterface) {
        BottomSheetDialog d = (BottomSheetDialog) dialogInterface;
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) d.findViewById(R.id.coordinatorLayout);
        RelativeLayout bottomSheetInternal = d.findViewById(R.id.bottom_sheet_layout);
        assert bottomSheetInternal != null;
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetInternal);
        assert coordinatorLayout != null;
        BottomSheetBehavior.from((View)coordinatorLayout.getParent()).setPeekHeight(bottomSheetInternal.getHeight());
        bottomSheetBehavior.setPeekHeight(bottomSheetInternal.getHeight());
        bottomSheetBehavior.setDraggable(false);
        bottomSheetBehavior.setHideable(false);
        coordinatorLayout.getParent().requestLayout();
    }


}
